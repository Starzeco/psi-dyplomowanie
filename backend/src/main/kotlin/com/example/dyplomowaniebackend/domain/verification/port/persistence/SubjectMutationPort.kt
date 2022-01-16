package com.example.dyplomowaniebackend.domain.verification.port.persistence

import com.example.dyplomowaniebackend.domain.model.SubjectStatusUpdate

interface SubjectMutationPort {
    fun updateStatus(subjectStatusUpdate: SubjectStatusUpdate): SubjectStatusUpdate
}