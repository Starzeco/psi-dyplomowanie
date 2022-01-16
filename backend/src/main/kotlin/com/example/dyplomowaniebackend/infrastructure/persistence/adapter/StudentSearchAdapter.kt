package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.StudentSearchPort
import com.example.dyplomowaniebackend.domain.model.Student
import com.example.dyplomowaniebackend.domain.model.exception.EntityNotFoundException
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.StudentRepository
import org.springframework.stereotype.Service

@Service
class StudentSearchAdapter(val studentRepository: StudentRepository) : StudentSearchPort {
    override fun findById(studentId: Long): Student? =
        studentRepository.findById(studentId)
            .map { student -> student.mapToDomain() }
            .orElse(null)

    override fun getById(studentId: Long): Student =
        studentRepository.findById(studentId)
            .map { staff -> staff.mapToDomain() }
            .orElseThrow { throw EntityNotFoundException(Student::class, studentId) }

    override fun findAllByStudentIdInAndSubjectIdNotNull(studentIds: Set<Long>): Set<Student> =
        studentRepository.findAllByStudentIdIsInAndStudentIdIsNotNull(studentIds)
            .map { it.mapToDomain() }
            .toSet()

    override fun existsByStudentIdAndSubjectIdNotNull(studentId: Long): Boolean =
        studentRepository.existsByStudentIdAndSubjectIdNotNull(studentId)

    override fun existsAllBySubjectId(subjectId: Long): Boolean =
        studentRepository.existsAllBySubjectId(subjectId)
}
