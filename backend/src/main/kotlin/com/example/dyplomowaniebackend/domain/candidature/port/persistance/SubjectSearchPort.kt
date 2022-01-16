package com.example.dyplomowaniebackend.domain.candidature.port.persistance

import com.example.dyplomowaniebackend.domain.model.Subject

interface SubjectSearchPort {
    fun getById(subjectId: Long): Subject
}
