package com.example.dyplomowaniebackend.domain.model

import java.time.Instant

data class Subject(
    val subjectId: Long,
    val topic: String,
    val topicInEnglish: String,
    val objective: String,
    val objectiveInEnglish: String,
    val realizationLanguage: RealizationLanguage,
    val realiseresNumber: Int,
    val accepted: Boolean? = null,
    val status: SubjectStatus,
    val creationDate: Instant = Instant.now(),
    val initiator: Student? = null,
//    val realiser: Set<Student>,
//    val propositionAcceptances: Set<PropositionAcceptance>,
//    val candidatures: Set<Candidature>,
    val supervisor: StaffMember,
//    val verifications: Set<Verification>,
    val graduationProcess: GraduationProcess,
)

enum class RealizationLanguage {
    POLISH,
    ENGLISH
}

enum class SubjectStatus {
    DRAFT,
    ACCEPTED_BY_SUPERVISOR,
    ACCEPTED_BY_INITIATOR,
    IN_VERIFICATION,
    IN_CORRECTION,
    VERIFIED,
    REJECTED,
    RESERVED
}
