package com.example.dyplomowaniebackend.infrastructure.persistence.mapper

import com.example.dyplomowaniebackend.domain.model.StaffMember
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.StaffMemberEntity


fun StaffMemberEntity.mapToDomain(): StaffMember =
    StaffMember(
        staffMemberId = this.staffMemberId,
        email = this.email,
        name = this.name,
        surname = this.surname,
        title = this.title,
        currentWorkload = this.currentWorkload,
        absoluteWorkload = this.absoluteWorkload,
        faculty = this.faculty!!.mapToDomain()
    )

fun StaffMember.mapToEntity(): StaffMemberEntity =
    StaffMemberEntity(
        staffMemberId = this.staffMemberId,
        email = this.email,
        name = this.name,
        surname = this.surname,
        title = this.title,
        currentWorkload = this.currentWorkload,
        absoluteWorkload = this.absoluteWorkload,
        facultyId = this.faculty.facultyId!!
    )
