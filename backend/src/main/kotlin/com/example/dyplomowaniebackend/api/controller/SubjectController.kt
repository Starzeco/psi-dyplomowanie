package com.example.dyplomowaniebackend.api.controller

import com.example.dyplomowaniebackend.domain.graduationProcess.port.api.SubjectCreationPort
import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.domain.model.SubjectCreation
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${api.prefix}/subject")
class SubjectController(val subjectCreationPort: SubjectCreationPort) {

    @PutMapping
    fun createSubject(@RequestBody subjectCreation: SubjectCreation): Subject {
        return subjectCreationPort.createSubject(subjectCreation)
    }
}