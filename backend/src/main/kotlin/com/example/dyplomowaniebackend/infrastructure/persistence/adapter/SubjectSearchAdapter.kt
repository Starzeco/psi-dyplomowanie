package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.domain.model.SubjectStatus
import com.example.dyplomowaniebackend.domain.model.exception.EntityNotFoundException
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.SubjectRepository
import org.springframework.stereotype.Service
import com.example.dyplomowaniebackend.domain.candidature.port.persistance.SubjectSearchPort as SubjectSearchPortCandidature
import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.SubjectSearchPort as SubjectSearchPortGraduationProcess

@Service
class SubjectSearchAdapter(private val subjectRepository: SubjectRepository) : SubjectSearchPortCandidature, SubjectSearchPortGraduationProcess {
    override fun getById(subjectId: Long, cut: Boolean): Subject =
        subjectRepository.findById(subjectId)
            .map { sub -> sub.mapToDomain(cut) }
            .orElseThrow{ throw EntityNotFoundException(Subject::class, subjectId) }

    override fun getSubjectsInStatus(subjectStatuses: List<SubjectStatus>): Set<Subject> {
        return subjectRepository.findAllByStatusIn(subjectStatuses).map { it.mapToDomain(true) }.toSet()
    }
}
