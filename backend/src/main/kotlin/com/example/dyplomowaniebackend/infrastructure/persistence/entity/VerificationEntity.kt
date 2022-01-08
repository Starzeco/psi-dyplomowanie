package com.example.dyplomowaniebackend.infrastructure.persistence.entity

import java.time.Instant
import javax.persistence.*

@Entity
class VerificationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val verificationId: Long,

    val verified: Boolean? = null,

    @Lob
    val justification: String? = null,

    val updateDate: Instant? = null,

    @ManyToOne
    @JoinColumn(name = "subject_id")
    val subject: SubjectEntity,

    @ManyToOne
    @JoinColumn(name = "verifier_id")
    val verifier: VerifierEntity

    //TODO: cyclic dependency
)
