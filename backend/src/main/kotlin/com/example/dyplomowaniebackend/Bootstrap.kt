package com.example.dyplomowaniebackend

import com.example.dyplomowaniebackend.domain.model.Degree
import com.example.dyplomowaniebackend.domain.model.Title
import com.example.dyplomowaniebackend.infrastructure.persistence.entity.*
import com.example.dyplomowaniebackend.infrastructure.persistence.repository.*
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class Bootstrap(
    val studentRepository: StudentRepository,
    val graduationProcessRepository: GraduationProcessRepository,
    val degreeCourseRepository: DegreeCourseRepository,
    val staffMemberRepository: StaffMemberRepository,
    val facultyRepository: FacultyRepository,
) :
    ApplicationListener<ContextRefreshedEvent> {
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val student0 = StudentEntity(
            index = "242422",
            email = "242422@student.pwr.edu.pl",
            name = "Marcel",
            surname = "Krakowiak",
            subjectProposals = setOf(),
            propositionAcceptances = setOf(),
            candidatures = setOf(),
            candidatureAcceptances = setOf(),
            graduationProcesses = setOf(),
        )
        val student1 = StudentEntity(
            index = "242421",
            email = "242421@student.pwr.edu.pl",
            name = "Kacper",
            surname = "Kowalski",
            subjectProposals = setOf(),
            propositionAcceptances = setOf(),
            candidatures = setOf(),
            candidatureAcceptances = setOf(),
            graduationProcesses = setOf(),
        )
        val student2 = StudentEntity(
            index = "242420",
            email = "242420@student.pwr.edu.pl",
            name = "Stasiek",
            surname = "Kolorowy",
            subjectProposals = setOf(),
            propositionAcceptances = setOf(),
            candidatures = setOf(),
            candidatureAcceptances = setOf(),
            graduationProcesses = setOf(),
        )
        studentRepository.saveAll(setOf(student0, student1, student2))

        val faculty = FacultyEntity(
            name = "Wydział infy i zarządznia",
            shortName = "WIZ",
            staffMembers = setOf(),
            degreeCourses = setOf()
        )

        val facultySaved = facultyRepository.save(faculty)

        val supervisor = StaffMemberEntity(
            email = "super.prowadzacy@pwr.edu.pl",
            name = "Super",
            surname = "Prowadzacy",
            title = Title.DOCTOR,
            currentWorkload = 24,
            absoluteWorkload = 180,
            subjects = setOf(),
            facultyId = facultySaved.facultyId!!,
            verifiers = setOf()
        )
        staffMemberRepository.save(supervisor)

        val degreeCourse = DegreeCourseEntity(
            name = "Infa",
            facultyId = facultySaved.facultyId,
            graduationProcesses = setOf(),
        )

        val savedDegreeCourse = degreeCourseRepository.save(degreeCourse)

        val graduationProcess = GraduationProcessEntity(
            cSDeadline = Instant.now(),
            vFDeadline = Instant.now(),
            cADeadline = Instant.now(),
            sPDeadline = Instant.now(),
            initialSemester = "Jakiś semssetrs 5",
            finalSemester = "Jakis semestr 6",
            degree = Degree.BATCHELOR,
            hCPerSubject = 10,
            students = setOf(),
            subjects = setOf(),
            degreeCourseId = savedDegreeCourse.degreeCourseId!!,
            verifiers = setOf()
        )

        graduationProcessRepository.save(graduationProcess)
    }
}