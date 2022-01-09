package com.example.dyplomowaniebackend.domain.model.exception

class GraduationProcessNotFoundException(override val message: String) : RuntimeException(message) {
}