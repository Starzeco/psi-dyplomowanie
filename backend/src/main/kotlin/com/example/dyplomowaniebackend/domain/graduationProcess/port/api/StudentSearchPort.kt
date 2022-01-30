package com.example.dyplomowaniebackend.domain.graduationProcess.port.api

import com.example.dyplomowaniebackend.domain.model.Student

interface StudentSearchPort {
    fun getStudentsByIndexes(indexes: List<String>): Set<Student>
}