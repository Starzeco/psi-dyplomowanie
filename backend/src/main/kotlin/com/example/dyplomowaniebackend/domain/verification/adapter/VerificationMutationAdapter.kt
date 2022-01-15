package com.example.dyplomowaniebackend.domain.verification.adapter

import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.domain.model.SubjectStatus
import com.example.dyplomowaniebackend.domain.model.SubjectStatusUpdate
import com.example.dyplomowaniebackend.domain.model.Verification
import com.example.dyplomowaniebackend.domain.verification.port.persistence.SubjectMutationPort
import com.example.dyplomowaniebackend.domain.verification.port.persistence.VerificationMutationPort
import com.example.dyplomowaniebackend.domain.verification.port.persistence.VerificationSearchPort
import org.springframework.stereotype.Service

@Service("verificationMutationAdapterApi")
class VerificationMutationAdapter(val verificationMutationPort: VerificationMutationPort,
                                  val verificationSearchPort: VerificationSearchPort,
                                  val subjectMutationPort: SubjectMutationPort,
) : com.example.dyplomowaniebackend.domain.verification.port.api.VerificationMutationPort {
    override fun verifyVerification(verificationId: Long, decision: Boolean, justification: String): Verification {
        val verificationUpdated: Verification = verificationMutationPort.verifyVerification(verificationId, decision, justification)
        updateSubjectIfNeeded(verificationUpdated.subject)
        return verificationUpdated
    }

    override fun verifyAllVerifications(verifierId: Long, decision: Boolean, justification: String): List<Verification> {
        val verificationsUpdated: List<Verification> = verificationMutationPort.verifyAllVerifications(verifierId, decision, justification)
        verificationsUpdated.forEach { updateSubjectIfNeeded(it.subject) }
        return verificationsUpdated
    }

    private fun updateSubjectIfNeeded(subject: Subject): SubjectStatusUpdate? {
        val subjectVerifications: List<Verification> = verificationSearchPort.findSubjectVerifications(subject.subjectId!!)
        return if(subjectVerifications.all { it.verified != null && it.verified }) {
            if(subject.initiator != null)
                updateStatus(SubjectStatusUpdate(subject.subjectId, SubjectStatus.RESERVED))
            else
                updateStatus(SubjectStatusUpdate(subject.subjectId, SubjectStatus.VERIFIED))
        }
        else if(subjectVerifications.any { it.verified != null && !it.verified })
            updateStatus(SubjectStatusUpdate(subject.subjectId, SubjectStatus.IN_CORRECTION))
        else
            null
    }

    private fun updateStatus(subjectStatusUpdate: SubjectStatusUpdate): SubjectStatusUpdate =
        subjectMutationPort.updateStatus(subjectStatusUpdate)
}