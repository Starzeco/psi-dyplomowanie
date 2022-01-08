package com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence

import com.example.dyplomowaniebackend.domain.model.StaffMember

interface StaffSearchPort {
    fun getStudentById(staffMemberId: Long?): StaffMember?
}