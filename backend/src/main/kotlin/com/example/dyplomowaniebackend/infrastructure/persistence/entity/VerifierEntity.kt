package com.example.dyplomowaniebackend.infrastructure.persistence.entity

import java.time.Instant
import javax.persistence.*

@Entity
class VerifierEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val verifierId: Long? = null,

    val name: String,

    val verificationsDeadline: Instant,

    @Column(name = "staff_member_id")
    val staffMemberId: Long,

    @ManyToOne
    @JoinColumn(name = "staff_member_id", insertable = false, updatable = false)
    val verifier: StaffMemberEntity? = null,

    @Column(name = "graduation_process_id")
    val graduationProcessId: Long,

    @ManyToOne
    @JoinColumn(name = "graduation_process_id", insertable = false, updatable = false)
    val graduationProcess: GraduationProcessEntity? = null,

    @OneToMany(mappedBy = "verifier")
    val verifications: Set<VerificationEntity>
)
