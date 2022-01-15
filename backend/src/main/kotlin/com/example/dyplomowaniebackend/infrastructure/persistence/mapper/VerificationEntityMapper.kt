package com.example.dyplomowaniebackend.infrastructure.persistence.mapper

import com.example.dyplomowaniebackend.domain.model.Verification
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.VerificationEntity

fun VerificationEntity.mapToDomain(cut: Boolean = false): Verification =
    Verification(
        verificationId = this.verificationId,
        verified = this.verified,
        justification = this.justification,
        updateDate = this.updateDate,
        subject = this.subject!!.mapToDomain(cut),
        verifier = this.verifier!!.mapToDomain(),
    )

fun Verification.mapToEntity(): VerificationEntity =
    VerificationEntity(
        verificationId = this.verificationId,
        verified = this.verified,
        justification = this.justification,
        updateDate = this.updateDate,
        subjectId = this.subject.subjectId!!,
        verifierId = this.verifier.verifierId!!
    )