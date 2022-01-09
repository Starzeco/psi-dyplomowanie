package com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence

import com.example.dyplomowaniebackend.domain.model.Subject

interface SubjectMutationPort {
    fun saveSubject(subject: Subject): Subject
}