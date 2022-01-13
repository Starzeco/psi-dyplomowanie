package com.example.dyplomowaniebackend.domain.graduationProcess.port.api

import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.domain.model.SubjectCreation
import com.example.dyplomowaniebackend.domain.model.SubjectStatusUpdate

interface SubjectCreationPort {
    fun createSubject(subjectCreation: SubjectCreation): Subject
    fun rejectSubject(subjectId: Long): SubjectStatusUpdate
    fun acceptSupervisorSubject(subjectId: Long): SubjectStatusUpdate
    fun acceptInitiatorSubject(subjectId: Long): SubjectStatusUpdate
    fun sendToVerificationSubject(subjectId: Long): SubjectStatusUpdate
}
