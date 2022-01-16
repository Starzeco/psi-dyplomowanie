package com.example.dyplomowaniebackend.infrastructure.persistence.mapper

import com.example.dyplomowaniebackend.domain.model.Verifier
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.VerifierEntity

fun VerifierEntity.mapToDomain(): Verifier =
    Verifier(
        verifierId = this.verifierId,
        name = this.name,
        verificationsDeadline = this.verificationsDeadline,
        verifier = this.verifier!!.mapToDomain(),
        graduationProcess = this.graduationProcess!!.mapToDomain()
    )

fun Verifier.mapToEntity(): VerifierEntity =
    VerifierEntity(
        verifierId = this.verifierId,
        name = this.name,
        verificationsDeadline = this.verificationsDeadline,
        staffMemberId = this.verifier.staffMemberId!!,
        graduationProcessId = this.graduationProcess.graduationProcessId!!
    )