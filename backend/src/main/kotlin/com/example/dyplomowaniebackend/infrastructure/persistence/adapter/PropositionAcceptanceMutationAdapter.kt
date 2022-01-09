package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance
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

    override fun insert(propositionAcceptance: PropositionAcceptance): Long {
        val exists =
            propositionAcceptance.propositionAcceptanceId?.let { propositionAcceptanceRepository.existsById(it) }
                ?: false
        return if (!exists)
            propositionAcceptanceRepository
                .save(propositionAcceptance.mapToEntity())
                .propositionAcceptanceId!!
        else propositionAcceptance.propositionAcceptanceId!!
    }

    override fun savePropositionAcceptances(propositionAcceptances: Set<PropositionAcceptance>): Set<PropositionAcceptance> {
        if (propositionAcceptances.isEmpty()) return setOf()
        return propositionAcceptanceRepository
            .saveAll(propositionAcceptances.map { it.mapToEntity() }.toSet())
            .map { it.mapToDomain() }
            .toSet()
    }
}
