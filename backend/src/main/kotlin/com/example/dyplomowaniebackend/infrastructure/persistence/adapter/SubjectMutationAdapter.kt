package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.SubjectMutationPort
import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.domain.model.SubjectStatusUpdate
import com.example.dyplomowaniebackend.domain.model.exception.SubjectConstraintViolationException
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToEntity
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.SubjectRepository
import org.springframework.stereotype.Service

@Service
class SubjectMutationAdapter(val subjectRepository: SubjectRepository) : SubjectMutationPort {
    override fun insert(subject: Subject): Subject {
        val hasSubjectId = subject.subjectId != null
        if (hasSubjectId) throw SubjectConstraintViolationException(
            "Can not insert a subject with id: ${subject.subjectId}"
        )
        return subjectRepository.save(subject.mapToEntity()).mapToDomain()
    }

    override fun updateStatus(subjectStatusUpdate: SubjectStatusUpdate): SubjectStatusUpdate {
        subjectRepository.updateStatusById(subjectStatusUpdate.subjectId, subjectStatusUpdate.status)
        return subjectStatusUpdate
    }

    override fun updateInitiatorIdById(subjectId: Long, initiatorId: Long): Long =
        subjectRepository.updateInitiatorIdById(subjectId, initiatorId).toLong()
}
