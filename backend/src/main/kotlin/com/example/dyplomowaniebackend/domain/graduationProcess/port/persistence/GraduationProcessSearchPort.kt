package com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence

import com.example.dyplomowaniebackend.domain.model.GraduationProcess

interface GraduationProcessSearchPort {
    fun getGraduationProcessById(graduationProcessId: Long?): GraduationProcess?
}