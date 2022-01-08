package com.example.dyplomowaniebackend.infrastructure.persistence.entity

import javax.persistence.*

@Entity
class PropositionAcceptanceEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val propositionAcceptanceId: Long? = null,

    val accepted: Boolean? = null,

    @Column(name = "student_id")
    val studentId: Long,

    @ManyToOne
    @JoinColumn(name="student_id", insertable = false, updatable = false)
    val student: StudentEntity? = null,

    @Column(name = "subject_id")
    val subjectId: Long,

    @ManyToOne
    @JoinColumn(name="subject_id", insertable = false, updatable = false)
    val subject: SubjectEntity? = null,
)
