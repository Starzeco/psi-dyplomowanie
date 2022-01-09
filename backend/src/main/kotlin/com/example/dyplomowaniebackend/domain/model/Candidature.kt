package com.example.dyplomowaniebackend.domain.model

import java.time.Instant

data class Candidature(
    val candidatureId: Long? = null,
    val accepted: Boolean? = null,
    val creationDate: Instant = Instant.now(),
    val student: Student,
    val candidatureAcceptances: Set<CandidatureAcceptance>,
    val subject: Subject
)

class CandidatureAcceptance(
    val candidatureAcceptanceId: Long? = null,
    val accepted: Boolean? = null,
    val student: Student,
    val candidature: Candidature
)
