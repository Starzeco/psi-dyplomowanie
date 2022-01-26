package com.example.dyplomowaniebackend.infrastructure.persistence.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.StaffSearchPort
import com.example.dyplomowaniebackend.domain.model.StaffMember
import com.example.dyplomowaniebackend.domain.model.exception.EntityNotFoundException
import com.example.dyplomowaniebackend.infrastructure.persistence.mapper.mapToDomain
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.GraduationProcessRepository
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.StaffMemberRepository
import org.springframework.stereotype.Service

@Service
class StaffSearchAdapter(val staffMemberRepository: StaffMemberRepository,
                         val graduationProcessRepository: GraduationProcessRepository) : StaffSearchPort {
    override fun getStaffMemberById(staffMemberId: Long): StaffMember =
        staffMemberRepository.findById(staffMemberId)
            .map { staff -> staff.mapToDomain() }
            .orElseThrow{ throw EntityNotFoundException(StaffMember::class, staffMemberId) }

    override fun getAllSupervisorsInGraduationProcess(graduationProcessId: Long): Set<StaffMember> {
        val graduationProcess = graduationProcessRepository.findById(graduationProcessId).get()
        return staffMemberRepository.findAllByFacultyId(graduationProcess.degreeCourse!!.faculty!!.facultyId!!)
            .map { it.mapToDomain() }
            .toSet()
    }
}