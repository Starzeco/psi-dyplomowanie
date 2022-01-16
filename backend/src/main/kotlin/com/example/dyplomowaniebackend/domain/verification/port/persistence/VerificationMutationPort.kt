package com.example.dyplomowaniebackend.domain.verification.port.persistence

import com.example.dyplomowaniebackend.domain.model.Verification

interface VerificationMutationPort {
    fun verifyVerification(verificationId: Long, decision: Boolean, justification: String): Verification
    fun verifyAllVerifications(verifierId: Long, decision: Boolean, justification: String): List<Verification>
}