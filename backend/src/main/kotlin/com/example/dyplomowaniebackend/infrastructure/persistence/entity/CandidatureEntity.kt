package com.example.dyplomowaniebackend.infrastructure.persistence.entity

import java.time.Instant
import javax.persistence.*

@Entity
class CandidatureEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val candidatureId: Long? = null,

    val accepted: Boolean? = null,

    val creationDate: Instant = Instant.now(),

    @Column(name = "student_id")
    val studentId: Long,

    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    val student: StudentEntity? = null,

//    @OneToMany(mappedBy = "candidature")
//    val candidatureAcceptances: Set<CandidatureAcceptanceEntity>,

    @Column(name = "subject_id")
    val subjectId: Long,

    @ManyToOne
    @JoinColumn(name = "subject_id", insertable = false, updatable = false)
    val subject: SubjectEntity? = null
)

@Entity
class CandidatureAcceptanceEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val candidatureAcceptanceId: Long? = null,

    val accepted: Boolean? = null,

    @Column(name = "student_id")
    val studentId: Long,

    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    val student: StudentEntity? = null,

    @Column(name = "candidature_id")
    val candidatureId: Long,

    @ManyToOne
    @JoinColumn(name = "candidature_id", insertable = false, updatable = false)
    val candidature: CandidatureEntity? = null
)
