package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.GraduationProcessSearchPort
import com.example.dyplomowaniebackend.domain.model.GraduationProcess
import com.example.dyplomowaniebackend.domain.model.exception.EntityNotFoundException
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.GraduationProcessRepository
import org.springframework.stereotype.Service

@Service
class GraduationProcessSearchAdapter(val graduationProcessRepository: GraduationProcessRepository) : GraduationProcessSearchPort {
    override fun getGraduationProcessById(graduationProcessId: Long): GraduationProcess =
        graduationProcessRepository.findById(graduationProcessId)
            .map { gp -> gp.mapToDomain() }
            .orElseThrow{ throw EntityNotFoundException(GraduationProcess::class, graduationProcessId) }
}