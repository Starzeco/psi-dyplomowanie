package com.example.dyplomowaniebackend.domain.submission.adapter

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance
import com.example.dyplomowaniebackend.domain.submission.port.PropositionAcceptanceServicePort
import com.example.dyplomowaniebackend.domain.submission.port.PropositionAcceptancesMutationPort
import com.example.dyplomowaniebackend.domain.submission.port.PropositionAcceptancesSearchPort
import org.springframework.stereotype.Component

@Component
class PropositionAcceptanceServiceAdapter(
    private val propositionAcceptancesSearchPort: PropositionAcceptancesSearchPort,
    private val propositionAcceptancesMutationPort: PropositionAcceptancesMutationPort
) : PropositionAcceptanceServicePort {

    override fun getAllPropositionAcceptancesByStudentId(studentId: Long): Set<PropositionAcceptance> =
        propositionAcceptancesSearchPort.getAllByStudentId(studentId)

    override fun updatePropositionAcceptanceAcceptedFieldById(propositionAcceptanceId: Long, accepted: Boolean): Long =
        propositionAcceptancesMutationPort.updateAcceptedFieldById(propositionAcceptanceId, accepted)

    override fun insertPropositionAcceptance(propositionAcceptance: PropositionAcceptance): Long =
        propositionAcceptancesMutationPort.insert(propositionAcceptance)

}
