package com.example.dyplomowaniebackend.domain.model

data class DegreeCourse(
    val degreeCourseId: Long,
    val name: String,
    val active: Boolean = true,
    val faculty: Faculty,
//    val graduationProcesses: Set<GraduationProcess>
)
