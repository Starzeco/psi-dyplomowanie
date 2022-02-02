package com.example.dyplomowaniebackend.domain.candidature.port.persistance

import com.example.dyplomowaniebackend.domain.model.Candidature
import com.example.dyplomowaniebackend.domain.model.CandidatureAcceptance
import com.example.dyplomowaniebackend.domain.model.CandidatureStatus
import com.example.dyplomowaniebackend.domain.model.CandidatureType

interface CandidatureSearchPort {
    fun existsCandidatureAcceptancesByCandidatureIdAndAcceptedIsFalseOrAcceptedIsNull(candidatureId: Long): Boolean
    fun getCandidatureAcceptanceById(candidatureAcceptanceId: Long): CandidatureAcceptance
    fun getCandidatureAcceptanceByCandidatureId(candidatureId: Long): Set<CandidatureAcceptance>
    fun getAllCandidatureAsSupervisor(
        supervisorId: Long,
        graduationProcessId: Long,
        phrase: String?,
        type: CandidatureType?,
        status: CandidatureStatus?
    ): Set<Candidature>
    fun getAllCandidatureAsStudent(
        studentId: Long,
        graduationProcessId: Long,
        phrase: String?,
        type: CandidatureType?,
        status: CandidatureStatus?
    ): Set<Candidature>
    fun getCandidatureById(candidatureId: Long): Candidature
    fun getCandidaturesBySubjectId(subjectId: Long): Set<Candidature>
}
