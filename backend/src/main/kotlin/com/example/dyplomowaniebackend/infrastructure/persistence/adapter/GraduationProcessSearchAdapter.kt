package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.GraduationProcessSearchPort
import com.example.dyplomowaniebackend.domain.model.GraduationProcess
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapCreation
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.GraduationProcessRepository
import org.springframework.stereotype.Service

@Service
class GraduationProcessSearchAdapter(val graduationProcessRepository: GraduationProcessRepository) : GraduationProcessSearchPort {
    override fun getGraduationProcessById(graduationProcessId: Long?): GraduationProcess? {
        if(graduationProcessId == null) return null
        return graduationProcessRepository.findById(graduationProcessId).map { gp -> gp.mapCreation() }.get()
    }
}