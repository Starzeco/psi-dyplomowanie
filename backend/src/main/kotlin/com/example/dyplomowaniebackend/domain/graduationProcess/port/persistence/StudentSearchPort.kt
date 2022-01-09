package com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence

import com.example.dyplomowaniebackend.domain.model.Student
import com.example.dyplomowaniebackend.domain.model.exception.EntityNotFoundException

interface StudentSearchPort {
    fun findStudentById(studentId: Long): Student?
    @Throws(EntityNotFoundException::class)
    fun getStudentById(studentId: Long): Student
}