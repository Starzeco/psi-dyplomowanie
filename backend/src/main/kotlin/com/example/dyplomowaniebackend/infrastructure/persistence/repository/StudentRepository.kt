package com.example.dyplomowaniebackend.infrastructure.persistence.repository

import com.example.dyplomowaniebackend.infrastructure.persistence.entity.StudentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : JpaRepository<StudentEntity, Long> {
}