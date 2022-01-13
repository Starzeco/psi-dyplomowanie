package com.example.dyplomowaniebackend.api.controller

import com.example.dyplomowaniebackend.api.dto.PropositionAcceptancePartialInfoResponse
import com.example.dyplomowaniebackend.domain.candidature.port.api.CandidatureServicePort
import com.example.dyplomowaniebackend.domain.graduationProcess.port.api.SubjectCreationPort
import com.example.dyplomowaniebackend.domain.model.CandidatureCreation
import com.example.dyplomowaniebackend.domain.model.SubjectCreation
import com.example.dyplomowaniebackend.api.dto.PropositionAcceptancePartialInfoResponse
import com.example.dyplomowaniebackend.domain.model.SubjectStatusUpdate
import com.example.dyplomowaniebackend.domain.submission.port.api.PropositionAcceptanceServicePort
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/subject")
class SubjectController(
    private val propositionAcceptanceService: PropositionAcceptanceServicePort,
    private val subjectCreationPort: SubjectCreationPort,
    private val candidatureServicePort: CandidatureServicePort
) {

    @PostMapping
    fun createSubject(@RequestBody subjectCreation: SubjectCreation): Long =
        subjectCreationPort.createSubject(subjectCreation)

    @PutMapping("status/reject/{subject_id}")
    fun rejectSubject(@PathVariable(name = "subject_id") subjectId: Long): SubjectStatusUpdate =
        subjectCreationPort.rejectSubject(subjectId)

    // TODO only supervisor can trigger
    @PutMapping("status/accept-supervisor/{subject_id}")
    fun acceptSupervisorSubject(@PathVariable(name = "subject_id") subjectId: Long): SubjectStatusUpdate =
        subjectCreationPort.acceptSupervisorSubject(subjectId)

    // TODO only initiator/student can trigger
    @PutMapping("status/accept-initiator/{subject_id}")
    fun acceptInitiatorSubject(@PathVariable(name = "subject_id") subjectId: Long): SubjectStatusUpdate =
        subjectCreationPort.acceptInitiatorSubject(subjectId)

    // TODO only supervisor can trigger
    @PutMapping("status/send-verification/{subject_id}")
    fun sendToVerificationSubject(@PathVariable(name = "subject_id") subjectId: Long): SubjectStatusUpdate =
        subjectCreationPort.sendToVerificationSubject(subjectId)

    @GetMapping("propositions/{student_id}")
    fun getAllPropositionAcceptancesByStudentId(
        @PathVariable(name = "student_id") studentId: Long,
    ): Set<PropositionAcceptancePartialInfoResponse> =
        propositionAcceptanceService
            .getAllPropositionAcceptancesByStudentId(studentId)
            .map { PropositionAcceptancePartialInfoResponse.fromDomain(it) }
            .toSet()

    @PutMapping("propositions/{proposition_acceptance_id}")
    fun updatePropositionAcceptanceAcceptedFieldById(
        @PathVariable(name = "proposition_acceptance_id") propositionAcceptanceId: Long,
        @RequestBody accepted: Boolean,
    ): Long =
        propositionAcceptanceService
            .updatePropositionAcceptanceAcceptedFieldById(propositionAcceptanceId, accepted)

    @PostMapping("candidature")
    fun createCandidature(@RequestBody candidatureCreation: CandidatureCreation): Long =
        candidatureServicePort.createCandidature(candidatureCreation)

    @PutMapping("candidature_acceptance/{candidature_acceptance_id}")
    fun decideAboutCandidatureAcceptance(
        @PathVariable(name = "candidature_acceptance_id") candidatureAcceptanceId: Long,
        @RequestBody accepted: Boolean,
    ): Long =
        candidatureServicePort.decideAboutCandidatureAcceptance(candidatureAcceptanceId, accepted)

}
