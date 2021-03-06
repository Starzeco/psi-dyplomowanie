package com.example.dyplomowaniebackend.domain.graduationProcess.port.api

import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.domain.model.SubjectStatus
import com.example.dyplomowaniebackend.domain.model.SubjectType

interface SubjectSearchPort {
    fun getSubjectsForStudent(
        studentId: Long,
        searchPhrase: String?,
        subjectType: SubjectType?,
        availableSubjects: Boolean,
        subjectStatus: SubjectStatus?
    ): Set<Subject>

    fun getSubjectsForSupervisor(
        supervisorId: Long,
        searchPhrase: String?,
        subjectType: SubjectType?,
        processingSubjects: Boolean,
        subjectStatus: SubjectStatus?
    ): Set<Subject>

    fun getSubjectById(subjectId: Long): Subject
}