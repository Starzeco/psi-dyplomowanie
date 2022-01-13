package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.StudentMutationPort
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.StudentRepository
import org.springframework.stereotype.Service

@Service
class StudentMutationAdapter(val studentRepository: StudentRepository) : StudentMutationPort {
    override fun updateSubjectIdByStudentIdIn(studentIds: Set<Long>, subjectId: Long): Set<Long> {
        studentRepository.updateSubjectIdByStudentIdIn(studentIds, subjectId)
        return studentIds
    }
}