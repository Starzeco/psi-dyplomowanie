package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance
import com.example.dyplomowaniebackend.domain.submission.port.PropositionAcceptancesMutationPort
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToEntity
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.PropositionAcceptancesRepository
import org.springframework.stereotype.Component

@Component
class PropositionAcceptancesMutationAdapter(
    private val propositionAcceptancesRepository: PropositionAcceptancesRepository
) : PropositionAcceptancesMutationPort {

    override fun updateAcceptedFieldById(propositionAcceptanceId: Long, accepted: Boolean): Long =
        propositionAcceptancesRepository.updateAcceptedById(propositionAcceptanceId, accepted)

    override fun insert(propositionAcceptance: PropositionAcceptance): Long {
        val exists = propositionAcceptancesRepository.existsById(propositionAcceptance.propositionAcceptanceId)
        return if (!exists)
            propositionAcceptancesRepository
                .save(propositionAcceptance.mapToEntity())
                .propositionAcceptanceId
        else propositionAcceptance.propositionAcceptanceId
    }

}
