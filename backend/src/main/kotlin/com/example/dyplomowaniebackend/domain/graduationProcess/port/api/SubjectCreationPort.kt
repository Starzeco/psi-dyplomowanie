package com.example.dyplomowaniebackend.domain.graduationProcess.port.api

import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.domain.model.SubjectCreation

interface SubjectCreationPort {
    fun createSubject(subjectCreation: SubjectCreation): Long
}
