package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.model.Verification
import com.example.dyplomowaniebackend.domain.verification.port.persistence.VerificationSearchPort
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.VerificationRepository
import org.springframework.stereotype.Service

@Service
class VerificationSearchAdapter(val verificationRepository: VerificationRepository) : VerificationSearchPort {
    override fun findAllVerifications(verifierId: Long, phrase: String?, verified: Boolean?): List<Verification> {
        val searchPhrase = "%${phrase.orEmpty()}%"
        return verificationRepository.findByVerifierIdAndVerifiedAndSubjectTopicLike(verifierId, verified, searchPhrase).map { it.mapToDomain(true) }
    }

    override fun findSubjectVerifications(subjectId: Long): List<Verification> {
        return verificationRepository.findBySubjectSubjectId(subjectId).map { it.mapToDomain(true) }
    }
}
