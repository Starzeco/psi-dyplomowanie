package com.example.dyplomowaniebackend.infrastructure.persistence.entity

import java.time.Instant
import javax.persistence.*

@Entity
class VerificationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val verificationId: Long? = null,

    val verified: Boolean? = null,

    @Lob
    val justification: String? = null,

    val updateDate: Instant? = null,

    @Column(name = "subject_id")
    val subjectId: Long,

    @ManyToOne
    @JoinColumn(name = "subject_id", insertable = false, updatable = false)
    val subject: SubjectEntity? = null,

    @Column(name = "verifier_id")
    val verifierId: Long,

    @ManyToOne
    @JoinColumn(name = "verifier_id", insertable = false, updatable = false)
    val verifier: VerifierEntity? = null

    //TODO: cyclic dependency
)
