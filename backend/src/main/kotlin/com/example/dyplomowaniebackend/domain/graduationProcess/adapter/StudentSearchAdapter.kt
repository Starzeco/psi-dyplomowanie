package com.example.dyplomowaniebackend.domain.graduationProcess.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.StudentSearchPort
import com.example.dyplomowaniebackend.domain.model.Student
import org.springframework.stereotype.Service

@Service("studentSearchAdapterApi")
class StudentSearchAdapter(private val studentSearchPort: StudentSearchPort) : com.example.dyplomowaniebackend.domain.graduationProcess.port.api.StudentSearchPort {
    override fun getStudentsByIndexes(indexes: List<String>): Set<Student> =
        studentSearchPort.getAllStudentsIndexIn(indexes)
}