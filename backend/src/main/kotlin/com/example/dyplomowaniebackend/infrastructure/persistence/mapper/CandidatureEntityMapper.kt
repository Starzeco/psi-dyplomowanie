package com.example.dyplomowaniebackend.infrastructure.persistence.mapper

import com.example.dyplomowaniebackend.domain.model.Candidature
import com.example.dyplomowaniebackend.domain.model.CandidatureAcceptance
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.CandidatureAcceptanceEntity
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.CandidatureEntity

fun Candidature.mapToEntity(): CandidatureEntity =
    CandidatureEntity(
        candidatureId = this.candidatureId,
        accepted = this.accepted,
        studentId = this.student.studentId!!,
        subjectId = this.subject.subjectId!!,
        creationDate = this.creationDate
    )

fun CandidatureAcceptance.mapToEntity(): CandidatureAcceptanceEntity =
    CandidatureAcceptanceEntity(
        accepted = this.accepted,
        studentId = this.student.studentId!!,
        candidatureId = this.candidature.candidatureId!!,
    )
