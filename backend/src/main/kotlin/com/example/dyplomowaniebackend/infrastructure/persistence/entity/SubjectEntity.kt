package com.example.dyplomowaniebackend.infrastructure.persistence.entity

import com.example.dyplomowaniebackend.domain.model.RealizationLanguage
import com.example.dyplomowaniebackend.domain.model.SubjectStatus
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
class SubjectEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val subjectId: Long? = null,

    val topic: String,

    val topicInEnglish: String,

    @Lob
    val objective: String,

    @Lob
    val objectiveInEnglish: String,

    val realizationLanguage: RealizationLanguage,

    @Min(0)
    val realiseresNumber: Int,

    val accepted: Boolean? = null,

    @Enumerated(EnumType.STRING)
    val status: SubjectStatus,

    val creationDate: Instant = Instant.now(),

    // WARN: THIS IS ID OF INITIATOR
    @Column(name = "student_id")
    val studentId: Long?,

    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    val initiator: StudentEntity? = null,

    @OneToMany(mappedBy = "subject")
    val realiser: Set<StudentEntity>,

//    @OneToMany(mappedBy = "subject")
//    val propositionAcceptances: Set<PropositionAcceptanceEntity>,

//    @OneToMany(mappedBy = "subject")
//    val candidatures: Set<CandidatureEntity>,

    @Column(name = "staff_member_id")
    val staffMemberId: Long,

    @ManyToOne
    @JoinColumn(name = "staff_member_id", insertable = false, updatable = false)
    val supervisor: StaffMemberEntity? = null,

//    @OneToMany(mappedBy = "subject")
//    val verifications: Set<VerificationEntity>,

    @Column(name = "graduation_process_id")
    val graduationProcessId: Long,

    @ManyToOne
    @JoinColumn(name = "graduation_process_id", insertable = false, updatable = false)
    val graduationProcess: GraduationProcessEntity? = null,
)
