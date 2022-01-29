package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.candidature.port.persistance.CandidatureSearchPort
import com.example.dyplomowaniebackend.domain.model.Candidature
import com.example.dyplomowaniebackend.domain.model.CandidatureAcceptance
import com.example.dyplomowaniebackend.domain.model.CandidatureStatus
import com.example.dyplomowaniebackend.domain.model.CandidatureType
import com.example.dyplomowaniebackend.domain.model.exception.EntityNotFoundException
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.CandidatureAcceptanceRepository
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.CandidatureRepository
import org.springframework.stereotype.Service

@Service
class CandidatureSearchAdapter(
    private val candidatureRepository: CandidatureRepository,
    private val candidatureAcceptanceRepository: CandidatureAcceptanceRepository
) : CandidatureSearchPort {
    override fun existsCandidatureAcceptancesByCandidatureIdAndAcceptedIsFalseOrAcceptedIsNull(candidatureId: Long): Boolean =
        candidatureAcceptanceRepository.existsByCandidatureIdAndAcceptedIsFalseOrAcceptedIsNull(candidatureId)

    override fun getCandidatureAcceptanceById(candidatureAcceptanceId: Long): CandidatureAcceptance =
        candidatureAcceptanceRepository.findById(candidatureAcceptanceId)
            .map { it.mapToDomain() }
            .orElseThrow { throw EntityNotFoundException(CandidatureAcceptance::class, candidatureAcceptanceId) }

    override fun getCandidatureAcceptanceByCandidatureId(candidatureId: Long): Set<CandidatureAcceptance> =
        candidatureAcceptanceRepository.findAllByCandidatureId(candidatureId)
            .map { it.mapToDomain(true) }
            .toSet()

    override fun getAllCandidatureAsSupervisor(
        supervisorId: Long,
        graduationProcessId: Long,
        phrase: String?,
        type: CandidatureType?,
        status: CandidatureStatus?
    ): Set<Candidature> =
        candidatureRepository.getAllCandidaturesBySupervisorIdAndGraduationProcessIdAndAcceptedCandidaturesAcceptances(
            supervisorId,
            graduationProcessId,
            phrase,
            type,
            status
        ).map { it.mapToDomain() }
            .toSet()

    override fun getAllCandidatureAsStudent(
        studentId: Long,
        graduationProcessId: Long,
        phrase: String?,
        type: CandidatureType?,
        status: CandidatureStatus?
    ): Set<Candidature> =
        candidatureRepository.getAllCandidaturesByStudentIdAndGraduationProcessIdAndAcceptedCandidaturesAcceptances(
            studentId,
            graduationProcessId,
            phrase,
            type,
            status
        ).map { it.mapToDomain(true) }
            .toSet()

    override fun getCandidatureById(candidatureId: Long): Candidature =
        candidatureRepository.findById(candidatureId)
            .map { it.mapToDomain() }
            .orElseThrow { throw EntityNotFoundException(Candidature::class, candidatureId) }

}
