package com.example.dyplomowaniebackend.infrastructure.persistence.repository

import com.example.dyplomowaniebackend.infrastructure.persistence.entity.PropositionAcceptanceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PropositionAcceptancesRepository : JpaRepository<PropositionAcceptanceEntity, Long> {

    fun findByStudentStudentId(studentId: Long): Set<PropositionAcceptanceEntity>

    @Modifying
    @Query(
        "UPDATE PropositionAcceptanceEntity PAE " +
                "SET PAE.accepted = :accepted " +
                "WHERE PAE.propositionAcceptanceId = :propositionAcceptanceId"
    )
    fun updateAcceptedById(
        @Param("propositionAcceptanceId") propositionAcceptanceId: Long,
        @Param("accepted") accepted: Boolean
    ): Long
}
