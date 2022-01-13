package com.example.dyplomowaniebackend.infrastructure.persistence.repository

import com.example.dyplomowaniebackend.infrastructure.persistence.entity.StudentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : JpaRepository<StudentEntity, Long> {
    fun existsStudentEntityByStudentIdAndSubjectIdNotNull(studentId: Long): Boolean
    fun findStudentEntitiesByStudentIdIsInAndStudentIdIsNotNull(studentIds: Set<Long>): Set<StudentEntity>
}
