package com.example.dyplomowaniebackend.infrastructure.persistence.entity

import javax.persistence.*

@Entity
class StudentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val studentId: Long,

    @Column(unique = true, length = 16)
    val index: String,

    @Column(unique = true, length = 32)
    val email: String,

    @Column(length = 64)
    val name: String,

    @Column(length = 64)
    val surname: String,

    @OneToMany(mappedBy = "initiator")
    val subjectProposals: Set<SubjectEntity>,

    @ManyToOne
    @JoinColumn(name = "subject_id")
    val subject: SubjectEntity? = null,

    @OneToMany(mappedBy = "student")
    val propositionAcceptances: Set<PropositionAcceptanceEntity>,

    @OneToMany(mappedBy = "student")
    val candidatures: Set<Candidature>,

    @OneToMany(mappedBy = "student")
    val candidatureAcceptances: Set<CandidatureAcceptance>,

    @ManyToMany
    val graduationProcesses: Set<GraduationProcess>
)

