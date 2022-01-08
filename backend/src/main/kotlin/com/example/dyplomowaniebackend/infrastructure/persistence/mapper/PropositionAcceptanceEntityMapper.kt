package com.example.dyplomowaniebackend.infrastructure.persistence.mapper

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.PropositionAcceptanceEntity


fun PropositionAcceptanceEntity.mapToDomain(): PropositionAcceptance =
    PropositionAcceptance(
        propositionAcceptanceId = this.propositionAcceptanceId,
        accepted = this.accepted,
        student = this.student.mapToDomain(),
        subject = this.subject.mapToDomain()
    )

fun PropositionAcceptance.mapToEntity(): PropositionAcceptanceEntity =
    PropositionAcceptanceEntity(
        propositionAcceptanceId = this.propositionAcceptanceId,
        accepted = this.accepted,
        student = this.student.mapToEntity(),
        subject = this.subject.mapToEntity()
    )
