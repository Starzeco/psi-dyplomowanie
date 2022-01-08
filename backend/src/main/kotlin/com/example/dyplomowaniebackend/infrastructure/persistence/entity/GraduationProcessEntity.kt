package com.example.dyplomowaniebackend.infrastructure.persistence.entity

import com.example.dyplomowaniebackend.domain.model.Degree
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
class GraduationProcess(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val graduationProcessId: String,

    //candidatures submitting deadline
    val cSDeadline: Instant,

    //verification forwarding
    val vFDeadline: Instant,

    //candidatures approving deadline
    val cADeadline: Instant,

    //subjects publication deadline
    val sPDeadline: Instant,

    val initialSemester: String,

    val finalSemester: String,

    val degree: Degree,

    //hours counted per subject
    @Min(0)
    val hCPerSubject: Int,

    @ManyToMany
    @JoinTable(
        name = "graduation_process_student",
        joinColumns = [JoinColumn(name = "student_id")],
        inverseJoinColumns = [JoinColumn(name = "graduation_process_id")]
    )
    val students: Set<StudentEntity>,

    @OneToMany(mappedBy = "graduationProcess")
    val subjects: Set<SubjectEntity>,

    @ManyToOne
    @JoinColumn(name = "degree_course_id")
    val degreeCourse: DegreeCourseEntity,

    @OneToMany(mappedBy = "graduationProcess")
    val verifiers: Set<VerifierEntity>,
)
