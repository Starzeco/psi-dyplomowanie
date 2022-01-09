package com.example.dyplomowaniebackend.domain.model.exception

class StudentNotFoundException(override val message: String) : RuntimeException(message) {
}