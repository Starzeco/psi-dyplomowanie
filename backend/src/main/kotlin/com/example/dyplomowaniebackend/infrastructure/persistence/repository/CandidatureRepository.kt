package com.example.dyplomowaniebackend.infrastructure.persistence.repository

import com.example.dyplomowaniebackend.domain.model.CandidatureStatus
import com.example.dyplomowaniebackend.domain.model.CandidatureType
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.CandidatureAcceptanceEntity
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.CandidatureEntity
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.StaffMemberEntity
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.SubjectEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.*


@Repository
interface CandidatureRepository : JpaRepository<CandidatureEntity, Long>, CandidatureRepositoryCustom {

    @Query(
        "UPDATE CandidatureEntity CE " +
                "SET CE.accepted = :accepted " +
                "WHERE CE.candidatureId = :candidatureId"
    )
    @Modifying
    @Transactional
    fun updateAcceptedById(
        @Param("candidatureId") candidatureId: Long, @Param("accepted") accepted: Boolean
    ): Int

    @Query(
        "UPDATE CandidatureEntity CE " +
                "SET CE.accepted = false " +
                "WHERE CE.subjectId = :subjectId AND CE.candidatureId <> :candidatureId"
    )
    @Modifying
    @Transactional
    fun updateAcceptedToFalseWithExclusiveIdBySubjectId(
        @Param("subjectId") subjectId: Long,
        @Param("candidatureId") candidatureId: Long
    ): Int
}

interface CandidatureRepositoryCustom {
    fun getAllCandidaturesBySupervisorIdAndGraduationProcessIdAndAcceptedCandidaturesAcceptances(
        supervisorId: Long,
        graduationProcessId: Long,
        phrase: String?,
        type: CandidatureType?,
        status: CandidatureStatus?
    ): Set<CandidatureEntity>

    fun getAllCandidaturesByStudentIdAndGraduationProcessIdAndAcceptedCandidaturesAcceptances(
        studentId: Long, graduationProcessId: Long, phrase: String?, type: CandidatureType?, status: CandidatureStatus?
    ): Set<CandidatureEntity>
}

