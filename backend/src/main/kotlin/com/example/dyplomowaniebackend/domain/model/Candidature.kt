package com.example.dyplomowaniebackend.domain.model

import java.time.Instant

data class Candidature(
    val candidatureId: Long? = null,
    val accepted: Boolean? = null,
    val student: Student,
//    val candidatureAcceptances: Set<CandidatureAcceptance>,
    val subject: Subject,
    val creationDate: Instant = Instant.now(),
    )

data class CandidatureAcceptance(
    val candidatureAcceptanceId: Long? = null,
    val accepted: Boolean? = null,
    val student: Student,
    val candidature: Candidature
)

data class CandidatureCreation (
    val studentId: Long,
    val subjectId: Long,
    val coauthors: Set<Long>
)
