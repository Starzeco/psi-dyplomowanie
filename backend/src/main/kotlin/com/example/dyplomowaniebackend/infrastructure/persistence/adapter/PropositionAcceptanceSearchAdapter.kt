package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance
import com.example.dyplomowaniebackend.domain.submission.port.persistence.PropositionAcceptanceSearchPort
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.PropositionAcceptancesRepository
import org.springframework.stereotype.Component

@Component
class PropositionAcceptanceSearchAdapter(
    private val propositionAcceptancesRepository: PropositionAcceptancesRepository
) : PropositionAcceptanceSearchPort {

    override fun getAllByStudentId(studentId: Long): Set<PropositionAcceptance> =
        propositionAcceptancesRepository.findByStudentStudentId(studentId)
            .map { it.mapToDomain() }
            .toSet()

}
