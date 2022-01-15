package com.example.dyplomowaniebackend.api.dto

import com.example.dyplomowaniebackend.domain.model.Candidature
import com.example.dyplomowaniebackend.domain.model.CandidatureAcceptance
import com.example.dyplomowaniebackend.domain.model.CandidatureStatus
import com.example.dyplomowaniebackend.domain.model.CandidatureType

data class CandidaturePartialInfoResponse(
    val subjectTopic: String,
    val supervisorName: String,
    val type: CandidatureType,
    val status: CandidatureStatus
    ) {
    companion object {

        private fun prepareStatus(candidature: Candidature, allCandidatureAcceptances: Set<CandidatureAcceptance>): CandidatureStatus =
            when (candidature.accepted) {
                true -> CandidatureStatus.ACCEPTED
                false -> CandidatureStatus.REJECTED
                else -> {
                    if (allCandidatureAcceptances.all { it.accepted == true }) CandidatureStatus.TO_ACCEPT_BY_SUPERVISOR
                    else if (allCandidatureAcceptances.all { it.accepted == false }) CandidatureStatus.REJECTED
                    else CandidatureStatus.TO_ACCEPT_BY_STUDENTS
                }

        }

        fun fromDomain(candidature: Candidature, candidatureAcceptances: Set<CandidatureAcceptance>): CandidaturePartialInfoResponse =
            CandidaturePartialInfoResponse(
                subjectTopic = candidature.subject.topic,
                supervisorName = candidature.subject.supervisor.fullName,
                type = if (candidatureAcceptances.isNotEmpty()) CandidatureType.GROUP else CandidatureType.INDIVIDUAL,
                status = prepareStatus(candidature, candidatureAcceptances)
            )
    }


}






