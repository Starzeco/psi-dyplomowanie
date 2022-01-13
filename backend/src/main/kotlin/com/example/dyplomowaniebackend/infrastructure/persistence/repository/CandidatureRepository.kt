package com.example.dyplomowaniebackend.infrastructure.persistence.repository

import com.example.dyplomowaniebackend.infrastructure.persistence.entity.CandidatureAcceptanceEntity
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.CandidatureEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface CandidatureRepository : JpaRepository<CandidatureEntity, Long>

@Repository
interface CandidatureAcceptanceRepository : JpaRepository<CandidatureAcceptanceEntity, Long> {

    @Query(
        "UPDATE CandidatureAcceptanceEntity CAE " +
                "SET CAE.accepted = :accepted " +
                "WHERE CAE.candidatureAcceptanceId = :candidatureAcceptanceId"
    )
    @Modifying
    @Transactional
    fun updateAcceptedById(
        @Param("candidatureAcceptanceId") candidatureAcceptanceId: Long,
        @Param("accepted") accepted: Boolean
    ): Int
}
