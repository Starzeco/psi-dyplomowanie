package com.example.dyplomowaniebackend.domain.model.exception

class SubjectConstraintViolationException(override val message: String) : RuntimeException(message)

class CandidatureConstraintViolationException(override val message: String) : RuntimeException(message)
class CandidatureAcceptanceConstraintViolationException(override val message: String) : RuntimeException(message)

class PropositionAcceptanceConstraintViolationException(override val message: String) : RuntimeException(message)
