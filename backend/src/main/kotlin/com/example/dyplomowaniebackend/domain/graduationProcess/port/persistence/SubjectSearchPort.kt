package com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence

import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.domain.model.exception.EntityNotFoundException

interface SubjectSearchPort {
    @Throws(EntityNotFoundException::class)
    fun getSubjectById(subjectId: Long): Subject
}