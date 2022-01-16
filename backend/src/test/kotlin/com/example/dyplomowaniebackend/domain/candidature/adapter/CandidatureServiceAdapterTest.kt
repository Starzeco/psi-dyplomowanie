package com.example.dyplomowaniebackend.domain.candidature.adapter

import com.example.dyplomowaniebackend.domain.candidature.port.persistance.CandidatureMutationPort
import com.example.dyplomowaniebackend.domain.candidature.port.persistance.CandidatureSearchPort
import com.example.dyplomowaniebackend.domain.candidature.port.persistance.SubjectSearchPort
import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.StudentMutationPort
import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.StudentSearchPort
import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.SubjectMutationPort
import com.example.dyplomowaniebackend.domain.model.*
import com.example.dyplomowaniebackend.domain.model.exception.CandidatureAcceptanceConstraintViolationException
import com.example.dyplomowaniebackend.domain.model.exception.CandidatureConstraintViolationException
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Clock
import java.time.Instant


internal class CandidatureServiceAdapterTest {

    private val studentSearchPort: StudentSearchPort = mockk()
    private val studentMutationPort: StudentMutationPort = mockk()
    private val subjectSearchPort: SubjectSearchPort = mockk()
    private val subjectMutationPort: SubjectMutationPort = mockk()
    private val candidatureSearchPort: CandidatureSearchPort = mockk()
    private val candidatureMutationPort: CandidatureMutationPort = mockk()
    private val clock: Clock = mockk()

    private val candidatureServiceAdapter = CandidatureServiceAdapter(
        studentSearchPort,
        studentMutationPort,
        subjectSearchPort,
        subjectMutationPort,
        candidatureSearchPort,
        candidatureMutationPort,
        clock
    )

    private val now = Instant.now()

    private val faculty = Faculty(
        facultyId = 1,
        name = "Wydział Informatyki i Zarządznia",
        shortName = "WIZ"
    )

    private val degreeCourse = DegreeCourse(
        name = "Informatyka Stosowana",
        faculty = faculty,
    )

    private val graduationProcess: GraduationProcess = GraduationProcess(
        cSDeadline = now,
        vFDeadline = now,
        cADeadline = now,
        sPDeadline = now,
        initialSemester = "2021 ZIMA",
        finalSemester = "2021 LATO",
        degree = Degree.BATCHELOR,
        hCPerSubject = 10,
        students = setOf(),
        degreeCourse = degreeCourse,
    )

    private val supervisor = StaffMember(
        staffMemberId = 1,
        email = "super.prowadzacy@pwr.edu.pl",
        name = "Super",
        surname = "Prowadzacy",
        title = Title.DOCTOR,
        currentWorkload = 24,
        absoluteWorkload = 180,
        faculty = faculty
    )

    private val subject = Subject(
        subjectId = 1,
        topic = "Przyklad temat",
        topicInEnglish = "Przyklad temat english",
        objective = "Cel pracy",
        objectiveInEnglish = "Cel pracy english",
        realizationLanguage = RealizationLanguage.POLISH,
        realiseresNumber = 1,
        supervisor = supervisor,
        graduationProcess = graduationProcess,
        status = SubjectStatus.DRAFT,
        creationDate = now
    )

    private val candidatureOwner: Student = Student(
        studentId = 1,
        index = "242421",
        email = "242421@student.pwr.edu.pl",
        name = "Kacper",
        surname = "Kowalski",
        graduationProcesses = setOf(),
    )

    private val studentWithoutSubject: Student = Student(
        studentId = 1,
        index = "242421",
        email = "242421@student.pwr.edu.pl",
        name = "Kacper",
        surname = "Kowalski",
        graduationProcesses = setOf(),
    )

    private val studentWithSubject: Student = Student(
        studentId = 1,
        index = "242421",
        email = "242421@student.pwr.edu.pl",
        name = "Kacper",
        surname = "Kowalski",
        graduationProcesses = setOf(),
        subject = subject
    )


