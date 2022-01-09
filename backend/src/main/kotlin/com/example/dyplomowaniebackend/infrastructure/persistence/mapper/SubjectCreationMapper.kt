package com.example.dyplomowaniebackend.infrastructure.persistence.mapper

import com.example.dyplomowaniebackend.domain.model.*
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.*

fun StudentEntity.mapToDomain(): Student {
    return Student(
        studentId = this.studentId!!,
        index = this.index,
        email = this.email,
        name = this.name,
        surname = this.surname,
        subjectProposals = setOf(),
        subject = null,
        propositionAcceptances = setOf(),
        candidatures = setOf(),
        candidatureAcceptances = setOf(),
        graduationProcesses = setOf()
    )
}

fun StaffMemberEntity.mapToDomain(): StaffMember {
    return StaffMember(
        staffMemberId = this.staffMemberId!!,
        email = this.email,
        name = this.name,
        title = this.title,
        surname = this.surname,
        currentWorkload = this.currentWorkload,
        absoluteWorkload = this.absoluteWorkload,
        subjects = setOf(),
        faculty = this.faculty!!.mapToDomain(),
        verifiers = setOf()
    )
}

fun FacultyEntity.mapToDomain(): Faculty {
    return Faculty(
        facultyId = this.facultyId!!,
        name = this.name,
        shortName = this.shortName,
        active = this.active,
        staffMembers = setOf(),
        degreeCourses = setOf()
    )
}

fun GraduationProcessEntity.mapToDomain(): GraduationProcess {
    return GraduationProcess(
        graduationProcessId = this.graduationProcessId!!,
        cSDeadline = this.cSDeadline,
        vFDeadline = this.vFDeadline,
        cADeadline = this.cADeadline,
        sPDeadline = this.sPDeadline,
        initialSemester = this.initialSemester,
        finalSemester = this.finalSemester,
        degree = this.degree,
        hCPerSubject = this.hCPerSubject,
        students = setOf(),
        subjects = setOf(),
        degreeCourse = this.degreeCourse!!.mapToDomain(),
        verifiers = setOf()
    )
}

fun DegreeCourseEntity.mapToDomain(): DegreeCourse {
    return DegreeCourse(
        degreeCourseId = this.degreeCourseId!!,
        name = this.name,
        active = this.active,
        faculty = this.faculty!!.mapToDomain(),
        graduationProcesses = setOf()
    )
}

fun PropositionAcceptance.mapToCreationEntity(): PropositionAcceptanceEntity {
    return PropositionAcceptanceEntity(
        studentId = this.student.studentId!!,
        subjectId = this.subject.subjectId!!,
    )
}

fun PropositionAcceptanceEntity.mapToDomain(): PropositionAcceptance {
    return PropositionAcceptance(
        propositionAcceptanceId = this.propositionAcceptanceId!!,
        accepted = this.accepted,
        student = this.student!!.mapToDomain(),
        subject = this.subject!!.mapToDomain(),
    )
}

fun SubjectEntity.mapToDomain(): Subject {
    return Subject(
        subjectId = this.subjectId!!,
        topic = this.topic,
        topicInEnglish = this.topicInEnglish,
        objective = this.objective,
        objectiveInEnglish = this.objectiveInEnglish,
        realizationLanguage = this.realizationLanguage,
        realiseresNumber = this.realiseresNumber,
        accepted = this.accepted,
        status = this.status,
        creationDate = this.creationDate,
        initiator = this.initiator?.mapToDomain(),
        realiser = setOf(),
        propositionAcceptances = setOf(),
        candidatures = setOf(),
        supervisor = this.supervisor!!.mapToDomain(),
        verifications = setOf(),
        graduationProcess = this.graduationProcess!!.mapToDomain()
    )
}

fun Subject.mapToCreationEntity(): SubjectEntity {
    return SubjectEntity(
        topic = this.topic,
        topicInEnglish = this.topicInEnglish,
        objective = this.objective,
        objectiveInEnglish = this.objectiveInEnglish,
        realizationLanguage = this.realizationLanguage,
        realiseresNumber = this.realiseresNumber,
        status = this.status,
        studentId = this.initiator?.studentId,
        realiser = setOf(),
        propositionAcceptances = setOf(),
        candidatures = setOf(),
        staffMemberId = this.supervisor.staffMemberId!!,
        verifications = setOf(),
        graduationProcessId = this.graduationProcess.graduationProcessId!!
    )
}