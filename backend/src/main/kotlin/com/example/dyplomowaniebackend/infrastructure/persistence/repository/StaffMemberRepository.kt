package com.example.dyplomowaniebackend.infrastructure.persistence.repository

import com.example.dyplomowaniebackend.infrastructure.persistence.entity.StaffMemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StaffMemberRepository : JpaRepository<StaffMemberEntity, Long> {
    fun findAllByFacultyId(facultyId: Long): Set<StaffMemberEntity>
}