    @Test
    fun `should create simple candidature without coauthors`() {
        // given
        val candidatureCreation = CandidatureCreation(
            studentId = candidatureOwner.studentId!!,
            subjectId = subject.subjectId!!,
            coauthors = setOf()
        )

        val candidatureId = 1L
        val candidature = Candidature(
            student = candidatureOwner,
            subject = subject,
            creationDate = now
        )

        val expectedResult = candidature.copy(candidatureId = 1L)

        every { clock.instant() } returns now
        every { studentSearchPort.findAllByStudentIdInAndSubjectIdNotNull(any()) } returns setOf()
        every { studentSearchPort.getById(any()) } returns candidatureOwner
        every { subjectSearchPort.getById(any()) } returns subject
        every { candidatureMutationPort.insert(any()) } answers { i ->
             (i.invocation.args[0] as Candidature).copy(candidatureId = candidatureId)
        }
        every { candidatureMutationPort.insertAcceptances(any()) } returns setOf()

        // when
        val result = candidatureServiceAdapter.createCandidature(candidatureCreation)

        // then
        verifySequence {
            studentSearchPort.findAllByStudentIdInAndSubjectIdNotNull(setOf(candidatureOwner.studentId!!))
            studentSearchPort.getById(candidatureOwner.studentId!!)
            subjectSearchPort.getById(subject.subjectId!!)
            clock.instant()
            candidatureMutationPort.insert(candidature)
            candidatureMutationPort.insertAcceptances(setOf())
        }

        assertEquals(expectedResult, result)
    }

    @Test
    fun `should create simple candidature with a coauthor`() {
        // given
        val candidatureCreation = CandidatureCreation(
            studentId = candidatureOwner.studentId!!,
            subjectId = subject.subjectId!!,
            coauthors = setOf(studentWithoutSubject.studentId!!)
        )

        val candidatureId = 1L
        val candidature = Candidature(
            student = candidatureOwner,
            subject = subject,
            creationDate = now
        )

        val expectedResult = candidature.copy(candidatureId = 1L)

        val candidatureAcceptance = CandidatureAcceptance(
            student = studentWithoutSubject,
            candidature = expectedResult
        )

        every { clock.instant()} returns now
        every { studentSearchPort.findAllByStudentIdInAndSubjectIdNotNull(any()) } returns setOf()
        every { studentSearchPort.getById(any()) } returns candidatureOwner andThen studentWithoutSubject
        every { subjectSearchPort.getById(any()) } returns subject
        every { candidatureMutationPort.insert(any()) } answers { i ->
            (i.invocation.args[0] as Candidature).copy(candidatureId = candidatureId)
        }
        every { candidatureMutationPort.insertAcceptances(any()) } returns setOf()

        // when
        val result = candidatureServiceAdapter.createCandidature(candidatureCreation)

        // then
        verifySequence {
            studentSearchPort.findAllByStudentIdInAndSubjectIdNotNull(setOf(studentWithoutSubject.studentId!!, candidatureOwner.studentId!!))
            studentSearchPort.getById(candidatureOwner.studentId!!)
            subjectSearchPort.getById(subject.subjectId!!)
            clock.instant()
            candidatureMutationPort.insert(candidature)
            studentSearchPort.getById(studentWithoutSubject.studentId!!)
            candidatureMutationPort.insertAcceptances(setOf(candidatureAcceptance))
        }

        assertEquals(expectedResult, result)
    }

    @Test
    fun `should try to create simple candidature with coauthor that is attached to subject and throw error`() {
        // given
        val candidatureCreation = CandidatureCreation(
            studentId = candidatureOwner.studentId!!,
            subjectId = subject.subjectId!!,
            coauthors = setOf(studentWithSubject.studentId!!)
        )
        val expectedExceptionMessage =
            "Can not create a candidature when one of its students realize a subject: [${studentWithSubject.studentId}]"

        every { studentSearchPort.findAllByStudentIdInAndSubjectIdNotNull(any()) } returns setOf(studentWithSubject)

        // when
        val result = assertThrows<CandidatureConstraintViolationException> {
            candidatureServiceAdapter.createCandidature(candidatureCreation)
        }

        // then
        verify(exactly = 1) { studentSearchPort.findAllByStudentIdInAndSubjectIdNotNull(setOf(studentWithSubject.studentId!!, candidatureOwner.studentId!!)) }
        verify(exactly = 0) { studentSearchPort.getById(any()) }
        verify(exactly = 0) { subjectSearchPort.getById(any()) }
        verify(exactly = 0) { candidatureMutationPort.insert(any()) }
        verify(exactly = 0) { candidatureMutationPort.insertAcceptances(any()) }

        assertEquals(expectedExceptionMessage, result.message)
    }

