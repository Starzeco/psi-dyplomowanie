package com.example.dyplomowaniebackend.api.dto

import com.example.dyplomowaniebackend.domain.model.Subject

data class SubjectPartialInfoResponse(
    val subjectId: Long,
    val topic: String,
    val supervisor: String,
    val type: SubjectType,
) {
    companion object {
        fun fromDomain(subject: Subject): SubjectPartialInfoResponse =
            SubjectPartialInfoResponse(
                subjectId = subject.subjectId,
                topic = subject.topic,
                supervisor = subject.supervisor.fullName,
                type = if (subject.realiseresNumber > 1) SubjectType.GROUP else SubjectType.INDIVIDUAL
            )
    }
}

enum class SubjectType {
    INDIVIDUAL,
    GROUP
}
