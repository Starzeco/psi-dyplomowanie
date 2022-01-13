package com.example.dyplomowaniebackend.domain.candidature.port.api

import com.example.dyplomowaniebackend.domain.model.Candidature
import com.example.dyplomowaniebackend.domain.model.CandidatureCreation

interface CandidatureServicePort {
    fun createCandidature(candidatureCreation: CandidatureCreation): Candidature
    fun decideAboutCandidatureAcceptance(candidatureAcceptanceId: Long, accepted: Boolean): Long
}
