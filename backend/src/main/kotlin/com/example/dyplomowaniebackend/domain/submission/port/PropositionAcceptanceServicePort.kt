package com.example.dyplomowaniebackend.domain.submission.port

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance

interface PropositionAcceptanceServicePort {
    fun getAllPropositionAcceptancesByStudentId(studentId: Long): Set<PropositionAcceptance>
    fun updatePropositionAcceptanceAcceptedFieldById(propositionAcceptanceId: Long, accepted: Boolean): Long
    fun insertPropositionAcceptance(propositionAcceptance: PropositionAcceptance): Long
}
