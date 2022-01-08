package com.example.dyplomowaniebackend.domain.model

data class StaffMember(
    val staffMemberId: String,
    val email: String,
    val name: String,
    val surname: String,
    val title: Title,
    val currentWorkload: Int,
    val absoluteWorkload: Int,
//    val subjects: Set<Subject>,
    val faculty: Faculty,
//    val verifiers: Set<Verifier>
) {
    val fullName = "${title.shorthand} $name $surname"
}

enum class Title(val shorthand: String) {
    PROFESSOR("prof."),
    DOCTOR("dr"),
    MASTER("mgr"),
    BATCHELOR("inż")
}
