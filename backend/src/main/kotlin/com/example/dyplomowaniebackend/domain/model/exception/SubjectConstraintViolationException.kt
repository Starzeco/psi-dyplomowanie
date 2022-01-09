package com.example.dyplomowaniebackend.domain.model.exception

class SubjectConstraintViolationException(override val message: String) : RuntimeException(message) {
}