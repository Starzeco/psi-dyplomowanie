package com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence

import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.domain.model.SubjectStatus

interface SubjectSearchPort {
    fun getById(subjectId: Long, cut: Boolean): Subject
    fun getSubjectsInStatus(subjectStatuses: List<SubjectStatus>): Set<Subject>
}
