package com.example.dyplomowaniebackend.api.dto

import com.example.dyplomowaniebackend.domain.model.Verifier
import java.time.Instant

data class VerifierPartialInfoResponse(
    val verifierId: Long,
    val name: String,
    val verificationsDeadline: Instant,
    val graduationProcessPartialInfo: GraduationProcessPartialInfoResponse
) {
    companion object {
        fun fromDomain(verifier: Verifier): VerifierPartialInfoResponse = VerifierPartialInfoResponse(
            verifierId = verifier.verifierId!!,
            name = verifier.name,
            verificationsDeadline = verifier.verificationsDeadline,
            graduationProcessPartialInfo = GraduationProcessPartialInfoResponse.fromDomain(verifier.graduationProcess)
        )
    }
}

