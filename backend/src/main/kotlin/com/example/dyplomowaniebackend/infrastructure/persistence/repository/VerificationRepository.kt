package com.example.dyplomowaniebackend.infrastructure.persistence.repository

import com.example.dyplomowaniebackend.domain.model.Verification
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.VerificationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface VerificationRepository : JpaRepository<VerificationEntity, Long> {
    fun findAllByVerifiedFalseAndAndSubjectId(subjectId: Long): List<VerificationEntity>

    fun findByVerifierIdAndVerifiedAndSubjectTopicLike(
        verifierId: Long,
        verified: Boolean?,
        title: String,
    ): List<VerificationEntity>

    fun findByVerificationIdAndVerifierId(verificationId: Long, verifierId: Long): VerificationEntity?

    fun findByVerifierVerifierId(verifierId: Long): List<VerificationEntity>
    fun findBySubjectSubjectId(subjectId: Long): List<VerificationEntity>

    @Query(
        "UPDATE VerificationEntity VE " +
                "SET VE.verified = :decision, " +
                "VE.updateDate = current_timestamp, " +
                "VE.justification = :justification " +
                "WHERE VE.verificationId = :verificationId"
    )
    @Modifying
    @Transactional
    fun updateVerifiedByVerificationId(
        @Param("verificationId") verificationId: Long,
        @Param("decision") decision: Boolean,
        @Param("justification") justification: String
    ): Int

    @Query(
        "UPDATE VerificationEntity VE " +
                "SET VE.verified = :decision, " +
                "VE.justification = :justification " +
                "WHERE VE.verifier.verifierId = :verifierId AND VE.verified IS NULL "
    )
    @Modifying
    @Transactional
    fun updateVerifiedByVerifierIdAndVerifiedIsNull(
        @Param("verifierId") verifierId: Long,
        @Param("decision") decision: Boolean,
        @Param("justification") justification: String
    ): Int

    @Query(
        "UPDATE VerificationEntity VE " +
                "SET VE.verified = null " +
                "WHERE VE.subject.subjectId = :subjectId"
    )
    @Modifying
    @Transactional
    fun updateOldVerifications(@Param("subjectId") subjectId: Long): Int
}
