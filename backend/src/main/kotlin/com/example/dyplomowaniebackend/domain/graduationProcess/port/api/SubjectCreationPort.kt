package com.example.dyplomowaniebackend.domain.graduationProcess.port.api

import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.domain.model.SubjectCreation
import com.example.dyplomowaniebackend.domain.model.SubjectStatusUpdate

interface SubjectCreationPort {
    fun createSubject(subjectCreation: SubjectCreation): Subject
    fun rejectSubject(subjectId: Long): SubjectStatusUpdate
    fun acceptSubjectPreparedBySupervisor(subjectId: Long): SubjectStatusUpdate
    fun acceptSubjectPreparedByInitiator(subjectId: Long): SubjectStatusUpdate
    fun sendSubjectToVerification(subjectId: Long): SubjectStatusUpdate
}
