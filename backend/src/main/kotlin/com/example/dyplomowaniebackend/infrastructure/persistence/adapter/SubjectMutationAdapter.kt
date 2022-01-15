package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.SubjectMutationPort
import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.domain.model.SubjectStatusUpdate
import com.example.dyplomowaniebackend.domain.model.SubjectUpdate
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToEntity
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.SubjectRepository
import org.springframework.stereotype.Service

@Service
class SubjectMutationAdapter(val subjectRepository: SubjectRepository) : SubjectMutationPort,
    com.example.dyplomowaniebackend.domain.verification.port.persistence.SubjectMutationPort {
    override fun saveSubject(subject: Subject): Subject =
        subjectRepository.save(subject.mapToEntity()).mapToDomain()

    override fun updateStatus(subjectStatusUpdate: SubjectStatusUpdate): SubjectStatusUpdate {
        subjectRepository.updateStatusById(subjectStatusUpdate.subjectId, subjectStatusUpdate.status)
        return subjectStatusUpdate
    }

    override fun updateSubject(updateSubject: SubjectUpdate): SubjectUpdate {
        subjectRepository.updateSubject(
            updateSubject.topic,
            updateSubject.topicInEnglish,
            updateSubject.objective,
            updateSubject.objectiveInEnglish,
            updateSubject.realiseresNumber
        )
        return updateSubject
    }
}