package com.example.dyplomowaniebackend.api.controller

import com.example.dyplomowaniebackend.api.dto.CandidaturePartialInfoResponse
import com.example.dyplomowaniebackend.domain.candidature.port.api.CandidatureServicePort
import com.example.dyplomowaniebackend.domain.model.CandidatureStatus
import com.example.dyplomowaniebackend.domain.model.CandidatureType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/supervisor")
class SupervisorController(
    private val candidatureServicePort: CandidatureServicePort
) {

    //TODO: supervisorId could be passed from auth context - it can be stored within TOKEN
    @GetMapping("{supervisor_id}/graduation_process/{graduation_process_id}/candidature")
    fun getAllCandidatures(
        @PathVariable(name = "supervisor_id") supervisorId: Long,
        @PathVariable(name = "graduation_process_id") graduationProcessId: Long,
        @RequestParam(name = "phrase") phrase: String?,
        @RequestParam(name = "type") type: CandidatureType?,
        @RequestParam(name = "status") status: CandidatureStatus?
    ): Set<CandidaturePartialInfoResponse> = candidatureServicePort.getAllCandidatureAsSupervisor(
        supervisorId = supervisorId,
        graduationProcessId = graduationProcessId,
        phrase = phrase,
        type = type,
        status = status
    ).map { CandidaturePartialInfoResponse.fromDomain(it.first, it.second) }
        .toSet()

}
