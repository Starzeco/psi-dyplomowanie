package com.example.dyplomowaniebackend.domain.model


data class PropositionAcceptance(
    val propositionAcceptanceId: Long? = null,
    val accepted: Boolean? = null,
    val student: Student,
    val subject: Subject,
)
