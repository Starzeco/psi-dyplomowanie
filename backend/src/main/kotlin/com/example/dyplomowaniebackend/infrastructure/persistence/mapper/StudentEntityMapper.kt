package com.example.dyplomowaniebackend.infrastructure.persistence.mapper

import com.example.dyplomowaniebackend.domain.model.Student
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.StudentEntity

fun StudentEntity.mapToDomain(cut: Boolean = false, cutDeep: Boolean = false): Student {
    return Student(
        studentId = this.studentId,
        index = this.index,
        email = this.email,
        name = this.name,
        surname = this.surname,
        subject = if(cutDeep) this.subject?.mapToDomain(true) else if(cut) null else this.subject?.mapToDomain(),
        graduationProcesses = if(cut) setOf() else this.graduationProcesses.map { it.mapToDomain() }.toSet()
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
