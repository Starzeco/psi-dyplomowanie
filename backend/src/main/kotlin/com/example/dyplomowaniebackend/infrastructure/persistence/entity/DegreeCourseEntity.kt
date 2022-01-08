package com.example.dyplomowaniebackend.infrastructure.persistence.entity

import javax.persistence.*

@Entity
class DegreeCourseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val degreeCourseId: Long? = null,

    @Column(unique = true, length = 128)
    val name: String,

    val active: Boolean = true,

    @Column(name = "faculty_id")
    val facultyId: Long,

    @ManyToOne
    @JoinColumn(name = "faculty_id", insertable = false, updatable = false)
    val faculty: FacultyEntity? = null,

    @OneToMany(mappedBy = "degreeCourse")
    val graduationProcesses: Set<GraduationProcessEntity>
)
