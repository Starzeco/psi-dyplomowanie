package com.example.dyplomowaniebackend.api.dto

import com.example.dyplomowaniebackend.domain.model.Candidature
import com.example.dyplomowaniebackend.domain.model.CandidatureAcceptance
import com.example.dyplomowaniebackend.domain.model.CandidatureStatus
import com.example.dyplomowaniebackend.domain.model.CandidatureType

data class CandidaturePartialInfoResponse(
    val subjectId: Long,
    val candidatureId: Long,
    val subjectTopic: String,
    val subjectTopicEnglish: String,
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
                    else if (allCandidatureAcceptances.any { it.accepted == false }) CandidatureStatus.REJECTED
                    else CandidatureStatus.TO_ACCEPT_BY_STUDENTS
                }

        }

        fun fromDomain(candidature: Candidature, candidatureAcceptances: Set<CandidatureAcceptance>): CandidaturePartialInfoResponse =
            CandidaturePartialInfoResponse(
                subjectId = candidature.subject.subjectId!!,
                candidatureId = candidature.candidatureId!!,
                subjectTopic = candidature.subject.topic,
                subjectTopicEnglish = candidature.subject.topicInEnglish,
                supervisorName = candidature.subject.supervisor.fullName,
                type = if (candidatureAcceptances.isNotEmpty()) CandidatureType.GROUP else CandidatureType.INDIVIDUAL,
                status = prepareStatus(candidature, candidatureAcceptances)
            )
    }


}






