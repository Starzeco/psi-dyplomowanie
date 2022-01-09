package com.example.dyplomowaniebackend.domain.model

import java.time.Instant

data class GraduationProcess(
    val graduationProcessId: Long? = null,
    val cSDeadline: Instant, //candidatures submitting deadline
    val vFDeadline: Instant, //verification forwarding
    val cADeadline: Instant, //candidatures approving deadline
    val sPDeadline: Instant, //subjects publication deadline
    val initialSemester: String,
    val finalSemester: String,
    val degree: Degree,
    val hCPerSubject: Int, //hours counted per subject
    val students: Set<Student>,
//    val subjects: Set<Subject>,
    val degreeCourse: DegreeCourse,
//    val verifiers: Set<Verifier>,
)

enum class Degree {
    MASTER,
    BATCHELOR
}
