package com.example.dyplomowaniebackend.domain.verification.adapter

import com.example.dyplomowaniebackend.domain.model.Verification
import org.springframework.stereotype.Service

@Service("verificationSearchAdapterApi")
class VerificationSearchAdapter(val verificationSearchPort: com.example.dyplomowaniebackend.domain.verification.port.persistence.VerificationSearchPort) : com.example.dyplomowaniebackend.domain.verification.port.api.VerificationSearchPort {
    override fun findAllVerifications(verifierId: Long, phrase: String, verified: Boolean?): List<Verification> =
        verificationSearchPort.findAllVerifications(verifierId, phrase, verified)
}