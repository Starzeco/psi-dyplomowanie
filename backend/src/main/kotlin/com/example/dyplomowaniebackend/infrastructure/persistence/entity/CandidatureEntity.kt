package com.example.dyplomowaniebackend.infrastructure.persistence.entity

import java.time.Instant
import javax.persistence.*

@Entity
class Candidature(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val candidatureId: Long,

    val accepted: Boolean? = null,

    val creationDate: Instant = Instant.now(),

    @ManyToOne
    @JoinColumn(name = "student_id")
    val student: StudentEntity,

    @OneToMany(mappedBy = "candidature")
    val candidatureAcceptances: Set<CandidatureAcceptance>,

    @ManyToOne
    @JoinColumn(name = "subject_id")
    val subject: SubjectEntity
)

@Entity
class CandidatureAcceptance(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val candidatureAcceptanceId: Long,

    val accepted: Boolean? = null,

    @ManyToOne
    @JoinColumn(name = "student_id")
    val student: StudentEntity,

    @ManyToOne
    @JoinColumn(name = "candidature_id")
    val candidature: Candidature
)
