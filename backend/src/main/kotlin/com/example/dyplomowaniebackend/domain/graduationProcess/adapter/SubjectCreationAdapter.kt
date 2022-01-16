package com.example.dyplomowaniebackend.domain.graduationProcess.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.api.SubjectCreationPort
import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.*
import com.example.dyplomowaniebackend.domain.model.*
import com.example.dyplomowaniebackend.domain.model.exception.SubjectConstraintViolationException
import com.example.dyplomowaniebackend.domain.model.exception.SubjectStatusChangeException
import com.example.dyplomowaniebackend.domain.submission.port.persistence.PropositionAcceptanceSearchPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock
import java.time.Instant

@Service
@Transactional
class SubjectCreationAdapter(
    private val studentSearchPort: StudentSearchPort,
    private val studentMutationPort: StudentMutationPort,
    private val staffSearchPort: StaffSearchPort,
    private val graduationProcessSearchPort: GraduationProcessSearchPort,
    private val propositionAcceptanceMutationPort: PropositionAcceptanceMutationPort,
    private val propositionAcceptanceSearchPort: PropositionAcceptanceSearchPort,
    private val subjectMutationPort: SubjectMutationPort,
    private val subjectSearchPort: SubjectSearchPort,
    private val clock: Clock
) : SubjectCreationPort {
    override fun createSubject(subjectCreation: SubjectCreation): Subject {
        if (subjectCreation.initiatorId == null && subjectCreation.proposedRealiserIds.isNotEmpty())
            throw SubjectConstraintViolationException("If there is not initiator then proposed realisers should be empty")
        val initiator: Student? = subjectCreation.initiatorId?.let { studentSearchPort.findById(it) }
        val supervisor: StaffMember = staffSearchPort.getStaffMemberById(subjectCreation.supervisorId)
        val graduationProcess: GraduationProcess =
            graduationProcessSearchPort.getGraduationProcessById(subjectCreation.graduationProcessId)
        val realisersNumber = if (subjectCreation.initiatorId == null) subjectCreation.realiseresNumber else subjectCreation.proposedRealiserIds.size + 1
        val subject = Subject(
            topic = subjectCreation.topic,
            topicInEnglish = subjectCreation.topicInEnglish,
            objective = subjectCreation.objective,
            objectiveInEnglish = subjectCreation.objectiveInEnglish,
            realizationLanguage = subjectCreation.realizationLanguage,
            realiseresNumber = realisersNumber,
            status = SubjectStatus.DRAFT,
            initiator = initiator,
            supervisor = supervisor,
            graduationProcess = graduationProcess,
            creationDate = Instant.now(clock)
        )
        val insertedSubject: Subject = subjectMutationPort.insert(subject)
        createPropositionAcceptances(insertedSubject, subjectCreation.proposedRealiserIds)
        return insertedSubject
    }

    private fun createPropositionAcceptances(
        subject: Subject,
        proposedRealiserIds: Set<Long>
    ): Set<PropositionAcceptance> {
        return propositionAcceptanceMutationPort.insertAll(
            proposedRealiserIds.map { studentId ->
                PropositionAcceptance(
                    student = studentSearchPort.getById(studentId),
                    subject = subject
                )
            }.toSet()
        )
    }

    override fun rejectSubject(subjectId: Long): SubjectStatusUpdate {
        val subject: Subject = subjectSearchPort.getById(subjectId)
        return if (canSubjectBeRejected(subject)) updateStatus(SubjectStatusUpdate(subjectId, SubjectStatus.REJECTED))
        else throw SubjectStatusChangeException("Subject with id $subjectId can not be rejected")
    }

    // Subject is in DRAFT status (rejected by supervisor) or subject was made by student and supervisor accepted it (rejected by student)
    private fun canSubjectBeRejected(subject: Subject): Boolean =
        subject.status == SubjectStatus.DRAFT || (subject.status == SubjectStatus.ACCEPTED_BY_SUPERVISOR && subject.initiator != null)

    override fun acceptSubjectPreparedBySupervisor(subjectId: Long): SubjectStatusUpdate {
        val subject: Subject = subjectSearchPort.getById(subjectId)
        return if (canSubjectBeAcceptedBySupervisor(subject)) updateStatus(
            SubjectStatusUpdate(
                subjectId,
                SubjectStatus.ACCEPTED_BY_SUPERVISOR
            )
        )
        else throw SubjectStatusChangeException("Subject with id $subjectId can not be accepted by supervisor")
    }

    // Subject is made by student, and it is individual subject or all members accepted proposition
    private fun canSubjectBeAcceptedBySupervisor(subject: Subject): Boolean {
        val acceptances: Set<PropositionAcceptance> =
            propositionAcceptanceSearchPort.getAllBySubjectId(subject.subjectId!!)
        return subject.initiator != null
                && subject.status == SubjectStatus.DRAFT
                && (acceptances.isEmpty() || acceptances.all { it.accepted != null && it.accepted })
    }

    override fun acceptSubjectPreparedByInitiator(subjectId: Long): SubjectStatusUpdate {
        val subject: Subject = subjectSearchPort.getById(subjectId)
        val realisers: Set<Student> =
            propositionAcceptanceSearchPort.getAllBySubjectId(subject.subjectId!!)
                .map { it.student }
                .toSet()
        return if (canSubjectBeAcceptedByInitiator(subject, realisers)) {
            val students: Set<Student> = realisers.plus(subject.initiator!!) // Initiator exists, checked in if
            assignSubject(students, subject)
            updateStatus(
                SubjectStatusUpdate(
                    subjectId,
                    SubjectStatus.ACCEPTED_BY_INITIATOR
                )
            )
        } else
            throw SubjectStatusChangeException("Subject with id $subjectId can not be accepted by initiator")
    }

    private fun assignSubject(students: Set<Student>, subject: Subject): Set<Long> {
        val studentsIds: Set<Long> = students.map { it.studentId!! }.toSet()
        return studentMutationPort.updateSubjectIdByStudentIdIn(studentsIds, subject.subjectId!!)
    }

    // Subject is made by student, and initiator has not subject, and all the members have not subject
    private fun canSubjectBeAcceptedByInitiator(subject: Subject, realisers: Set<Student>): Boolean =
        subject.initiator != null
                && subject.status == SubjectStatus.ACCEPTED_BY_SUPERVISOR
                && subject.initiator.subject == null
                && (realisers.isEmpty() || realisers.all { it.subject == null })

    override fun sendSubjectToVerification(subjectId: Long): SubjectStatusUpdate {
        val subject: Subject = subjectSearchPort.getById(subjectId)
        return if (canSubjectBeSentToVerification(subject)) updateStatus(
            SubjectStatusUpdate(
                subjectId,
                SubjectStatus.IN_VERIFICATION
            )
        )
        else throw SubjectStatusChangeException("Subject with id $subjectId can not be sent to verification")
    }

    private fun canSubjectBeSentToVerification(subject: Subject): Boolean =
        subject.status == SubjectStatus.ACCEPTED_BY_INITIATOR
                || (subject.status == SubjectStatus.DRAFT && subject.initiator == null)

    private fun updateStatus(subjectStatusUpdate: SubjectStatusUpdate): SubjectStatusUpdate =
        subjectMutationPort.updateStatus(subjectStatusUpdate)
}
