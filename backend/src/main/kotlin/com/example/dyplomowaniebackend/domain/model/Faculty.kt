package com.example.dyplomowaniebackend.domain.model

data class Faculty(
    val facultyId: Long,
    val name: String,
    val shortName: String,
    val active: Boolean = true,
//    val staffMembers: Set<StaffMember>,
//    val degreeCourses: Set<DegreeCourse>
)
