package com.example.dyplomowaniebackend.infrastructure.persistence.entity

import javax.persistence.*

@Entity
class FacultyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val facultyId: Long,

    @Column(unique = true, length = 128)
    val name: String,

    @Column(unique = true, length = 4)
    val shortName: String,

    val active: Boolean = true,

    @OneToMany(mappedBy = "faculty")
    val staffMembers: Set<StaffMember>,

    @OneToMany(mappedBy = "faculty")
    val degreeCourses: Set<DegreeCourseEntity>
)
