package com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence

import com.example.dyplomowaniebackend.domain.model.StaffMember
import com.example.dyplomowaniebackend.domain.model.exception.EntityNotFoundException

interface StaffSearchPort {
    @Throws(EntityNotFoundException::class)
    fun getStudentById(staffMemberId: Long): StaffMember
}