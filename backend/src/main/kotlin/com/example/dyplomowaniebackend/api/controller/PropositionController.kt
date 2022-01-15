package com.example.dyplomowaniebackend.api.controller

import com.example.dyplomowaniebackend.api.dto.PropositionAcceptancePartialInfoResponse
import com.example.dyplomowaniebackend.domain.submission.port.api.PropositionAcceptanceServicePort
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/propositions")
class PropositionController(
    private val propositionAcceptanceService: PropositionAcceptanceServicePort,
) {

    @GetMapping("/{student_id}/{graduation_process_id}")
    fun getAllByStudentIdAndGraduationProcessId(
        @PathVariable(name = "student_id") studentId: Long,
        @PathVariable(name = "graduation_process_id") graduationProcessId: Long,
    ): Set<PropositionAcceptancePartialInfoResponse> =
        propositionAcceptanceService
            .getAllPropositionAcceptancesByStudentIdAndGraduationProcessId(studentId, graduationProcessId)
            .map { PropositionAcceptancePartialInfoResponse.fromDomain(it) }
            .toSet()

    @PutMapping("{proposition_acceptance_id}")
    fun updateAcceptedById(
        @PathVariable(name = "proposition_acceptance_id") propositionAcceptanceId: Long,
        @RequestBody accepted: Boolean,
    ): Long =
        propositionAcceptanceService
            .updatePropositionAcceptanceAcceptedFieldById(propositionAcceptanceId, accepted)
}
