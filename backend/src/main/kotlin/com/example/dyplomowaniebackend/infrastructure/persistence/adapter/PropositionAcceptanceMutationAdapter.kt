package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.PropositionAcceptanceMutationPort
import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapCreation
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapCreationEntity
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.PropositionAcceptanceRepository
import org.springframework.stereotype.Service

@Service
class PropositionAcceptanceMutationAdapter(val propositionAcceptanceRepository: PropositionAcceptanceRepository) :
    PropositionAcceptanceMutationPort {
    override fun savePropositionAcceptances(propositionAcceptances: Set<PropositionAcceptance>): Set<PropositionAcceptance> {
        if (propositionAcceptances.isEmpty()) return setOf()
        return propositionAcceptanceRepository
            .saveAll(propositionAcceptances.map { propositionAcceptance -> propositionAcceptance.mapCreationEntity() }.toSet())
            .map { propositionAcceptance -> propositionAcceptance.mapCreation() }
            .toSet()
    }
}