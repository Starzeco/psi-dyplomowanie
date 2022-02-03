package com.example.dyplomowaniebackend.domain.submission.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.mail.MailSenderPort
import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance
import com.example.dyplomowaniebackend.domain.submission.port.api.PropositionAcceptanceServicePort
import com.example.dyplomowaniebackend.domain.submission.port.persistence.PropositionAcceptanceMutationPort
import com.example.dyplomowaniebackend.domain.submission.port.persistence.PropositionAcceptanceSearchPort
import org.springframework.stereotype.Component

@Component
class PropositionAcceptanceServiceAdapter(
    private val propositionAcceptanceSearchPort: PropositionAcceptanceSearchPort,
    private val propositionAcceptanceMutationPort: PropositionAcceptanceMutationPort,
    private val mailSenderPort: MailSenderPort,
) : PropositionAcceptanceServicePort {

    override fun getAllPropositionAcceptancesByStudentIdAndGraduationProcessId(studentId: Long, graduationProcessId: Long): Set<PropositionAcceptance> =
        propositionAcceptanceSearchPort.getAllByStudentIdAndGraduationProcessId(studentId, graduationProcessId)

    override fun updatePropositionAcceptanceAcceptedFieldById(propositionAcceptanceId: Long, accepted: Boolean): Long {
        mailSenderPort.sendMail("242422@student.pwr.edu.pl", "Została podjęta decyzja na temat jednej z propozycji realizacji tematu stworzonego przez Ciebie.")
        return propositionAcceptanceMutationPort.updateAcceptedFieldById(propositionAcceptanceId, accepted)
    }

}
