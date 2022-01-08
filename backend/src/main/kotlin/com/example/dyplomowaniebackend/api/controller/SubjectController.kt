package com.example.dyplomowaniebackend.api.controller

import com.example.dyplomowaniebackend.api.dto.PropositionAcceptancePartialInfoResponse
import com.example.dyplomowaniebackend.domain.submission.port.PropositionAcceptanceServicePort
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/subject")
class SubjectController(
    private val propositionAcceptanceService: PropositionAcceptanceServicePort
) {

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


}

