package com.example.dyplomowaniebackend.domain.graduationProcess.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.api.SubjectCreationPort
import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.SubjectMutationPort
import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.*
import com.example.dyplomowaniebackend.domain.model.*
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
        val initiator: Student? = subjectCreation.initiatorId?.let { studentSearchPort.findStudentById(it) }
        val supervisor: StaffMember = staffSearchPort.getStudentById(subjectCreation.supervisorId)
        val graduationProcess: GraduationProcess =
            graduationProcessSearchPort.getGraduationProcessById(subjectCreation.graduationProcessId)
        val subject = Subject(
            topic = subjectCreation.topic,
            topicInEnglish = subjectCreation.topicInEnglish,
            objective = subjectCreation.objective,
            objectiveInEnglish = subjectCreation.objectiveInEnglish,
            realizationLanguage = subjectCreation.realizationLanguage,
            realiseresNumber = subjectCreation.realiseresNumber,
            status = SubjectStatus.DRAFT,
            initiator = initiator,
            supervisor = supervisor,
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
                    student = studentSearchPort.getStudentById(studentId),
                    subject = subject
                )
            }.toSet()
        )
    }
}