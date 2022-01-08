package com.example.dyplomowaniebackend.infrastructure.persistence.entity

import javax.persistence.*

@Entity
class StudentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val studentId: Long? = null,

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

    @Column(name = "subject_id")
    val subjectId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "subject_id", insertable = false, updatable = false)
    val subject: SubjectEntity? = null,

    @OneToMany(mappedBy = "student")
    val propositionAcceptances: Set<PropositionAcceptanceEntity>,

    @OneToMany(mappedBy = "student")
    val candidatures: Set<CandidatureEntity>,

    @OneToMany(mappedBy = "student")
    val candidatureAcceptances: Set<CandidatureAcceptanceEntity>,

    @ManyToMany
    val graduationProcesses: Set<GraduationProcessEntity>
)

