package com.example.dyplomowaniebackend.api.controller

import com.example.dyplomowaniebackend.api.dto.CandidaturePartialInfoResponse
import com.example.dyplomowaniebackend.domain.candidature.port.api.CandidatureServicePort
import com.example.dyplomowaniebackend.domain.graduationProcess.port.api.StudentSearchPort
import com.example.dyplomowaniebackend.domain.model.CandidatureStatus
import com.example.dyplomowaniebackend.domain.model.CandidatureType
import com.example.dyplomowaniebackend.domain.model.Student
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/student")
class StudentController(
    private val candidatureServicePort: CandidatureServicePort,
    private val studentSearchPort: StudentSearchPort,
) {

    @GetMapping
    fun getStudentsByIndexes(@RequestParam(name = "indexes") indexes: String): Set<Student> =
        studentSearchPort.getStudentsByIndexes(indexes.split(","))

    //TODO: studentId could be passed from auth context - it can be stored within TOKEN
    @GetMapping("{student_id}/graduation_process/{graduation_process_id}/candidature")
    fun getAllCandidatures(
        @PathVariable(name = "student_id") studentId: Long,
        @PathVariable(name = "graduation_process_id") graduationProcessId: Long,
        @RequestParam(name = "phrase", required = false) phrase: String?,
        @RequestParam(name = "type", required = false) type: CandidatureType?,
        @RequestParam(name = "status", required = false) status: CandidatureStatus?
    ): Set<CandidaturePartialInfoResponse> = candidatureServicePort.getAllCandidatureAsStudent(
        studentId = studentId,
        graduationProcessId = graduationProcessId,
        phrase = phrase,
        type = type,
        status = status
    ).map { CandidaturePartialInfoResponse.fromDomain(it.first, it.second) }
        .toSet()

}
