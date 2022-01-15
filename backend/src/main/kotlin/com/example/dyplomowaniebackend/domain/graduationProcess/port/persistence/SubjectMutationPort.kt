package com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence

import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.domain.model.SubjectStatusUpdate
import com.example.dyplomowaniebackend.domain.model.SubjectUpdate

interface SubjectMutationPort {
    fun insert(subject: Subject): Subject
    fun updateStatus(subjectStatusUpdate: SubjectStatusUpdate): SubjectStatusUpdate
    fun updateSubject(updateSubject: SubjectUpdate): SubjectUpdate
}