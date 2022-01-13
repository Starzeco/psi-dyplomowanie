package com.example.dyplomowaniebackend.infrastructure.persistence.repository

import com.example.dyplomowaniebackend.infrastructure.persistence.entity.CandidatureAcceptanceEntity
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.CandidatureEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CandidatureRepository : JpaRepository<CandidatureEntity, Long>

@Repository
interface CandidatureAcceptanceRepository : JpaRepository<CandidatureAcceptanceEntity, Long>
