package com.example.dyplomowaniebackend.domain.candidature.port.persistance

import com.example.dyplomowaniebackend.domain.model.CandidatureAcceptance

interface CandidatureSearchPort {
    fun getCandidatureAcceptanceById(candidatureAcceptanceId: Long): CandidatureAcceptance
}
