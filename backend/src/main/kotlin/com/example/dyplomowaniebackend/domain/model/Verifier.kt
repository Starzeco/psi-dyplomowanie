package com.example.dyplomowaniebackend.domain.model

import java.time.Instant

data class Verifier(
    val verifierId: Long? = null,
    val name: String,
    val verificationsDeadline: Instant,
    val verifier: StaffMember,
    val graduationProcess: GraduationProcess,
    //val verifications: Set<Verification>
)
