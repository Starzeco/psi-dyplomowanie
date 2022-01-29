package com.example.dyplomowaniebackend.api.dto

import com.example.dyplomowaniebackend.domain.model.Degree
import com.example.dyplomowaniebackend.domain.model.GraduationProcess
import java.time.Instant


data class GraduationProcessPartialInfoResponse(
    val graduationProcessId: Long,
    val cSDeadline: Instant, //candidatures submitting deadline
    val vFDeadline: Instant, //verification forwarding
    val cADeadline: Instant, //candidatures approving deadline
    val sPDeadline: Instant, //subjects publication deadline
    val initialSemester: String,
    val finalSemester: String,
    val degree: Degree,
    val degreeCourseName: String,
    val facultyName: String,
    val facultyShortName: String,
) {
    companion object {
        fun fromDomain(graduationProcess: GraduationProcess): GraduationProcessPartialInfoResponse =
            GraduationProcessPartialInfoResponse(
                graduationProcessId = graduationProcess.graduationProcessId!!,
                cSDeadline = graduationProcess.cSDeadline,
                vFDeadline = graduationProcess.vFDeadline,
                cADeadline = graduationProcess.cADeadline,
                sPDeadline = graduationProcess.sPDeadline,
                initialSemester = graduationProcess.initialSemester,
                finalSemester = graduationProcess.finalSemester,
                degree = graduationProcess.degree,
                degreeCourseName = graduationProcess.degreeCourse.name,
                facultyName = graduationProcess.degreeCourse.faculty.name,
                facultyShortName = graduationProcess.degreeCourse.faculty.shortName
            )
    }
}


