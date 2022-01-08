package com.example.dyplomowaniebackend.infrastructure.persistence.mapper

import com.example.dyplomowaniebackend.domain.model.Faculty
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.FacultyEntity


fun FacultyEntity.mapToDomain(): Faculty =
    Faculty(
        facultyId = this.facultyId,
        name = this.name,
        shortName = this.shortName,
        active = this.active,
    )

fun Faculty.mapToEntity(): FacultyEntity =
    FacultyEntity(
        facultyId = this.facultyId,
        name = this.name,
        shortName = this.shortName,
        active = this.active,
    )
