package com.example.dyplomowaniebackend.infrastructure.persistence.mapper

import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.SubjectEntity


fun SubjectEntity.mapToDomain(): Subject =
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
        initiator = null,
        supervisor = this.supervisor.mapToDomain(),
        graduationProcess = this.graduationProcess.mapToDomain(),
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
        initiator = null,
        supervisor = this.supervisor.mapToEntity(),
        graduationProcess = this.graduationProcess.mapToEntity(),
    )
