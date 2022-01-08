package com.example.dyplomowaniebackend.api.controller

import com.example.dyplomowaniebackend.domain.graduationProcess.port.api.SubjectCreationPort
import com.example.dyplomowaniebackend.domain.model.Subject
import com.example.dyplomowaniebackend.domain.model.SubjectCreation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.prefix}/subject")
class SubjectController(val subjectCreationPort: SubjectCreationPort) {

    @PostMapping
    fun createSubject(@RequestBody subjectCreation: SubjectCreation): Subject {
        return subjectCreationPort.createSubject(subjectCreation)
    }
}