package com.example.dyplomowaniebackend.domain.submission.adapter

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance
import com.example.dyplomowaniebackend.domain.submission.port.api.PropositionAcceptanceServicePort
import com.example.dyplomowaniebackend.domain.submission.port.persistence.PropositionAcceptanceMutationPort
import com.example.dyplomowaniebackend.domain.submission.port.persistence.PropositionAcceptanceSearchPort
import org.springframework.stereotype.Component

@Component
class PropositionAcceptanceServiceAdapter(
    private val propositionAcceptanceSearchPort: PropositionAcceptanceSearchPort,
    private val propositionAcceptanceMutationPort: PropositionAcceptanceMutationPort
) : PropositionAcceptanceServicePort {

    override fun getAllPropositionAcceptancesByStudentIdAndGraduationProcessId(studentId: Long, graduationProcessId: Long): Set<PropositionAcceptance> =
        propositionAcceptanceSearchPort.getAllByStudentIdAndGraduationProcessId(studentId, graduationProcessId)

    override fun updatePropositionAcceptanceAcceptedFieldById(propositionAcceptanceId: Long, accepted: Boolean): Long =
        propositionAcceptanceMutationPort.updateAcceptedFieldById(propositionAcceptanceId, accepted)

}
