package com.example.dyplomowaniebackend.domain.model

data class Student(
    val studentId: Long? = null,
    val index: String,
    val email: String,
    val name: String,
    val surname: String,
    val subjectProposals: Set<Subject>,
    val subject: Subject? = null,
    val propositionAcceptances: Set<PropositionAcceptance>,
    val candidatures: Set<Candidature>,
    val candidatureAcceptances: Set<CandidatureAcceptance>,
    val graduationProcesses: Set<GraduationProcess>
)

