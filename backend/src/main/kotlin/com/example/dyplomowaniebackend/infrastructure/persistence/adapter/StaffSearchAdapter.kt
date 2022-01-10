package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.StaffSearchPort
import com.example.dyplomowaniebackend.domain.model.StaffMember
import com.example.dyplomowaniebackend.domain.model.exception.EntityNotFoundException
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.StaffMemberRepository
import org.springframework.stereotype.Service

@Service
class StaffSearchAdapter(val staffMemberRepository: StaffMemberRepository) : StaffSearchPort {
    override fun getStaffMemberById(staffMemberId: Long): StaffMember =
        staffMemberRepository.findById(staffMemberId)
            .map { staff -> staff.mapToDomain() }
            .orElseThrow{ throw EntityNotFoundException(StaffMember::class, staffMemberId) }
}