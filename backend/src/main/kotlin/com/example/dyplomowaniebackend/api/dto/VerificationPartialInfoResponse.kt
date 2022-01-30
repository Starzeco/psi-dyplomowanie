package com.example.dyplomowaniebackend.api.dto

import com.example.dyplomowaniebackend.domain.model.*
import java.time.Instant

data class VerificationPartialInfoResponse(
    val verificationId: Long,
    val verified: Boolean,
    val justification: String,
    val decisionDate: Instant,
    val subject: SubjectPartialInfo

) {
    companion object {
        fun fromDomain(verification: Verification): VerificationPartialInfoResponse = VerificationPartialInfoResponse(
            verificationId = verification.verificationId!!,
            verified = verification.verified!!,
            justification = verification.justification!!,
            decisionDate = verification.updateDate!!,
            subject = SubjectPartialInfo.fromDomain(verification.subject)
        )
    }

    data class SubjectPartialInfo(
        val subjectId: Long,
        val topic: String,
        val topicInEnglish: String,
        val objective: String,
        val objectiveInEnglish: String,
        val realizationLanguage: RealizationLanguage,
        val creationDate: Instant,
        val supervisor: StaffMemberPartialInfo,
    ) {
        companion object {
            fun fromDomain(subject: Subject): SubjectPartialInfo = SubjectPartialInfo(
                subjectId = subject.subjectId!!,
                topic = subject.topic,
                topicInEnglish = subject.topicInEnglish,
                objective = subject.objective,
                objectiveInEnglish = subject.objective,
                realizationLanguage = subject.realizationLanguage,
                creationDate = subject.creationDate,
                supervisor = StaffMemberPartialInfo.fromDomain(subject.supervisor)
            )
        }
    }

    data class StaffMemberPartialInfo(
        val staffMemberId: Long,
        val name: String,
        val surname: String,
        val title: Title,
    ) {
        companion object {
            fun fromDomain(staffMember: StaffMember): StaffMemberPartialInfo = StaffMemberPartialInfo(
                staffMemberId = staffMember.staffMemberId!!,
                name = staffMember.fullName,
                surname = staffMember.surname,
                title = staffMember.title
            )
        }
    }

}


