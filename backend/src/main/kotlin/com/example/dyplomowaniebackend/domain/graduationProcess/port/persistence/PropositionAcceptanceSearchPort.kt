package com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance

interface PropositionAcceptanceSearchPort {
    fun getAllBySubjectId(subjectId: Long): Set<PropositionAcceptance>
}