package com.example.dyplomowaniebackend.domain.submission.port.persistence

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance

interface PropositionAcceptanceSearchPort {
    fun getAllByStudentIdAndGraduationProcessId(studentId: Long, graduationProcessId: Long): Set<PropositionAcceptance>
    fun getAllBySubjectId(subjectId: Long): Set<PropositionAcceptance>
}
