package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.StaffSearchPort
import com.example.dyplomowaniebackend.domain.model.StaffMember
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapCreation
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.StaffMemberRepository
import org.springframework.stereotype.Service

@Service
class StaffSearchAdapter(val staffMemberRepository: StaffMemberRepository) : StaffSearchPort {
    override fun getStudentById(staffMemberId: Long?): StaffMember? {
        if(staffMemberId == null) return null
        return staffMemberRepository.findById(staffMemberId).map { staff -> staff.mapCreation() }.get()
    }
}