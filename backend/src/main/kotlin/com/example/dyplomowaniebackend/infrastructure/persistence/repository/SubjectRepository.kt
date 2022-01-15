package com.example.dyplomowaniebackend.infrastructure.persistence.repository

import com.example.dyplomowaniebackend.domain.model.SubjectStatus
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.SubjectEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface SubjectRepository : JpaRepository<SubjectEntity, Long> {

    @Query(
        "UPDATE SubjectEntity SUB " +
                "SET SUB.status = :status " +
                "WHERE SUB.subjectId = :subjectId"
    )
    @Modifying
    @Transactional
    fun updateStatusById(
        @Param("subjectId") subjectId: Long,
        @Param("status") status: SubjectStatus
    ): Int

    @Query(
        "UPDATE SubjectEntity SUB " +
                "SET SUB.topic = :topic, " +
                "SUB.topicInEnglish = :topicInEnglish, " +
                "SUB.objective = :objective, " +
                "SUB.objectiveInEnglish = :objectiveInEnglish, " +
                "SUB.realiseresNumber = :realiseresNumber " +
                "WHERE SUB.subjectId = :subjectId"
    )
    @Modifying
    @Transactional
    fun updateSubject(
        @Param("topic") topic: String,
        @Param("topicInEnglish") topicInEnglish: String,
        @Param("objective") objective: String,
        @Param("objectiveInEnglish") objectiveInEnglish: String,
        @Param("realiseresNumber") realiseresNumber: Int
    ): Int
}