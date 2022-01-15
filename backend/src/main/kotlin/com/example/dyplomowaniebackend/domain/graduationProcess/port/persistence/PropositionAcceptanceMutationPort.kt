package com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance

interface PropositionAcceptanceMutationPort {
    fun insertAll(propositionAcceptances: Set<PropositionAcceptance>): Set<PropositionAcceptance>
}
