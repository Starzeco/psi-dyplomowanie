package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.model.SubjectType
import com.example.dyplomowaniebackend.domain.model.Verification
import com.example.dyplomowaniebackend.domain.verification.port.persistence.VerificationSearchPort
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.VerificationRepository
import org.springframework.stereotype.Service

@Service
class VerificationSearchAdapter(val verificationRepository: VerificationRepository) : VerificationSearchPort {
    override fun findAllVerifications(
        verifierId: Long,
        phrase: String?,
        verified: Boolean?,
        subjectType: SubjectType?
    ): List<Verification> {
        val searchPhrase = "%${phrase.orEmpty()}%"
        val subjectStatusThreshold = 1
        return when (subjectType) {
            SubjectType.INDIVIDUAL -> verificationRepository.findByVerifierIdAndVerifiedAndSubjectTopicLikeAndSubjectRealiseresNumberEquals(
                verifierId,
                verified,
                searchPhrase,
                subjectStatusThreshold
            )
            SubjectType.GROUP -> verificationRepository.findByVerifierIdAndVerifiedAndSubjectTopicLikeAndSubjectRealiseresNumberGreaterThan(
                verifierId,
                verified,
                searchPhrase,
                subjectStatusThreshold
            )
            else -> verificationRepository.findByVerifierIdAndVerifiedAndSubjectTopicLike(
                verifierId,
                verified,
                searchPhrase
            )
        }.map { it.mapToDomain(true) }
    }

    override fun findSubjectVerifications(subjectId: Long): List<Verification> {
        return verificationRepository.findBySubjectSubjectId(subjectId).map { it.mapToDomain(true) }
    }
}
