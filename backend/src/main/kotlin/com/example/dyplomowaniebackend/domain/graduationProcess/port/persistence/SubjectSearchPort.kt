package com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence

import com.example.dyplomowaniebackend.domain.model.Subject

interface SubjectSearchPort {
    fun getSubjectById(subjectId: Long): Subject
}
