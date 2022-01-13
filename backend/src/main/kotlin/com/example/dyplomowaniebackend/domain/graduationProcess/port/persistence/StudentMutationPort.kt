package com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence

interface StudentMutationPort {
    fun updateSubjectIdByStudentIdIn(studentIds: Set<Long>, subjectId: Long): Set<Long>
}