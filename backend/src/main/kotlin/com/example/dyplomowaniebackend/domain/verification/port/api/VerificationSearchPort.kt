package com.example.dyplomowaniebackend.domain.verification.port.api

import com.example.dyplomowaniebackend.domain.model.SubjectType
import com.example.dyplomowaniebackend.domain.model.Verification
import com.example.dyplomowaniebackend.domain.model.Verifier

interface VerificationSearchPort {
    fun findVerificationAsVerifier(verifierId: Long, verificationId: Long): Verification
    fun findAllVerifiersOfStaffMember(staffMemberId: Long): List<Verifier>
    fun findAllVerifications(
        verifierId: Long,
        phrase: String?,
        verified: Boolean?,
        subjectType: SubjectType?
    ): List<Verification>
}
