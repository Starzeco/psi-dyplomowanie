package com.example.dyplomowaniebackend.api.controller

import com.example.dyplomowaniebackend.domain.candidature.port.api.CandidatureServicePort
import com.example.dyplomowaniebackend.domain.model.Candidature
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/candidature")
class CandidatureController(private val candidatureServicePort: CandidatureServicePort) {

    @GetMapping("{candidature_id}")
    fun getCandidatureById(@PathVariable(name = "candidature_id") candidatureId: Long): Candidature =
        candidatureServicePort.getCandidatureById(candidatureId)
}