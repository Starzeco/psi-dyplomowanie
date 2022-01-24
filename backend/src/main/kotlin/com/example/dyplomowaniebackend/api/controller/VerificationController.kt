package com.example.dyplomowaniebackend.api.controller

import com.example.dyplomowaniebackend.domain.model.Verification
import com.example.dyplomowaniebackend.domain.verification.port.api.VerificationMutationPort
import com.example.dyplomowaniebackend.domain.verification.port.api.VerificationSearchPort
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/verification")
class VerificationController(val verificationSearchPort: VerificationSearchPort,
                             val verificationMutationPort: VerificationMutationPort) {

    @GetMapping
    fun findAllVerifications(@RequestParam verifierId: Long,
                             @RequestParam(required = false) phrase: String?,
                             @RequestParam(required = false) verified: Boolean?): List<Verification> =
        verificationSearchPort.findAllVerifications(verifierId, phrase, verified)

    @PutMapping
    fun verifyVerification(@RequestParam verificationId: Long, @RequestParam decision: Boolean, @RequestParam justification: String): Verification =
        verificationMutationPort.verifyVerification(verificationId, decision, justification)

    @PutMapping("all")
    fun verifyAllVerifications(@RequestParam verifierId: Long, @RequestParam decision: Boolean, @RequestParam justification: String): List<Verification> =
        verificationMutationPort.verifyAllVerifications(verifierId, decision, justification)
}
