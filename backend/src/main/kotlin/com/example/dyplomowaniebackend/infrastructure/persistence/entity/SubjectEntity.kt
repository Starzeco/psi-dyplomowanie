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
    val subjectId: Long,

    @Column(unique = true)
    val topic: String,

    @Column(unique = true)
    val topicInEnglish: String,

    @Lob
    val objective: String,

    @Lob
    val objectiveInEnglish: String,

    val realizationLanguage: RealizationLanguage,

    @Min(0)
    val realiseresNumber: Int,

    val accepted: Boolean? = null,

    val status: SubjectStatus,

    val creationDate: Instant = Instant.now(),

    @ManyToOne
    @JoinColumn(name = "student_id")
    val initiator: StudentEntity? = null,

    @OneToMany(mappedBy = "subject")
    val realiser: Set<StudentEntity>,

    @OneToMany(mappedBy = "subject")
    val propositionAcceptances: Set<PropositionAcceptanceEntity>,

    @OneToMany(mappedBy = "subject")
    val candidatures: Set<Candidature>,

    @ManyToOne
    @JoinColumn(name = "staff_member_id")
    val supervisor: StaffMember,

    @OneToMany(mappedBy = "subject")
    val verifications: Set<VerificationEntity>,

    @ManyToOne
    @JoinColumn(name = "graduation_process_id")
    val graduationProcess: GraduationProcess,
)
