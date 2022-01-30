package com.example.dyplomowaniebackend.domain.graduationProcess.port.api

import com.example.dyplomowaniebackend.domain.model.StaffMember

interface SupervisorSearchPort {
    fun getAllSupervisors(graduationProcessId: Long): Set<StaffMember>
}