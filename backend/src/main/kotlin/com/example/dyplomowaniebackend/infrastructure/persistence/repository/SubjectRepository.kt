package com.example.dyplomowaniebackend.infrastructure.persistence.repository

import com.example.dyplomowaniebackend.infrastructure.persistence.entity.SubjectEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubjectRepository : JpaRepository<SubjectEntity, Long> {
}