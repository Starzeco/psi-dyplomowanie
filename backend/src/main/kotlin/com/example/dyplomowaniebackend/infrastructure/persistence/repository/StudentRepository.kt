package com.example.dyplomowaniebackend.infrastructure.persistence.repository

import com.example.dyplomowaniebackend.infrastructure.persistence.entity.StudentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : JpaRepository<StudentEntity, Long> {
    @Query(
        "SELECT SE FROM StudentEntity SE " +
                "WHERE SE.subjectId IS NOT NULL " +
                "AND SE.studentId IN :studentIds"
    )
    fun findStudentsWhoRealizesAnySubject(@Param("studentIds") studentIds: Set<Long>): Set<StudentEntity>
}
