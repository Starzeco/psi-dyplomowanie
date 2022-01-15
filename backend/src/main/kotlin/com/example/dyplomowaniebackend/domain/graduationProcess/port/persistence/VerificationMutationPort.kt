package com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence

import com.example.dyplomowaniebackend.domain.model.Verification

interface VerificationMutationPort {
    fun insertAll(verifications: List<Verification>): List<Verification>
    fun updateOldVerifications(subjectId: Long)
}