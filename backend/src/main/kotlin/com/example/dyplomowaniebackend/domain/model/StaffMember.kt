package com.example.dyplomowaniebackend.domain.model

data class StaffMember(
    val staffMemberId: Long? = null,
    val email: String,
    val name: String,
    val surname: String,
    val title: Title,
    val currentWorkload: Int,
    val absoluteWorkload: Int,
    val subjects: Set<Subject>,
    val faculty: Faculty,
    val verifiers: Set<Verifier>
)

enum class Title {
    PROFESSOR,
    DOCTOR,
    MASTER,
    BATCHELOR
}
