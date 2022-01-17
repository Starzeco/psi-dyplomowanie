package com.example.dyplomowaniebackend.domain.candidature.adapter

import com.example.dyplomowaniebackend.domain.candidature.port.api.CandidatureServicePort
import com.example.dyplomowaniebackend.domain.candidature.port.persistance.CandidatureMutationPort
import com.example.dyplomowaniebackend.domain.candidature.port.persistance.CandidatureSearchPort
import com.example.dyplomowaniebackend.domain.candidature.port.persistance.SubjectSearchPort
import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.StudentMutationPort
import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.StudentSearchPort
import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.SubjectMutationPort
import com.example.dyplomowaniebackend.domain.model.*
import com.example.dyplomowaniebackend.domain.model.exception.CandidatureAcceptanceConstraintViolationException
import com.example.dyplomowaniebackend.domain.model.exception.CandidatureConstraintViolationException
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.Instant

@Service
class CandidatureServiceAdapter(
    private val studentSearchPort: StudentSearchPort,
    private val studentMutationPort: StudentMutationPort,
    private val subjectSearchPort: SubjectSearchPort,
    private val subjectMutationPort: SubjectMutationPort,
    private val candidatureSearchPort: CandidatureSearchPort,
    private val candidatureMutationPort: CandidatureMutationPort,
    private val clock: Clock
) : CandidatureServicePort {
    override fun createCandidature(candidatureCreation: CandidatureCreation): Candidature {
        val subject = subjectSearchPort.getById(candidatureCreation.subjectId, false)
        val subjectId = subject.subjectId!!

        // verify if candidature can be created
        if (subject.status != SubjectStatus.VERIFIED)
            throw CandidatureConstraintViolationException("Could not create a candidature because a subject $subjectId is not ${SubjectStatus.VERIFIED}")

        val hasSubjectAssignedStudents = studentSearchPort.existsAllBySubjectId(subjectId)
        if (hasSubjectAssignedStudents)
            throw CandidatureConstraintViolationException("Could not create a candidature because a subject $subjectId has already assigned students")

        val studentIds = candidatureCreation.coauthors.plus(candidatureCreation.studentId)
        val studentsWhoRealizesAnySubject = studentSearchPort.findAllByStudentIdInAndSubjectIdNotNull(studentIds)
        if (studentsWhoRealizesAnySubject.isNotEmpty()) throw CandidatureConstraintViolationException(
            "Can not create a candidature when one of its students [${
                studentsWhoRealizesAnySubject.map { it.studentId }.joinToString(
                    " | "
                )
            }] realize a subject $subjectId"
        )

        // preparing and inserting data
        val candidature = Candidature(
            student = studentSearchPort.getById(candidatureCreation.studentId),
            subject = subject,
            creationDate = Instant.now(clock)
        )
        val insertedCandidature = candidatureMutationPort.insert(candidature)
        val candidatureAcceptances = candidatureCreation.coauthors.map {
            CandidatureAcceptance(
                student = studentSearchPort.getById(it),
                candidature = insertedCandidature
            )
        }.toSet()
        candidatureMutationPort.insertAcceptances(candidatureAcceptances)
        return insertedCandidature
    }

    //TODO: we should check if a user (supervisor) owns candidature
    // I would like to pass smt like context
    override fun decideAboutCandidature(candidatureId: Long, accepted: Boolean): Long {
        val existsNotAcceptedCandidatureAcceptance =
            candidatureSearchPort.existsCandidatureAcceptancesByCandidatureIdAndAcceptedIsFalseOrAcceptedIsNull(
                candidatureId
            )

        // verify candidature's constrains
        if (existsNotAcceptedCandidatureAcceptance)
            throw CandidatureConstraintViolationException("Could not decide about a candidature with id $candidatureId because some not accepted candidature acceptances exist")

        val candidature = candidatureSearchPort.getCandidatureById(candidatureId)
        if (candidature.subject.status != SubjectStatus.VERIFIED)
            throw CandidatureConstraintViolationException("Could not decide about a candidature with id $candidatureId because a subject is not ${SubjectStatus.VERIFIED}")
        if (candidature.subject.initiator != null)
            throw CandidatureConstraintViolationException("Could not decide about a candidature with id $candidatureId because a subject has an initiator")

        // try to update the candidature
        val candidatureUpdated = candidatureMutationPort.updateAcceptedById(candidatureId, accepted) == 1L
        if (!candidatureUpdated) throw CandidatureAcceptanceConstraintViolationException(
            "Can not decide about candidature with id $candidatureId because it have not been updated"
        )

        val subjectId = candidature.subject.subjectId!!

        // change a subject status
        val subjectStatus = if (accepted) SubjectStatus.RESERVED else SubjectStatus.REJECTED
        val subjectStatusUpdate = SubjectStatusUpdate(
            subjectId = subjectId,
            status = subjectStatus
        )
        subjectMutationPort.updateStatus(subjectStatusUpdate)

        // if the candidature is accepted then reject others and assign students to a subject
        if (accepted) {
            candidatureMutationPort.updateAcceptedToFalseWithExclusiveIdBySubjectId(subjectId, candidatureId)
            val initiatorId = candidature.student.studentId!!
            val coauthorsIds = candidatureSearchPort.getCandidatureAcceptanceByCandidatureId(candidatureId).map { it.student.studentId!! }
            val studentIds = coauthorsIds.plus(initiatorId).toSet()
            studentMutationPort.updateSubjectIdByStudentIdIn(studentIds, subjectId)
        }

        return candidatureId
    }

    //TODO: studentId should be passed from auth context
    override fun decideAboutCandidatureAcceptance(candidatureAcceptanceId: Long, accepted: Boolean): Long {
        val candidatureAcceptance = candidatureSearchPort.getCandidatureAcceptanceById(candidatureAcceptanceId)
        val studentId = candidatureAcceptance.student.studentId!!

        // verify if acceptance can be accepted / rejected - the student can not be attached to a subject
        val doesStudentRealizeSubject = studentSearchPort.existsByStudentIdAndSubjectIdNotNull(studentId)
        if (doesStudentRealizeSubject) throw CandidatureAcceptanceConstraintViolationException(
            "Can not decide about candidature acceptance with id $candidatureAcceptanceId because a student $studentId realizes a subject"
        )

        val candidatureAcceptanceUpdated =
            candidatureMutationPort.updateAcceptanceAcceptedById(candidatureAcceptanceId, accepted) == 1L
        if (!candidatureAcceptanceUpdated) throw CandidatureAcceptanceConstraintViolationException(
            "Can not decide about candidature acceptance with id $candidatureAcceptanceId because it have not been updated"
        )
        return candidatureAcceptanceId
    }

    //TODO: studentId should be passed from auth context
    override fun getAllCandidatureAsSupervisor(
        supervisorId: Long,
        graduationProcessId: Long,
        phrase: String?,
        type: CandidatureType?,
        status: CandidatureStatus?
    ): Set<Pair<Candidature, Set<CandidatureAcceptance>>> = candidatureSearchPort.getAllCandidatureAsSupervisor(
        supervisorId = supervisorId,
        graduationProcessId = graduationProcessId,
        phrase = phrase,
        type = type,
        status = status
    ).map {
        Pair(it, candidatureSearchPort.getCandidatureAcceptanceByCandidatureId(it.candidatureId!!))
    }.toSet()

    override fun getAllCandidatureAsStudent(
        studentId: Long,
        graduationProcessId: Long,
        phrase: String?,
        type: CandidatureType?,
        status: CandidatureStatus?
    ): Set<Pair<Candidature, Set<CandidatureAcceptance>>> = candidatureSearchPort.getAllCandidatureAsStudent(
        studentId = studentId,
        graduationProcessId = graduationProcessId,
        phrase = phrase,
        type = type,
        status = status
    ).map {
        Pair(it, candidatureSearchPort.getCandidatureAcceptanceByCandidatureId(it.candidatureId!!))
    }.toSet()

}
