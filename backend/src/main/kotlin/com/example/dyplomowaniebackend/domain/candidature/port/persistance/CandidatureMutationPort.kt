package com.example.dyplomowaniebackend.domain.candidature.port.persistance

import com.example.dyplomowaniebackend.domain.model.Candidature
import com.example.dyplomowaniebackend.domain.model.CandidatureAcceptance

interface CandidatureMutationPort {
    fun insert(candidature: Candidature): Candidature
    fun insertAcceptances(candidatureAcceptances: Set<CandidatureAcceptance>): Set<CandidatureAcceptance>
    fun updateAcceptanceAcceptedById(
        candidatureAcceptanceId: Long,
        accepted: Boolean
    ): Long
}
