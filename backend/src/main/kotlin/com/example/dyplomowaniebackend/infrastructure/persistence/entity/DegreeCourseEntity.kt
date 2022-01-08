package com.example.dyplomowaniebackend.infrastructure.persistence.entity

import javax.persistence.*

@Entity
class DegreeCourseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val degreeCourseId: Long,

    @Column(unique = true, length = 128)
    val name: String,

    val active: Boolean = true,

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    val faculty: FacultyEntity,

//    @OneToMany(mappedBy = "degreeCourse")
//    val graduationProcesses: Set<GraduationProcessEntity>
)
