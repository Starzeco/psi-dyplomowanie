package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance
import com.example.dyplomowaniebackend.domain.model.exception.PropositionAcceptanceConstraintViolationException
import com.example.dyplomowaniebackend.domain.submission.port.persistence.PropositionAcceptanceMutationPort
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToEntity
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.PropositionAcceptanceRepository
import org.springframework.stereotype.Service

@Service
class PropositionAcceptanceMutationAdapter(
    private val propositionAcceptanceRepository: PropositionAcceptanceRepository
) : PropositionAcceptanceMutationPort,
    com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.PropositionAcceptanceMutationPort {

    override fun updateAcceptedFieldById(propositionAcceptanceId: Long, accepted: Boolean): Long {
        propositionAcceptanceRepository.updateAcceptedById(propositionAcceptanceId, accepted)
        return propositionAcceptanceId
    }

    override fun insert(propositionAcceptance: PropositionAcceptance): PropositionAcceptance {
        val hasPropositionAcceptanceId = propositionAcceptance.propositionAcceptanceId != null
        if (hasPropositionAcceptanceId) throw PropositionAcceptanceConstraintViolationException(
            "Can not insert a proposition acceptance with id: ${propositionAcceptance.propositionAcceptanceId}"
        )
        return propositionAcceptanceRepository.save(propositionAcceptance.mapToEntity()).mapToDomain()
    }


    override fun insertAll(propositionAcceptances: Set<PropositionAcceptance>): Set<PropositionAcceptance> {
        if (propositionAcceptances.isEmpty()) return setOf()
        val propositionAcceptanceIds = propositionAcceptances
            .filter { it.propositionAcceptanceId != null }
            .map { it.propositionAcceptanceId!! }
        if (propositionAcceptanceIds.isNotEmpty())
            throw PropositionAcceptanceConstraintViolationException(
                "Can not insert a proposition acceptances with ids: [${propositionAcceptanceIds.joinToString(" | ")}]"
            )
        return propositionAcceptanceRepository
            .saveAll(propositionAcceptances.map { it.mapToEntity() }.toSet())
            .map { it.mapToDomain() }
            .toSet()
    }
}
