package com.example.dyplomowaniebackend.domain.candidature.adapter

import com.example.dyplomowaniebackend.domain.candidature.port.api.CandidatureCreationPort
import com.example.dyplomowaniebackend.domain.candidature.port.persistance.CandidatureMutationPort
import com.example.dyplomowaniebackend.domain.candidature.port.persistance.SubjectSearchPort
import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.StudentSearchPort
import com.example.dyplomowaniebackend.domain.model.Candidature
import com.example.dyplomowaniebackend.domain.model.CandidatureAcceptance
import com.example.dyplomowaniebackend.domain.model.CandidatureCreation
import com.example.dyplomowaniebackend.domain.model.exception.CandidatureConstraintViolationException
import org.springframework.stereotype.Service

@Service
class CandidatureCreationAdapter(
    private val studentSearchPort: StudentSearchPort,
    private val subjectSearchPort: SubjectSearchPort,
    private val candidatureMutationPort: CandidatureMutationPort,
) : CandidatureCreationPort {
    override fun createCandidature(candidatureCreation: CandidatureCreation): Long {
        val studentIds = candidatureCreation.coauthors.plus(candidatureCreation.studentId)
        val studentsWhoRealizesAnySubject = studentSearchPort.findStudentsWhoRealizesAnySubject(studentIds)
        if (studentsWhoRealizesAnySubject.isNotEmpty())
            throw CandidatureConstraintViolationException(
                "Can not create a candidature when one of its students realize a subject: [${
                    studentsWhoRealizesAnySubject.map { it.studentId }.joinToString(
                        " | "
                    )
                }]"
            )

        val candidature = Candidature(
            student = studentSearchPort.getStudentById(candidatureCreation.studentId),
            subject = subjectSearchPort.getSubjectById(candidatureCreation.subjectId)
        )
        val candidatureId = candidatureMutationPort.insert(candidature)
        val candidatureAcceptances = candidatureCreation.coauthors.map {
            CandidatureAcceptance(
                student = studentSearchPort.getStudentById(it),
                candidature = candidature.copy(candidatureId = candidatureId)
            )
        }.toSet()
        candidatureMutationPort.insertAcceptances(candidatureAcceptances)
        return candidatureId
    }

}
