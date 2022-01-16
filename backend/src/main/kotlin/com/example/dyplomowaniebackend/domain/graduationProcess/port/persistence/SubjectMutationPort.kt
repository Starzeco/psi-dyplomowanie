package com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence

import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.domain.model.SubjectStatusUpdate

interface SubjectMutationPort {
    fun insert(subject: Subject): Subject
    fun updateStatus(subjectStatusUpdate: SubjectStatusUpdate): SubjectStatusUpdate
    fun updateInitiatorIdById(subjectId: Long, initiatorId: Long): Long
}
