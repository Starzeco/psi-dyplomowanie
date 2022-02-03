package com.example.dyplomowaniebackend.domain.verification.adapter

import com.example.dyplomowaniebackend.domain.model.SubjectType
import com.example.dyplomowaniebackend.domain.model.Verification
import com.example.dyplomowaniebackend.domain.model.Verifier
import org.springframework.stereotype.Service

@Service("verificationSearchAdapterApi")
class VerificationSearchAdapter(val verificationSearchPort: com.example.dyplomowaniebackend.domain.verification.port.persistence.VerificationSearchPort) :
    com.example.dyplomowaniebackend.domain.verification.port.api.VerificationSearchPort {
    override fun findAllRejectedVerificationBySubjectId(subjectId: Long): List<Verification> =
        verificationSearchPort.findAllRejectedVerificationBySubjectId(subjectId)

    override fun findVerificationAsVerifier(verifierId: Long, verificationId: Long): Verification =
        verificationSearchPort.findAllVerificationAsVerifier(verifierId, verificationId)

    override fun findAllVerifiersOfStaffMember(staffMemberId: Long): List<Verifier> =
        verificationSearchPort.findAllVerifiersOfStaffMember(staffMemberId)

    override fun findAllVerifications(
        verifierId: Long,
        phrase: String?,
        verified: Boolean?,
        subjectType: SubjectType?
    ): List<Verification> =
        verificationSearchPort.findAllVerifications(verifierId, phrase, verified, subjectType)
}
