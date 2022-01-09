package com.example.dyplomowaniebackend.domain.model

import java.time.Instant
import javax.persistence.*

data class Verification(
    val verificationId: Long? = null,
    val verified: Boolean? = null,
    val justification: String? = null,
    val updateDate: Instant? = null,
    val subject: Subject,
    val verifier: Verifier
)
