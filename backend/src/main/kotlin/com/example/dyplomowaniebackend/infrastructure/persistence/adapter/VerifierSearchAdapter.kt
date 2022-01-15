package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.VerifierSearchPort
import com.example.dyplomowaniebackend.domain.model.Verifier
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.VerifierRepository
import org.springframework.stereotype.Service

@Service
class VerifierSearchAdapter(val verifierRepository: VerifierRepository) : VerifierSearchPort {
    override fun findVerifiersByGraduationProcessId(graduationProcessId: Long): List<Verifier> {
        return verifierRepository.findByGraduationProcessId(graduationProcessId).map { it.mapToDomain() }
    }
}