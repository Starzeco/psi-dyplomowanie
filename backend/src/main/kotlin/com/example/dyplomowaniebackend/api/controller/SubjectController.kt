package com.example.dyplomowaniebackend.api.controller

import com.example.dyplomowaniebackend.api.dto.PropositionAcceptancePartialInfoResponse
import com.example.dyplomowaniebackend.domain.candidature.port.api.CandidatureCreationPort
import com.example.dyplomowaniebackend.domain.graduationProcess.port.api.SubjectCreationPort
import com.example.dyplomowaniebackend.domain.model.CandidatureCreation
import com.example.dyplomowaniebackend.domain.model.SubjectCreation
import com.example.dyplomowaniebackend.domain.submission.port.api.PropositionAcceptanceServicePort
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/subject")
class SubjectController(
    private val propositionAcceptanceService: PropositionAcceptanceServicePort,
    private val subjectCreationPort: SubjectCreationPort,
    private val candidatureCreationPort: CandidatureCreationPort
) {

    @PostMapping
    fun createSubject(@RequestBody subjectCreation: SubjectCreation): Long =
        subjectCreationPort.createSubject(subjectCreation)

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
        @RequestParam(name = "accepted", required = true) accepted: Boolean,
    ): Long =
        propositionAcceptanceService
            .updatePropositionAcceptanceAcceptedFieldById(propositionAcceptanceId, accepted)

    @PostMapping("candidature")
    fun createCandidature(@RequestBody candidatureCreation: CandidatureCreation): Long =
        candidatureCreationPort.createCandidature(candidatureCreation)

}
