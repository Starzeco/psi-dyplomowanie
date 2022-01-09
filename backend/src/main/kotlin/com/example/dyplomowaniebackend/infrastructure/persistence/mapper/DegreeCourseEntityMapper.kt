package com.example.dyplomowaniebackend.infrastructure.persistence.mapper

import com.example.dyplomowaniebackend.domain.model.DegreeCourse
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.DegreeCourseEntity


fun DegreeCourseEntity.mapToDomain(): DegreeCourse =
    DegreeCourse(
        degreeCourseId = this.degreeCourseId,
        name = this.name,
        active = this.active,
        faculty = this.faculty!!.mapToDomain()
    )

fun DegreeCourse.mapToEntity(): DegreeCourseEntity =
    DegreeCourseEntity(
        degreeCourseId = this.degreeCourseId,
        name = this.name,
        active = this.active,
        facultyId = this.faculty.facultyId!!
    )