@Repository
class CandidatureRepositoryCustomImpl(
    @PersistenceContext private val entityManager: EntityManager,
) : CandidatureRepositoryCustom {

    override fun getAllCandidaturesBySupervisorIdAndGraduationProcessIdAndAcceptedCandidaturesAcceptances(
        supervisorId: Long,
        graduationProcessId: Long,
        phrase: String?,
        type: CandidatureType?,
        status: CandidatureStatus?
    ): Set<CandidatureEntity> {
        val (criteriaBuilder, query, root) = preparePrerequisites()

        val acceptedPath: Path<Boolean> = root.get("accepted")
        val acceptedCandidaturesPredicate = when (status) {
            CandidatureStatus.ACCEPTED -> criteriaBuilder.isTrue(acceptedPath)
            CandidatureStatus.REJECTED -> criteriaBuilder.isFalse(acceptedPath)
            else -> criteriaBuilder.isTrue(criteriaBuilder.literal(true))
        }

        val subjectJoin: Join<CandidatureEntity, SubjectEntity> = root.join("subject")
        val subjectIdPredicate = criteriaBuilder.and(
            criteriaBuilder.equal(subjectJoin.get<Long>("staffMemberId"), supervisorId),
            criteriaBuilder.equal(subjectJoin.get<Long>("graduationProcessId"), graduationProcessId),
            criteriaBuilder.like(subjectJoin.get("name"), "%$phrase%"),
        )

        val supervisorJoin: Join<SubjectEntity, StaffMemberEntity> = subjectJoin.join("supervisor")
        val supervisorNamePredicate = prepareSupervisorNamePredicate(criteriaBuilder, supervisorJoin, phrase)

        val (subqueryCandidatureAcceptances, subqueryAcceptedCandidatureAcceptances) = prepareCandidatureAcceptanceSubqueries(
            query, criteriaBuilder, root
        )

        val candidatureAcceptancesPredicate = when (type) {
            CandidatureType.GROUP -> criteriaBuilder.greaterThan(subqueryCandidatureAcceptances, 0L)
            CandidatureType.INDIVIDUAL -> criteriaBuilder.equal(subqueryCandidatureAcceptances, 0L)
            else -> criteriaBuilder.isTrue(criteriaBuilder.literal(true))
        }

        val acceptedCriteriaAcceptancesPredicate = criteriaBuilder.or(
            criteriaBuilder.equal(subqueryCandidatureAcceptances, subqueryAcceptedCandidatureAcceptances),
            criteriaBuilder.equal(subqueryCandidatureAcceptances, 0L)
        )

        val predicates = criteriaBuilder.and(
            subjectIdPredicate,
            supervisorNamePredicate,
            acceptedCandidaturesPredicate,
            candidatureAcceptancesPredicate,
            acceptedCriteriaAcceptancesPredicate
        )

        return executeQuery(query, root, predicates)
    }

    override fun getAllCandidaturesByStudentIdAndGraduationProcessIdAndAcceptedCandidaturesAcceptances(
        studentId: Long, graduationProcessId: Long, phrase: String?, type: CandidatureType?, status: CandidatureStatus?
    ): Set<CandidatureEntity> {
        val (criteriaBuilder, query, root) = preparePrerequisites()

        val acceptedPath: Path<Boolean> = root.get("accepted")
        val acceptedCandidaturesPredicate = when (status) {
            CandidatureStatus.ACCEPTED -> criteriaBuilder.isTrue(acceptedPath)
            CandidatureStatus.REJECTED -> criteriaBuilder.isFalse(acceptedPath)
            else -> criteriaBuilder.isTrue(criteriaBuilder.literal(true))
        }


        val subjectJoin: Join<CandidatureEntity, SubjectEntity> = root.join("subject")
        val subjectIdPredicate = criteriaBuilder.and(
            criteriaBuilder.equal(subjectJoin.get<Long>("graduationProcessId"), graduationProcessId),
            criteriaBuilder.like(subjectJoin.get("topic"), "%${phrase.orEmpty()}%"),
        )

        val subqueryStudentHasAccessToCandidature = query.subquery(Boolean::class.java)
        val studentJoin: Join<CandidatureAcceptanceEntity, CandidatureEntity> =
            subqueryStudentHasAccessToCandidature.from(CandidatureAcceptanceEntity::class.java).join("candidature")
        subqueryStudentHasAccessToCandidature.where(
            criteriaBuilder.and(
                criteriaBuilder.equal(studentJoin.get<String>("studentId"), studentId), criteriaBuilder.equal(
                    root.get<String>("candidatureId"), studentJoin.get<String>("candidatureId")
                )
            )
        )

        val joinSupervisor: Join<SubjectEntity, StaffMemberEntity> = subjectJoin.join("supervisor")

        val (subqueryCandidatureAcceptances, subqueryAcceptedCandidatureAcceptances) = prepareCandidatureAcceptanceSubqueries(
            query, criteriaBuilder, root
        )

        val supervisorNamePredicate = prepareSupervisorNamePredicate(criteriaBuilder, joinSupervisor, phrase)

        val candidatureAcceptancesPredicate = when (type) {
            CandidatureType.GROUP -> criteriaBuilder.greaterThan(subqueryCandidatureAcceptances, 0L)
            CandidatureType.INDIVIDUAL -> criteriaBuilder.equal(subqueryCandidatureAcceptances, 0L)
            else -> criteriaBuilder.isTrue(criteriaBuilder.literal(true))
        }

        val acceptedCriteriaAcceptancesPredicate = when (status) {
            CandidatureStatus.TO_ACCEPT_BY_STUDENTS -> criteriaBuilder.notEqual(
                subqueryCandidatureAcceptances, subqueryAcceptedCandidatureAcceptances
            )
            CandidatureStatus.TO_ACCEPT_BY_SUPERVISOR -> criteriaBuilder.equal(
                subqueryCandidatureAcceptances, subqueryAcceptedCandidatureAcceptances
            )
            else -> criteriaBuilder.isTrue(criteriaBuilder.literal(true))
        }

        val predicates = criteriaBuilder.and(
            subjectIdPredicate,
            supervisorNamePredicate,
            acceptedCandidaturesPredicate,
            candidatureAcceptancesPredicate,
            acceptedCriteriaAcceptancesPredicate
        )

        return executeQuery(query, root, predicates)
    }

    private fun preparePrerequisites(): Triple<CriteriaBuilder, CriteriaQuery<CandidatureEntity>, Root<CandidatureEntity>> {
        val criteriaBuilder = entityManager.criteriaBuilder
        val query = criteriaBuilder.createQuery(CandidatureEntity::class.java)
        val root = query.from(CandidatureEntity::class.java)
        return Triple(criteriaBuilder, query, root)
    }

    private fun prepareCandidatureAcceptanceSubqueries(
        query: CriteriaQuery<CandidatureEntity>, criteriaBuilder: CriteriaBuilder, root: Root<CandidatureEntity>
    ): Pair<Subquery<Long>, Subquery<Long>> {
        val subqueryCandidatureAcceptances = query.subquery(Long::class.java)
        val candidatureAcceptanceRoot = subqueryCandidatureAcceptances.from(CandidatureAcceptanceEntity::class.java)
        subqueryCandidatureAcceptances.where(
            criteriaBuilder.equal(
                candidatureAcceptanceRoot.get<Long>("candidatureId"), root.get<Long>("candidatureId")
            )
        )
        subqueryCandidatureAcceptances.select(criteriaBuilder.count(candidatureAcceptanceRoot.get<Long>("candidatureAcceptanceId")))

        val subqueryAcceptedCandidatureAcceptances = query.subquery(Long::class.java)
        val acceptedCandidatureAcceptancesRoot =
            subqueryAcceptedCandidatureAcceptances.from(CandidatureAcceptanceEntity::class.java)

        subqueryAcceptedCandidatureAcceptances.select(
            criteriaBuilder.count(
                acceptedCandidatureAcceptancesRoot.get<Long>(
                    "candidatureAcceptanceId"
                )
            )
        )
        subqueryAcceptedCandidatureAcceptances.where(
            criteriaBuilder.and(
                criteriaBuilder.isTrue(acceptedCandidatureAcceptancesRoot.get("accepted")), criteriaBuilder.equal(
                    acceptedCandidatureAcceptancesRoot.get<Long>("candidatureId"), root.get<Long>("candidatureId")
                )
            )
        )
        return Pair(subqueryCandidatureAcceptances, subqueryAcceptedCandidatureAcceptances)
    }

    private fun prepareSupervisorNamePredicate(
        criteriaBuilder: CriteriaBuilder, supervisorPath: Path<StaffMemberEntity>, phrase: String?
    ) = if (phrase != null) criteriaBuilder.like(
        criteriaBuilder.concat(
            supervisorPath.get("title"), criteriaBuilder.concat(
                " ", criteriaBuilder.concat(
                    supervisorPath.get("name"), criteriaBuilder.concat(
                        " ", supervisorPath.get("surname")
                    )
                )
            )
        ), "%$phrase%"
    ) else criteriaBuilder.isTrue(criteriaBuilder.literal(true))

    private fun executeQuery(
        query: CriteriaQuery<CandidatureEntity>, root: Root<CandidatureEntity>, predicates: Predicate
    ) = entityManager.createQuery(
        query.select(root).where(predicates)
    ).resultList.toSet()


}

@Repository
interface CandidatureAcceptanceRepository : JpaRepository<CandidatureAcceptanceEntity, Long> {

    @Query(
        "UPDATE CandidatureAcceptanceEntity CAE " + "SET CAE.accepted = :accepted " + "WHERE CAE.candidatureAcceptanceId = :candidatureAcceptanceId"
    )
    @Modifying
    @Transactional
    fun updateAcceptedById(
        @Param("candidatureAcceptanceId") candidatureAcceptanceId: Long, @Param("accepted") accepted: Boolean
    ): Int

    fun findAllByCandidatureId(candidatureId: Long): Set<CandidatureAcceptanceEntity>

    fun existsByCandidatureIdAndAcceptedIsFalseOrAcceptedIsNull(candidatureId: Long): Boolean
}


