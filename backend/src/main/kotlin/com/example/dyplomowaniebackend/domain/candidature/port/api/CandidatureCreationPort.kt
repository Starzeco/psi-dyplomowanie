package com.example.dyplomowaniebackend.domain.candidature.port.api

import com.example.dyplomowaniebackend.domain.model.CandidatureCreation

interface CandidatureCreationPort {
    fun createCandidature(candidatureCreation: CandidatureCreation): Long
}
