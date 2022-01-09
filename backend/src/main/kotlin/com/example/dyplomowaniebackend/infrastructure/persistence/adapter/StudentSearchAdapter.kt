package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.StudentSearchPort
import com.example.dyplomowaniebackend.domain.model.Student
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapCreation
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.StudentRepository
import org.springframework.stereotype.Service

@Service
class StudentSearchAdapter(val studentRepository: StudentRepository) : StudentSearchPort {
    override fun getStudentById(studentId: Long?): Student? {
        if(studentId == null) return null
        return studentRepository.findById(studentId).map { student -> student.mapCreation() }.orElse(null)
    }
}