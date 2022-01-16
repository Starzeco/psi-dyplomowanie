package com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence

import com.example.dyplomowaniebackend.domain.model.Subject

interface SubjectSearchPort {
    fun getById(subjectId: Long): Subject
}
