package com.example.dyplomowaniebackend.infrastructure.persistence.mapper

import com.example.dyplomowaniebackend.domain.model.GraduationProcess
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.GraduationProcessEntity


fun GraduationProcessEntity.mapToDomain(): GraduationProcess =
    GraduationProcess(
        graduationProcessId = this.graduationProcessId,
        cSDeadline = this.cSDeadline,
        vFDeadline = this.vFDeadline,
        cADeadline = this.cADeadline,
        sPDeadline = this.sPDeadline,
        initialSemester = this.initialSemester,
        finalSemester = this.finalSemester,
        degree = this.degree,
        hCPerSubject = this.hCPerSubject,
        students = this.students.map { it.mapToDomain() }.toSet(),
        degreeCourse = this.degreeCourse!!.mapToDomain(),
    )


fun GraduationProcess.mapToEntity(): GraduationProcessEntity =
    GraduationProcessEntity(
        graduationProcessId = this.graduationProcessId,
        cSDeadline = this.cSDeadline,
        vFDeadline = this.vFDeadline,
        cADeadline = this.cADeadline,
        sPDeadline = this.sPDeadline,
        initialSemester = this.initialSemester,
        finalSemester = this.finalSemester,
        degree = this.degree,
        hCPerSubject = this.hCPerSubject,
        students = this.students.map { it.mapToEntity() }.toSet(),
        degreeCourseId = this.degreeCourse.degreeCourseId!!,
    )
