package com.example.dyplomowaniebackend.api.controller

import com.example.dyplomowaniebackend.api.dto.VerificationDecisionRequest
import com.example.dyplomowaniebackend.api.dto.VerifierPartialInfoResponse
import com.example.dyplomowaniebackend.domain.model.SubjectType
import com.example.dyplomowaniebackend.domain.model.Verification
import com.example.dyplomowaniebackend.domain.verification.port.api.VerificationMutationPort
import com.example.dyplomowaniebackend.domain.verification.port.api.VerificationSearchPort
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/verifier")
class VerifierController(
    val verificationSearchPort: VerificationSearchPort,
    val verificationMutationPort: VerificationMutationPort
) {

    @GetMapping("{staff_member_id}")
    fun findAllVerifiersOfStaffMember(
        @PathVariable(name = "staff_member_id") staffMemberId: Long,
    ): List<VerifierPartialInfoResponse> =
        verificationSearchPort.findAllVerifiersOfStaffMember(staffMemberId).map {
            VerifierPartialInfoResponse.fromDomain(it)
        }

    @GetMapping("{verifier_id}/verifications")
    fun findAllVerifications(
        @PathVariable(name = "verifier_id") verifierId: Long,
        @RequestParam(required = false) phrase: String?,
        @RequestParam(required = false) verified: Boolean?,
        @RequestParam(required = false, name = "subject_type") subjectType: SubjectType?,
    ): List<Verification> =
        verificationSearchPort.findAllVerifications(verifierId, phrase, verified, subjectType)

    @PutMapping
    fun verifyVerification(
        @RequestParam verificationId: Long,
        @RequestParam decision: Boolean,
        @RequestParam justification: String
    ): Verification =
        verificationMutationPort.verifyVerification(verificationId, decision, justification)

    @PutMapping("{verifier_id}/verifications")
    fun verifyAllVerifications(
        @PathVariable(name = "verifier_id") verifierId: Long,
        @RequestBody verificationDecisionRequest: VerificationDecisionRequest
    ): List<Verification> =
        verificationMutationPort.verifyAllVerifications(
            verifierId,
            verificationDecisionRequest.decision,
            verificationDecisionRequest.justification
        )
}
