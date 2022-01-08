package com.example.dyplomowaniebackend.infrastructure.persistence.entity

import javax.persistence.*

@Entity
class PropositionAcceptanceEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val propositionAcceptanceId: Long,

    val accepted: Boolean? = null,

    @ManyToOne
    @JoinColumn(name="student_id")
    val student: StudentEntity,

    @ManyToOne
    @JoinColumn(name="subject_id")
    val subject: SubjectEntity,
)
