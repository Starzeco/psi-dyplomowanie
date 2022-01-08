package com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence

import com.example.dyplomowaniebackend.domain.model.Student

interface StudentSearchPort {
    fun getStudentById(studentId: Long?): Student?
}