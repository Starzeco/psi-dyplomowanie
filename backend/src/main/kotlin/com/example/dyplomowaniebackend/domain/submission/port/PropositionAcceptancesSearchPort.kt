package com.example.dyplomowaniebackend.domain.submission.port

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance

interface PropositionAcceptancesSearchPort {
    fun getAllByStudentId(studentId: Long): Set<PropositionAcceptance>
}
