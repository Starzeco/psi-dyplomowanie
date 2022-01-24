package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.VerificationMutationPort
import com.example.dyplomowaniebackend.domain.model.Verification
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToEntity
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.VerificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class VerificationMutationAdapter(val verificationRepository: VerificationRepository) : VerificationMutationPort, com.example.dyplomowaniebackend.domain.verification.port.persistence.VerificationMutationPort {
    override fun insertAll(verifications: List<Verification>): List<Verification> {
        return verificationRepository.saveAll(verifications.map { it.mapToEntity() }).map { it.mapToDomain(true) }
    }

    override fun updateOldVerifications(subjectId: Long) {
        verificationRepository.updateOldVerifications(subjectId)
    }

    override fun verifyVerification(verificationId: Long, decision: Boolean, justification: String): Verification {
        verificationRepository.updateVerifiedByVerificationId(verificationId, decision, justification)
        return verificationRepository.findById(verificationId).get().mapToDomain(true)
    }

    override fun verifyAllVerifications(verifierId: Long, decision: Boolean, justification: String): List<Verification> {
        verificationRepository.updateVerifiedByVerifierIdAndVerifiedIsNull(verifierId, decision, justification)
        return verificationRepository.findByVerifierVerifierId(verifierId).map { it.mapToDomain(true) }
    }
}
