package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance
import com.example.dyplomowaniebackend.domain.model.exception.PropositionAcceptanceConstraintViolationException
import com.example.dyplomowaniebackend.domain.submission.port.persistence.PropositionAcceptanceMutationPort
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToEntity
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.PropositionAcceptancesRepository
import org.springframework.stereotype.Service

@Service
class PropositionAcceptancesMutationAdapter(
    private val propositionAcceptancesRepository: PropositionAcceptancesRepository
) : PropositionAcceptanceMutationPort {

    override fun updateAcceptedFieldById(propositionAcceptanceId: Long, accepted: Boolean): Long {
        propositionAcceptancesRepository.updateAcceptedById(propositionAcceptanceId, accepted)
        return propositionAcceptanceId
    }

    override fun insert(propositionAcceptance: PropositionAcceptance): Long {
        val hasPropositionAcceptanceId = propositionAcceptance.propositionAcceptanceId != null
        if (hasPropositionAcceptanceId) throw PropositionAcceptanceConstraintViolationException(
            "Can not insert a proposition acceptance with id: ${propositionAcceptance.propositionAcceptanceId}"
        )
        return propositionAcceptancesRepository.save(propositionAcceptance.mapToEntity()).propositionAcceptanceId!!
    }
}
