package com.example.dyplomowaniebackend.infrastructure.persistence.mapper

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.PropositionAcceptanceEntity


fun PropositionAcceptanceEntity.mapToDomain(cut: Boolean = false): PropositionAcceptance =
    PropositionAcceptance(
        propositionAcceptanceId = this.propositionAcceptanceId,
        accepted = this.accepted,
        student = this.student!!.mapToDomain(cut),
        subject = this.subject!!.mapToDomain(cut)
    )

fun PropositionAcceptance.mapToEntity(): PropositionAcceptanceEntity =
    PropositionAcceptanceEntity(
        propositionAcceptanceId = this.propositionAcceptanceId,
        accepted = this.accepted,
        studentId = this.student.studentId!!,
        subjectId = this.subject.subjectId!!
    )