    @Test
    fun `should decide about candidature acceptance`() {
        // given
        val accepted = true

        val candidature = Candidature(
            candidatureId = 1,
            student = candidatureOwner,
            subject = subject,
            creationDate = now
        )

        val candidatureAcceptance = CandidatureAcceptance(
            candidatureAcceptanceId = 1,
            student = studentWithoutSubject,
            candidature = candidature
        )

        every { candidatureSearchPort.getCandidatureAcceptanceById(any()) } returns candidatureAcceptance
        every { studentSearchPort.existsByStudentIdAndSubjectIdNotNull(any()) } returns false
        every { candidatureMutationPort.updateAcceptanceAcceptedById(any(), any()) } returns 1L

        // when
        val result = candidatureServiceAdapter.decideAboutCandidatureAcceptance(candidatureAcceptance.candidatureAcceptanceId!!, accepted)

        // then
        verifySequence {
            candidatureSearchPort.getCandidatureAcceptanceById(candidatureAcceptance.candidatureAcceptanceId!!)
            studentSearchPort.existsByStudentIdAndSubjectIdNotNull(studentWithoutSubject.studentId!!)
            candidatureMutationPort.updateAcceptanceAcceptedById(candidatureAcceptance.candidatureAcceptanceId!!, accepted)
        }

        assertEquals(candidatureAcceptance.candidatureAcceptanceId!!, result)
    }

    @Test
    fun `should try to decide about candidature acceptance when it does not exists and throw error`() {
        // given
        val accepted = true

        val candidature = Candidature(
            candidatureId = 1,
            student = candidatureOwner,
            subject = subject,
            creationDate = now
        )

        val candidatureAcceptance = CandidatureAcceptance(
            candidatureAcceptanceId = 1,
            student = studentWithoutSubject,
            candidature = candidature
        )

        val expectedExceptionMessage = "Can not decide about candidature acceptance with id ${candidatureAcceptance.candidatureAcceptanceId} because it have not been updated"

        every { candidatureSearchPort.getCandidatureAcceptanceById(any()) } returns candidatureAcceptance
        every { studentSearchPort.existsByStudentIdAndSubjectIdNotNull(any()) } returns false
        every { candidatureMutationPort.updateAcceptanceAcceptedById(any(), any()) } returns 0L

        // when
        val result = assertThrows<CandidatureAcceptanceConstraintViolationException> {
            candidatureServiceAdapter.decideAboutCandidatureAcceptance(candidatureAcceptance.candidatureAcceptanceId!!, accepted)
        }

        // then
        verifySequence {
            candidatureSearchPort.getCandidatureAcceptanceById(candidatureAcceptance.candidatureAcceptanceId!!)
            studentSearchPort.existsByStudentIdAndSubjectIdNotNull(studentWithoutSubject.studentId!!)
            candidatureMutationPort.updateAcceptanceAcceptedById(candidatureAcceptance.candidatureAcceptanceId!!, accepted)
        }

        assertEquals(expectedExceptionMessage, result.message)
    }

    @Test
    fun `should try to decide about candidature acceptance when a subject is assigned to the student and throw error`() {
        // given
        val accepted = true

        val candidature = Candidature(
            candidatureId = 1,
            student = candidatureOwner,
            subject = subject,
            creationDate = now
        )

        val candidatureAcceptance = CandidatureAcceptance(
            candidatureAcceptanceId = 1,
            student = studentWithSubject,
            candidature = candidature
        )

        val expectedExceptionMessage =
            "Can not decide about candidature acceptance with id ${candidatureAcceptance.candidatureAcceptanceId} because a student ${studentWithSubject.studentId} realizes a subject"

        every { candidatureSearchPort.getCandidatureAcceptanceById(candidatureAcceptance.candidatureAcceptanceId!!) } returns candidatureAcceptance
        every { studentSearchPort.existsByStudentIdAndSubjectIdNotNull(studentWithSubject.studentId!!) } returns true

        // when
        val result = assertThrows<CandidatureAcceptanceConstraintViolationException> {
            candidatureServiceAdapter.decideAboutCandidatureAcceptance(candidatureAcceptance.candidatureAcceptanceId!!, accepted)
        }

        // then
        verifySequence {
            candidatureSearchPort.getCandidatureAcceptanceById(candidatureAcceptance.candidatureAcceptanceId!!)
            studentSearchPort.existsByStudentIdAndSubjectIdNotNull(studentWithSubject.studentId!!)
        }

        assertEquals(expectedExceptionMessage, result.message)
    }


}
