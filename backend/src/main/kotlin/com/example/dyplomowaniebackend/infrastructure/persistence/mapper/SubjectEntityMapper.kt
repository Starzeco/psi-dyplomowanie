package com.example.dyplomowaniebackend.infrastructure.persistence.mapper

import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.SubjectEntity


fun SubjectEntity.mapToDomain(cut: Boolean = false): Subject =
    Subject(
        subjectId = this.subjectId,
        topic = this.topic,
        topicInEnglish = this.topicInEnglish,
        objective = this.objective,
        objectiveInEnglish = this.objectiveInEnglish,
        realizationLanguage = this.realizationLanguage,
        realiseresNumber = this.realiseresNumber,
        accepted = this.accepted,
        status = this.status,
        creationDate = this.creationDate,
        initiator = if(cut) this.initiator?.mapToDomain(true) else this.initiator?.mapToDomain(false),
        supervisor = this.supervisor!!.mapToDomain(),
        graduationProcess = this.graduationProcess!!.mapToDomain(),
    )

fun Subject.mapToEntity(): SubjectEntity =
    SubjectEntity(
        subjectId = this.subjectId,
        topic = this.topic,
        topicInEnglish = this.topicInEnglish,
        objective = this.objective,
        objectiveInEnglish = this.objectiveInEnglish,
        realizationLanguage = this.realizationLanguage,
        realiseresNumber = this.realiseresNumber,
        accepted = this.accepted,
        status = this.status,
        creationDate = this.creationDate,
        studentId = this.initiator?.studentId,
        staffMemberId = this.supervisor.staffMemberId!!,
        graduationProcessId = this.graduationProcess.graduationProcessId!!
    )
