package com.example.dyplomowaniebackend.domain.model


data class PropositionAcceptance(
    val propositionAcceptanceId: Long,
    val accepted: Boolean? = null,
    val student: Student,
    val subject: Subject,
)
