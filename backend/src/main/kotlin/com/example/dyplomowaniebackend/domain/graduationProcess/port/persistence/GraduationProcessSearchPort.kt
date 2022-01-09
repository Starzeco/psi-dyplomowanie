package com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence

import com.example.dyplomowaniebackend.domain.model.GraduationProcess
import com.example.dyplomowaniebackend.domain.model.exception.EntityNotFoundException

interface GraduationProcessSearchPort {
    @Throws(EntityNotFoundException::class)
    fun getGraduationProcessById(graduationProcessId: Long): GraduationProcess
}