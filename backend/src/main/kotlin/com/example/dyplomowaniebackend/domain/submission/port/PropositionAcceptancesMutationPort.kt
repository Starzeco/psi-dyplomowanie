package com.example.dyplomowaniebackend.domain.submission.port

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance

interface PropositionAcceptancesMutationPort {
    fun updateAcceptedFieldById(propositionAcceptanceId: Long, accepted: Boolean): Long
    fun insert(propositionAcceptance: PropositionAcceptance): Long
}
