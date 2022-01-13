package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.candidature.port.persistance.CandidatureSearchPort
import com.example.dyplomowaniebackend.domain.model.CandidatureAcceptance
import com.example.dyplomowaniebackend.domain.model.exception.EntityNotFoundException
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.CandidatureAcceptanceRepository
import org.springframework.stereotype.Service

@Service
class CandidatureSearchAdapter(
    private val candidatureAcceptanceRepository: CandidatureAcceptanceRepository
) : CandidatureSearchPort {
    override fun getCandidatureAcceptanceById(candidatureAcceptanceId: Long): CandidatureAcceptance =
        candidatureAcceptanceRepository.findById(candidatureAcceptanceId)
            .map { it.mapToDomain() }
            .orElseThrow { throw EntityNotFoundException(CandidatureAcceptance::class, candidatureAcceptanceId) }
}
