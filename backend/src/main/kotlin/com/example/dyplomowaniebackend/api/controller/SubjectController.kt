package com.example.dyplomowaniebackend.api.controller

import com.example.dyplomowaniebackend.domain.candidature.port.api.CandidatureServicePort
import com.example.dyplomowaniebackend.domain.graduationProcess.port.api.SubjectCreationPort
import com.example.dyplomowaniebackend.domain.model.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/subject")
class SubjectController(
    private val subjectCreationPort: SubjectCreationPort,
    private val candidatureServicePort: CandidatureServicePort
) {

    @PostMapping
    fun createSubject(@RequestBody subjectCreation: SubjectCreation): Subject =
        subjectCreationPort.createSubject(subjectCreation)

    @PutMapping
    fun updateSubject(@RequestBody updateSubject: SubjectUpdate): SubjectUpdate =
        subjectCreationPort.updateSubject(updateSubject)

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

    @PostMapping("candidature")
    fun createCandidature(@RequestBody candidatureCreation: CandidatureCreation): Candidature =
        candidatureServicePort.createCandidature(candidatureCreation)

    @PutMapping("candidature/{candidature_id}")
    fun decideAboutCandidature(
        @PathVariable(name = "candidature_id") candidatureId: Long,
        @RequestBody accepted: Boolean,
    ): Long =
        candidatureServicePort.decideAboutCandidature(candidatureId, accepted)

    @PutMapping("candidature_acceptance/{candidature_acceptance_id}")
    fun decideAboutCandidatureAcceptance(
        @PathVariable(name = "candidature_acceptance_id") candidatureAcceptanceId: Long,
        @RequestBody accepted: Boolean,
    ): Long =
        candidatureServicePort.decideAboutCandidatureAcceptance(candidatureAcceptanceId, accepted)

}
