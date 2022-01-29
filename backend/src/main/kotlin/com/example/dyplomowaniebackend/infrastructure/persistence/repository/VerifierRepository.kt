package com.example.dyplomowaniebackend.infrastructure.persistence.repository

import com.example.dyplomowaniebackend.infrastructure.persistence.entity.VerifierEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VerifierRepository : JpaRepository<VerifierEntity, Long> {
    fun findAllByStaffMemberId(staffMemberId: Long): List<VerifierEntity>
    fun findByGraduationProcessId(graduationProcessId: Long): List<VerifierEntity>
}
