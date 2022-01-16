package com.example.dyplomowaniebackend.api.advice

import com.example.dyplomowaniebackend.domain.model.exception.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


data class ErrorResponse(val message: String, val errors: List<String>)

@RestControllerAdvice
class RestControllerExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(
        EntityNotFoundException::class
    )
    fun handleNotFoundException(error: RuntimeException): ResponseEntity<ErrorResponse?>? {
        return ResponseEntity(ErrorResponse(error.message!!, listOf()), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(
        SubjectConstraintViolationException::class,
        SubjectStatusChangeException::class,
        CandidatureConstraintViolationException::class,
        CandidatureAcceptanceConstraintViolationException::class
    )
    fun handleConstraintViolationException(error: RuntimeException): ResponseEntity<ErrorResponse?>? {
        return ResponseEntity(ErrorResponse(error.message!!, listOf()), HttpStatus.BAD_REQUEST)
    }
}