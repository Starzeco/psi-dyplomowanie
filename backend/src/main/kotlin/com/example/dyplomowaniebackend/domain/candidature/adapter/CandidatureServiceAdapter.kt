package com.example.dyplomowaniebackend.domain.candidature.adapter

import com.example.dyplomowaniebackend.domain.candidature.port.api.CandidatureServicePort
import com.example.dyplomowaniebackend.domain.candidature.port.persistance.CandidatureMutationPort
import com.example.dyplomowaniebackend.domain.candidature.port.persistance.CandidatureSearchPort
import com.example.dyplomowaniebackend.domain.candidature.port.persistance.SubjectSearchPort
import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.StudentSearchPort
import com.example.dyplomowaniebackend.domain.model.*
import com.example.dyplomowaniebackend.domain.model.exception.CandidatureAcceptanceConstraintViolationException
import com.example.dyplomowaniebackend.domain.model.exception.CandidatureConstraintViolationException
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.Instant

@Service
class CandidatureServiceAdapter(
    private val studentSearchPort: StudentSearchPort,
    private val subjectSearchPort: SubjectSearchPort,
    private val candidatureSearchPort: CandidatureSearchPort,
    private val candidatureMutationPort: CandidatureMutationPort,
    private val clock: Clock
) : CandidatureServicePort {
    override fun createCandidature(candidatureCreation: CandidatureCreation): Candidature {
        val studentIds = candidatureCreation.coauthors.plus(candidatureCreation.studentId)
        val studentsWhoRealizesAnySubject = studentSearchPort.findStudentsByStudentIdInAndSubjectIdNotNull(studentIds)
        if (studentsWhoRealizesAnySubject.isNotEmpty()) throw CandidatureConstraintViolationException(
            "Can not create a candidature when one of its students realize a subject: [${
                studentsWhoRealizesAnySubject.map { it.studentId }.joinToString(
                    " | "
                )
            }]"
        )

        val candidature = Candidature(
            student = studentSearchPort.getStudentById(candidatureCreation.studentId),
            subject = subjectSearchPort.getSubjectById(candidatureCreation.subjectId),
            creationDate = Instant.now(clock)
        )
        val insertedCandidature = candidatureMutationPort.insert(candidature)
        val candidatureAcceptances = candidatureCreation.coauthors.map {
            CandidatureAcceptance(
                student = studentSearchPort.getStudentById(it),
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
            candidatureSearchPort.existsCandidatureAcceptancesByCandidatureIdAndAcceptedIsFalseOrAcceptedIsNull(candidatureId)
        if (existsNotAcceptedCandidatureAcceptance)
            throw CandidatureConstraintViolationException("Could not decide about a candidature with id $candidatureId because some not accepted candidatures acceptances exist")
        val candidatureUpdated =
            candidatureMutationPort.updateAcceptedById(candidatureId, accepted) == 1L
        if (!candidatureUpdated) throw CandidatureAcceptanceConstraintViolationException(
            "Can not decide about candidature with id $candidatureId because it have not been updated"
        )
        return candidatureId
    }

    //TODO: studentId should be passed from auth context
    // We should check if a student owns a candidature acceptance
    override fun decideAboutCandidatureAcceptance(candidatureAcceptanceId: Long, accepted: Boolean): Long {
        val candidatureAcceptance = candidatureSearchPort.getCandidatureAcceptanceById(candidatureAcceptanceId)
        val studentId = candidatureAcceptance.student.studentId!!
        val doesStudentRealizeSubject = studentSearchPort.existsStudentByStudentIdAndSubjectIdNotNull(studentId)
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
