package com.example.dyplomowaniebackend.api.dto

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance

data class PropositionAcceptancePartialInfoResponse(
    val propositionAcceptanceId: Long,
    val accepted: Boolean? = null,
    val subject: SubjectPartialInfoResponse
) {
    companion object {
        fun fromDomain(propositionAcceptance: PropositionAcceptance): PropositionAcceptancePartialInfoResponse =
            PropositionAcceptancePartialInfoResponse(
                propositionAcceptanceId = propositionAcceptance.propositionAcceptanceId!!,
                accepted = propositionAcceptance.accepted,
                subject = SubjectPartialInfoResponse.fromDomain(propositionAcceptance.subject)
            )
    }
}

