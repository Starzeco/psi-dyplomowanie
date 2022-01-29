package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.model.SubjectType
import com.example.dyplomowaniebackend.domain.model.Verification
import com.example.dyplomowaniebackend.domain.model.Verifier
import com.example.dyplomowaniebackend.domain.model.exception.VerificationConstraintViolationException
import com.example.dyplomowaniebackend.domain.verification.port.persistence.VerificationSearchPort
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.VerificationRepository
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.VerifierRepository
import org.springframework.stereotype.Service

@Service
class VerificationSearchAdapter(
    private val verificationRepository: VerificationRepository,
    private val verifierRepository: VerifierRepository
) : VerificationSearchPort {
    override fun findAllVerificationAsVerifier(verifierId: Long, verificationId: Long): Verification {
        val verification = verificationRepository.findByVerificationIdAndVerifierId(verificationId, verifierId)
        if (verification == null)
            throw VerificationConstraintViolationException("Verifier $verifierId can not find verification $verificationId")
        else
            return verification.mapToDomain(true)
    }

    override fun findAllVerifiersOfStaffMember(staffMemberId: Long): List<Verifier> =
        verifierRepository.findAllByStaffMemberId(staffMemberId).map { it.mapToDomain() }

    override fun findAllVerifications(
        verifierId: Long,
        phrase: String?,
        verified: Boolean?,
        subjectType: SubjectType?
    ): List<Verification> {
        val searchPhrase = "%${phrase.orEmpty()}%"
        return verificationRepository.findByVerifierIdAndVerifiedAndSubjectTopicLike(
            verifierId,
            verified,
            searchPhrase
        ).filter {
            when (subjectType) {
                SubjectType.GROUP -> it.subject!!.realiseresNumber > 1
                SubjectType.INDIVIDUAL -> it.subject!!.realiseresNumber == 1
                else -> true
            }
        }.map { it.mapToDomain(true) }
    }

    override fun findSubjectVerifications(subjectId: Long): List<Verification> {
        return verificationRepository.findBySubjectSubjectId(subjectId).map { it.mapToDomain(true) }
    }
}
