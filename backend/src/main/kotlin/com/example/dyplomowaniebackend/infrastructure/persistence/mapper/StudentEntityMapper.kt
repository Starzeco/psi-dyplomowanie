package com.example.dyplomowaniebackend.infrastructure.persistence.mapper

import com.example.dyplomowaniebackend.domain.model.Student
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.StudentEntity

fun StudentEntity.mapToDomain(cut: Boolean = false): Student {
    return Student(
        studentId = this.studentId,
        index = this.index,
        email = this.email,
        name = this.name,
        surname = this.surname,
        subject = if(cut) null else this.subject?.mapToDomain(),
        graduationProcesses = this.graduationProcesses.map { it.mapToDomain() }.toSet()
    )
}

fun Student.mapToEntity(): StudentEntity {
    return StudentEntity(
        studentId = this.studentId,
        index = this.index,
        email = this.email,
        name = this.name,
        surname = this.surname,
        subject = this.subject?.mapToEntity(),
        graduationProcesses = this.graduationProcesses.map { it.mapToEntity() }.toSet()
    )
}
