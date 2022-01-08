package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.SubjectMutationPort
import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.SubjectEntity
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapCreation
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapCreationEntity
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.SubjectRepository
import org.springframework.stereotype.Service

@Service
class SubjectMutationAdapter(val subjectRepository: SubjectRepository) : SubjectMutationPort {
    override fun saveSubject(subject: Subject): Subject {
        val entity = subject.mapCreationEntity()
        val sub: SubjectEntity = subjectRepository.saveAndFlush(entity)
        val yo = subjectRepository.findById(1)
        return sub.mapCreation()
    }
}