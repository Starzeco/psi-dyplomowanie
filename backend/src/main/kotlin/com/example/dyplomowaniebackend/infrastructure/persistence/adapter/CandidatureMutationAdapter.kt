package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.candidature.port.persistance.CandidatureMutationPort
import com.example.dyplomowaniebackend.domain.model.Candidature
import com.example.dyplomowaniebackend.domain.model.CandidatureAcceptance
import com.example.dyplomowaniebackend.domain.model.exception.CandidatureAcceptanceConstraintViolationException
import com.example.dyplomowaniebackend.domain.model.exception.CandidatureConstraintViolationException
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToEntity
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.CandidatureAcceptanceRepository
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.CandidatureRepository
import org.springframework.stereotype.Service

@Service
class CandidatureMutationAdapter(
    private val candidatureRepository: CandidatureRepository,
    private val candidatureAcceptanceRepository: CandidatureAcceptanceRepository
) : CandidatureMutationPort {
    override fun insert(candidature: Candidature): Candidature {
        val hasCandidatureId = candidature.candidatureId != null
        if (hasCandidatureId) throw CandidatureConstraintViolationException(
            "Can not insert a candidature with id: ${candidature.candidatureId}"
        )
        return candidatureRepository.save(candidature.mapToEntity()).mapToDomain()
    }

    override fun updateAcceptedById(candidatureId: Long, accepted: Boolean): Long =
        candidatureRepository.updateAcceptedById(candidatureId, accepted).toLong()


    override fun insertAcceptances(candidatureAcceptances: Set<CandidatureAcceptance>): Set<CandidatureAcceptance> {
        val acceptancesIds =
            candidatureAcceptances.filter { it.candidatureAcceptanceId != null }.map { it.candidatureAcceptanceId!! }
        if (acceptancesIds.isNotEmpty()) throw CandidatureAcceptanceConstraintViolationException(
            "Can not insert candidature acceptances with ids: [${
                acceptancesIds.joinToString(
                    " | "
                )
            }]"
        )
        val candidatureAcceptanceEntities = candidatureAcceptances.map { it.mapToEntity() }
        return candidatureAcceptanceRepository.saveAll(candidatureAcceptanceEntities)
            .map { it.mapToDomain() }
            .toSet()
    }

    override fun updateAcceptanceAcceptedById(candidatureAcceptanceId: Long, accepted: Boolean): Long =
        candidatureAcceptanceRepository.updateAcceptedById(candidatureAcceptanceId, accepted).toLong()

    override fun updateAcceptedToFalseWithExclusiveIdBySubjectId(subjectId: Long, candidatureId: Long): Long =
        candidatureRepository.updateAcceptedToFalseWithExclusiveIdBySubjectId(subjectId, candidatureId).toLong()

}
