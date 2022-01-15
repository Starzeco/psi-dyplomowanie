package com.example.dyplomowaniebackend.domain.submission.port.api

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance

interface PropositionAcceptanceServicePort {
    fun getAllPropositionAcceptancesByStudentIdAndGraduationProcessId(studentId: Long, graduationProcessId: Long): Set<PropositionAcceptance>
    fun updatePropositionAcceptanceAcceptedFieldById(propositionAcceptanceId: Long, accepted: Boolean): Long
}
