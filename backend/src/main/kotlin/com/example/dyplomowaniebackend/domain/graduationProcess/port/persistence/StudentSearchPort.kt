package com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence

import com.example.dyplomowaniebackend.domain.model.Student

interface StudentSearchPort {
    fun findById(studentId: Long): Student?
    fun getById(studentId: Long): Student
    fun findAllByStudentIdInAndSubjectIdNotNull(studentIds: Set<Long>): Set<Student>
    fun existsByStudentIdAndSubjectIdNotNull(studentId: Long): Boolean
    fun existsAllBySubjectId(subjectId: Long) : Boolean
}
