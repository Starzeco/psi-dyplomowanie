package com.example.dyplomowaniebackend.domain.submission.port.persistence

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance

interface PropositionAcceptanceMutationPort {
    fun updateAcceptedFieldById(propositionAcceptanceId: Long, accepted: Boolean): Long
    fun insert(propositionAcceptance: PropositionAcceptance): Long
}
