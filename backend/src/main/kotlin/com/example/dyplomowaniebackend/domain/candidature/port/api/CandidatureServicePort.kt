package com.example.dyplomowaniebackend.domain.candidature.port.api

import com.example.dyplomowaniebackend.domain.model.*

interface CandidatureServicePort {
    fun createCandidature(candidatureCreation: CandidatureCreation): Candidature
    fun decideAboutCandidatureAcceptance(candidatureAcceptanceId: Long, accepted: Boolean): Long
    fun getAllCandidatureAsSupervisor(
        supervisorId: Long,
        graduationProcessId: Long,
        phrase: String?,
        type: CandidatureType?,
        status: CandidatureStatus?
    ): Set<Pair<Candidature, Set<CandidatureAcceptance>>>
    fun getAllCandidatureAsStudent(
        studentId: Long,
        graduationProcessId: Long,
        phrase: String?,
        type: CandidatureType?,
        status: CandidatureStatus?
    ): Set<Pair<Candidature, Set<CandidatureAcceptance>>>
}
