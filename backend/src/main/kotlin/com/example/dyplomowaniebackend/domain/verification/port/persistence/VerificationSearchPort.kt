package com.example.dyplomowaniebackend.domain.verification.port.persistence

import com.example.dyplomowaniebackend.domain.model.SubjectType
import com.example.dyplomowaniebackend.domain.model.Verification

interface VerificationSearchPort {
    fun findAllVerifications(
        verifierId: Long,
        phrase: String?,
        verified: Boolean?,
        subjectType: SubjectType?
    ): List<Verification>

    fun findSubjectVerifications(subjectId: Long): List<Verification>
}
