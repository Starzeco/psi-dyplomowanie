package com.example.dyplomowaniebackend.domain.graduationProcess.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.SubjectSearchPort
import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.domain.model.SubjectStatus
import com.example.dyplomowaniebackend.domain.model.SubjectType
import org.springframework.stereotype.Service

@Service("subjectSearchAdapterApi")
class SubjectSearchAdapter(private val subjectSearchPort: SubjectSearchPort) :
    com.example.dyplomowaniebackend.domain.graduationProcess.port.api.SubjectSearchPort {
    override fun getSubjectsForStudent(
        studentId: Long,
        searchPhrase: String?,
        subjectType: SubjectType?,
        availableSubjects: Boolean,
        subjectStatus: SubjectStatus?
    ): Set<Subject> {
        val statuses = if(availableSubjects) listOf(SubjectStatus.VERIFIED) else getSearchedStatuses(subjectStatus)
        val subjects = subjectSearchPort.getSubjectsInStatus(statuses)
        val phrase = searchPhrase.orEmpty()
        val filteredSubjects = subjects
            .filter { it.topic.contains(phrase, ignoreCase = true) || it.supervisor.name.contains(phrase, ignoreCase = true) }
            .filter { subjectType == null || (subjectType == SubjectType.GROUP && it.realiseresNumber > 1) || (subjectType == SubjectType.INDIVIDUAL && it.realiseresNumber <= 1) }
            .toSet()
        return if(availableSubjects) filteredSubjects
        else filteredSubjects.filter { it.initiator != null && it.initiator.studentId == studentId }.toSet()
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

    override fun getSubjectById(subjectId: Long): Subject = subjectSearchPort.getById(subjectId, true)
}