package com.example.dyplomowaniebackend.domain.model.exception

class StaffMemberNotFoundException(override val message: String) : RuntimeException(message) {
}