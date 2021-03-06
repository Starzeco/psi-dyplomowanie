package com.example.dyplomowaniebackend.infrastructure.persistence.entity

import com.example.dyplomowaniebackend.domain.model.Title
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
class StaffMemberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val staffMemberId: Long? = null,

    @Column(unique = true, length = 32)
    val email: String,

    @Column(length = 64)
    val name: String,

    @Column(length = 64)
    val surname: String,

    val title: Title,

    @Min(0)
    val currentWorkload: Int,

    @Min(0)
    val absoluteWorkload: Int,

//    @OneToMany(mappedBy = "supervisor")
//    val subjects: Set<SubjectEntity>,

    @Column(name = "faculty_id")
    val facultyId: Long,

    @ManyToOne
    @JoinColumn(name = "faculty_id", insertable = false, updatable = false)
    val faculty: FacultyEntity? = null,

//    @OneToMany(mappedBy = "verifier")
//    val verifiers: Set<VerifierEntity>
)
