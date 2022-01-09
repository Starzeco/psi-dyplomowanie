package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.StudentSearchPort
import com.example.dyplomowaniebackend.domain.model.Student
import com.example.dyplomowaniebackend.domain.model.exception.EntityNotFoundException
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.StudentRepository
import org.springframework.stereotype.Service

@Service
class StudentSearchAdapter(val studentRepository: StudentRepository) : StudentSearchPort {
    override fun findStudentById(studentId: Long): Student? =
        studentRepository.findById(studentId)
            .map { student -> student.mapToDomain() }
            .orElse(null)

    override fun getStudentById(studentId: Long): Student =
        studentRepository.findById(studentId)
            .map { staff -> staff.mapToDomain() }
            .orElseThrow{ throw EntityNotFoundException(Student::class, studentId) }
}