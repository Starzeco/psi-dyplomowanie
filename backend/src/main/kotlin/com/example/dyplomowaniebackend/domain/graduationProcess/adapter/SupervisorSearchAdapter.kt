package com.example.dyplomowaniebackend.domain.graduationProcess.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.api.SupervisorSearchPort
import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.StaffSearchPort
import com.example.dyplomowaniebackend.domain.model.StaffMember
import org.springframework.stereotype.Service

@Service
class SupervisorSearchAdapter(private val staffSearchPort: StaffSearchPort) : SupervisorSearchPort {
    override fun getAllSupervisors(graduationProcessId: Long): Set<StaffMember> =
        staffSearchPort.getAllSupervisorsInGraduationProcess(graduationProcessId)
}