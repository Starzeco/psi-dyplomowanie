package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.model.PropositionAcceptance
import com.example.dyplomowaniebackend.domain.submission.port.PropositionAcceptancesSearchPort
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.PropositionAcceptancesRepository
import org.springframework.stereotype.Component

@Component
class PropositionAcceptancesSearchAdapter(
    private val propositionAcceptancesRepository: PropositionAcceptancesRepository
) : PropositionAcceptancesSearchPort {

    override fun getAllByStudentId(studentId: Long): Set<PropositionAcceptance> =
        propositionAcceptancesRepository.findByStudentStudentId(studentId)
            .map { it.mapToDomain() }
            .toSet()

}
