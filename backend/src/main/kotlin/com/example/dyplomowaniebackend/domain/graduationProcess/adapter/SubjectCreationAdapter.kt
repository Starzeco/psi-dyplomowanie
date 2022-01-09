package com.example.dyplomowaniebackend.domain.graduationProcess.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.api.SubjectCreationPort
import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.*
import com.example.dyplomowaniebackend.domain.model.*
import com.example.dyplomowaniebackend.domain.model.exception.GraduationProcessNotFoundException
import com.example.dyplomowaniebackend.domain.model.exception.StaffMemberNotFoundException
import com.example.dyplomowaniebackend.domain.model.exception.StudentNotFoundException
import com.example.dyplomowaniebackend.domain.model.exception.SubjectConstraintViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SubjectCreationAdapter(
    val studentSearchPort: StudentSearchPort,
    val staffSearchPort: StaffSearchPort,
    val graduationProcessSearchPort: GraduationProcessSearchPort,
    val propositionAcceptanceMutationPort: PropositionAcceptanceMutationPort,
    val subjectMutationPort: SubjectMutationPort
) : SubjectCreationPort {
    override fun createSubject(subjectCreation: SubjectCreation): Subject {
        if (subjectCreation.initiatorId == null && subjectCreation.proposedRealiserIds.isNotEmpty())
            throw SubjectConstraintViolationException("If there is not initiator then proposed realisers should be empty")
        val initiator: Student? = studentSearchPort.getStudentById(subjectCreation.initiatorId)
        val supervisor: StaffMember = staffSearchPort.getStudentById(subjectCreation.supervisorId)
            ?: throw StaffMemberNotFoundException("Supervisor not found")
        val graduationProcess: GraduationProcess =
            graduationProcessSearchPort.getGraduationProcessById(subjectCreation.graduationProcessId)
                ?: throw GraduationProcessNotFoundException("Graduation process does not exists")
        val subject = Subject(
            topic = subjectCreation.topic,
            topicInEnglish = subjectCreation.topicInEnglish,
            objective = subjectCreation.objective,
            objectiveInEnglish = subjectCreation.objectiveInEnglish,
            realizationLanguage = subjectCreation.realizationLanguage,
            realiseresNumber = subjectCreation.realiseresNumber,
            status = SubjectStatus.DRAFT,
            initiator = initiator,
            realiser = setOf(),
            propositionAcceptances = setOf(),
            candidatures = setOf(),
            supervisor = supervisor,
            verifications = setOf(),
            graduationProcess = graduationProcess
        )
        val savedSubject: Subject = subjectMutationPort.saveSubject(subject)
        createPropositionAcceptances(savedSubject, subjectCreation.proposedRealiserIds)
        return savedSubject
    }

    private fun createPropositionAcceptances(
        subject: Subject,
        proposedRealiserIds: Set<Long>
    ): Set<PropositionAcceptance> {
        return propositionAcceptanceMutationPort.savePropositionAcceptances(
            proposedRealiserIds.map { studentId ->
                PropositionAcceptance(
                    propositionAcceptanceId = null,
                    accepted = null,
                    student = studentSearchPort.getStudentById(studentId)
                        ?: throw StudentNotFoundException("Student $studentId does not exists, so he can not be added to realisers"),
                    subject = subject
                )
            }.toSet()
        )
    }
}