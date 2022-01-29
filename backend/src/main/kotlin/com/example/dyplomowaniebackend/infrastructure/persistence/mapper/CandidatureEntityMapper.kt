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
        creationDate = this.creationDate,
        candidatureAcceptances = setOf()
    )

fun CandidatureEntity.mapToDomain(cut: Boolean = false, getProposition: Boolean = false): Candidature =
    Candidature(
        candidatureId = this.candidatureId,
        accepted = this.accepted,
        student = this.student!!.mapToDomain(cut),
        subject = this.subject!!.mapToDomain(cut),
        creationDate = this.creationDate,
        candidatureAcceptances = if(getProposition) this.candidatureAcceptances.map { it.mapToDomain(getProposition) }.toSet() else setOf()
    )

fun CandidatureAcceptance.mapToEntity(): CandidatureAcceptanceEntity =
    CandidatureAcceptanceEntity(
        accepted = this.accepted,
        studentId = this.student.studentId!!,
        candidatureId = this.candidature.candidatureId!!,
    )

fun CandidatureAcceptanceEntity.mapToDomain(cut: Boolean = false): CandidatureAcceptance =
    CandidatureAcceptance(
        candidatureAcceptanceId = this.candidatureAcceptanceId,
        accepted = this.accepted,
        student = this.student!!.mapToDomain(cut),
        candidature = this.candidature!!.mapToDomain(cut),
    )
