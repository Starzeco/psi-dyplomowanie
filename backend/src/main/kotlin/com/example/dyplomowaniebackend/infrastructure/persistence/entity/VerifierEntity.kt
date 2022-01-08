package com.example.dyplomowaniebackend.infrastructure.persistence.entity

import java.time.Instant
import javax.persistence.*

@Entity
class VerifierEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val verifierId: Long,

    val name: String,

    val verificationsDeadline: Instant,

    @ManyToOne
    @JoinColumn(name = "staff_member_id")
    val verifier: StaffMemberEntity,

    @ManyToOne
    @JoinColumn(name = "graduation_process_id")
    val graduationProcess: GraduationProcessEntity,

//    @OneToMany(mappedBy = "verifier")
//    val verifications: Set<VerificationEntity>
)
