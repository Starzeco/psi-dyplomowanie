package com.example.dyplomowaniebackend.api.dto

data class VerificationDecisionRequest(
    val decision: Boolean,
    val justification: String
)
