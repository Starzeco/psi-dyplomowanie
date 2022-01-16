package com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence

import com.example.dyplomowaniebackend.domain.model.Verifier

interface VerifierSearchPort {
    fun findVerifiersByGraduationProcessId(graduationProcessId: Long): List<Verifier>
}