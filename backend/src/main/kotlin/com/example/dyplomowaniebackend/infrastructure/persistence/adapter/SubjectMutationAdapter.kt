package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.SubjectMutationPort
import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToEntity
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.SubjectRepository
import org.springframework.stereotype.Service

@Service
class SubjectMutationAdapter(val subjectRepository: SubjectRepository) : SubjectMutationPort {
    override fun saveSubject(subject: Subject): Subject {
        return subjectRepository.save(subject.mapToEntity()).mapToDomain()
    }
}