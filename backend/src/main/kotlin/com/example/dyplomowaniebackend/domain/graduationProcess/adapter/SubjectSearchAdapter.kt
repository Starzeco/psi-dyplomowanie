package com.example.dyplomowaniebackend.domain.graduationProcess.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.PropositionAcceptanceSearchPort
import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.SubjectSearchPort
import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.domain.model.SubjectStatus
import com.example.dyplomowaniebackend.domain.model.SubjectType
import org.springframework.stereotype.Service

@Service("subjectSearchAdapterApi")
class SubjectSearchAdapter(private val subjectSearchPort: SubjectSearchPort,
                           private val propositionAcceptanceSearchPort: PropositionAcceptanceSearchPort) :
    com.example.dyplomowaniebackend.domain.graduationProcess.port.api.SubjectSearchPort {
    override fun getSubjectsForStudent(
        studentId: Long,
        searchPhrase: String?,
        subjectType: SubjectType?,
        availableSubjects: Boolean,
        subjectStatus: SubjectStatus?
    ): Set<Subject> {
        val statuses = if(availableSubjects) listOf(SubjectStatus.VERIFIED) else getSearchedStatuses(subjectStatus)
        val subjects = subjectSearchPort.getSubjectsInStatus(statuses).map {
                sub -> sub.copy(propositionAcceptances = propositionAcceptanceSearchPort.getAllBySubjectId(sub.subjectId!!))
        }
        val phrase = searchPhrase.orEmpty()
        val filteredSubjects = subjects
            .filter { it.topic.contains(phrase, ignoreCase = true) || it.supervisor.name.contains(phrase, ignoreCase = true) }
            .filter { subjectType == null || (subjectType == SubjectType.GROUP && it.realiseresNumber > 1) || (subjectType == SubjectType.INDIVIDUAL && it.realiseresNumber <= 1) }
            .toSet()
        return if(availableSubjects) filteredSubjects
        else filteredSubjects.filter { (it.initiator != null && it.initiator.studentId == studentId)
                || it.propositionAcceptances.map { a -> a.student.studentId }.contains(studentId)
                || (it.status == SubjectStatus.RESERVED && it.realiser.map { s -> s.studentId }.contains(studentId))}
            .toSet()
    }

    private fun getSearchedStatuses(subjectStatus: SubjectStatus?): List<SubjectStatus> {
        return if(subjectStatus != null) listOf(subjectStatus)
        else listOf(
            SubjectStatus.DRAFT,
            SubjectStatus.ACCEPTED_BY_SUPERVISOR,
            SubjectStatus.ACCEPTED_BY_INITIATOR,
            SubjectStatus.IN_VERIFICATION,
            SubjectStatus.IN_CORRECTION,
            SubjectStatus.REJECTED,
            SubjectStatus.RESERVED
        )
    }

    override fun getSubjectsForSupervisor(
        supervisorId: Long,
        searchPhrase: String?,
        subjectType: SubjectType?,
        processingSubjects: Boolean,
        subjectStatus: SubjectStatus?
    ): Set<Subject> {
        val statuses = getCorrectStatusesToSearch(processingSubjects)
        val subjects = subjectSearchPort.getSubjectsInStatus(statuses).map {
                sub -> sub.copy(propositionAcceptances = propositionAcceptanceSearchPort.getAllBySubjectId(sub.subjectId!!))
        }
        val phrase = searchPhrase.orEmpty()
        val filteredSubjects = subjects
            .filter { it.topic.contains(phrase, ignoreCase = true) || it.supervisor.name.contains(phrase, ignoreCase = true) }
            .filter { subjectType == null || (subjectType == SubjectType.GROUP && it.realiseresNumber > 1) || (subjectType == SubjectType.INDIVIDUAL && it.realiseresNumber <= 1) }
            .filter { it.supervisor.staffMemberId == supervisorId }
            .toSet()
        return if(processingSubjects) filteredSubjects
        else filteredSubjects.filter { it.initiator == null
                || (it.propositionAcceptances.isEmpty() && it.realiseresNumber == 1)
                || it.propositionAcceptances.all { a -> a.accepted != null && a.accepted }
        }.toSet()
    }

    private fun getCorrectStatusesToSearch(processingSubjects: Boolean): List<SubjectStatus> {
        return if(processingSubjects) listOf(
            SubjectStatus.IN_VERIFICATION,
            SubjectStatus.IN_CORRECTION,
            SubjectStatus.VERIFIED,
            SubjectStatus.RESERVED
        )
        else listOf(
            SubjectStatus.DRAFT,
            SubjectStatus.ACCEPTED_BY_SUPERVISOR,
            SubjectStatus.ACCEPTED_BY_INITIATOR,
            SubjectStatus.REJECTED
        )
    }

    override fun getSubjectById(subjectId: Long): Subject = subjectSearchPort.getById(subjectId, true).copy(propositionAcceptances = propositionAcceptanceSearchPort.getAllBySubjectId(subjectId))
}