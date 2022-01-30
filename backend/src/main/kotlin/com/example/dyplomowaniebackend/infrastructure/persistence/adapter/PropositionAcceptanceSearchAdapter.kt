package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance
import com.example.dyplomowaniebackend.domain.submission.port.persistence.PropositionAcceptanceSearchPort
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.PropositionAcceptanceRepository
import org.springframework.stereotype.Service

@Service
class PropositionAcceptanceSearchAdapter(
    private val propositionAcceptanceRepository: PropositionAcceptanceRepository
) : PropositionAcceptanceSearchPort, com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.PropositionAcceptanceSearchPort {

    override fun getAllByStudentIdAndGraduationProcessId(
        studentId: Long,
        graduationProcessId: Long
    ): Set<PropositionAcceptance> =
        propositionAcceptanceRepository.findByStudentIdAndGraduationProcessId(studentId, graduationProcessId)
            .map { it.mapToDomain() }
            .toSet()

    override fun getAllBySubjectId(subjectId: Long): Set<PropositionAcceptance> =
        propositionAcceptanceRepository.findBySubjectSubjectId(subjectId)
            .map { it.mapToDomain(true) }
            .toSet()
}
