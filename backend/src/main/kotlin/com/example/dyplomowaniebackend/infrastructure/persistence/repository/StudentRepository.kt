package com.example.dyplomowaniebackend.infrastructure.persistence.repository

import com.example.dyplomowaniebackend.infrastructure.persistence.entity.StudentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface StudentRepository : JpaRepository<StudentEntity, Long> {

    @Query(
        "UPDATE StudentEntity S " +
                "SET S.subjectId = :subjectId " +
                "WHERE S.studentId IN :studentIds"
    )
    @Modifying
    @Transactional
    fun updateSubjectIdByStudentIdIn(@Param("studentIds") studentIds: Set<Long>, @Param("subjectId") subjectId: Long)
}