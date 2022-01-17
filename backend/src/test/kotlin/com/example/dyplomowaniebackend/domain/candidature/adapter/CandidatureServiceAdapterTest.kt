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
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
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

    private val verifiedSubject = Subject(
        subjectId = 1,
        topic = "Przyklad temat",
        topicInEnglish = "Przyklad temat english",
        objective = "Cel pracy",
        objectiveInEnglish = "Cel pracy english",
        realizationLanguage = RealizationLanguage.POLISH,
        realiseresNumber = 1,
        supervisor = supervisor,
        graduationProcess = graduationProcess,
        status = SubjectStatus.VERIFIED,
        creationDate = now
    )

    private val draftSubject = Subject(
        subjectId = 2,
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
        subject = verifiedSubject
    )

    private val verifiedSubjectWithInitiator = Subject(
        subjectId = 1,
        topic = "Przyklad temat",
        topicInEnglish = "Przyklad temat english",
        objective = "Cel pracy",
        objectiveInEnglish = "Cel pracy english",
        realizationLanguage = RealizationLanguage.POLISH,
        realiseresNumber = 1,
        initiator = studentWithSubject,
        supervisor = supervisor,
        graduationProcess = graduationProcess,
        status = SubjectStatus.VERIFIED,
        creationDate = now
    )

    private val candidatureToVerifiedSubject = Candidature(
        candidatureId = 1,
        accepted = null,
        student = candidatureOwner,
        subject = verifiedSubject,
        creationDate = now
    )

    private val candidatureToNotVerifiedSubject = Candidature(
        candidatureId = 1,
        accepted = null,
        student = candidatureOwner,
        subject = draftSubject,
        creationDate = now
    )

    private val candidatureToVerifiedSubjectWithInitiator = Candidature(
        candidatureId = 1,
        accepted = null,
        student = candidatureOwner,
        subject = verifiedSubjectWithInitiator,
        creationDate = now
    )


    @Test
    fun `should create simple candidature without coauthors`() {
        // given
        val candidatureCreation = CandidatureCreation(
            studentId = candidatureOwner.studentId!!,
            subjectId = verifiedSubject.subjectId!!,
            coauthors = setOf()
        )

        val candidatureId = 1L
        val candidature = Candidature(
            student = candidatureOwner,
            subject = verifiedSubject,
            creationDate = now
        )

        val expectedResult = candidature.copy(candidatureId = 1L)

        every { clock.instant() } returns now
        every { subjectSearchPort.getById(any(), any()) } returns verifiedSubject
        every { studentSearchPort.existsAllBySubjectId(any()) } returns false
        every { studentSearchPort.findAllByStudentIdInAndSubjectIdNotNull(any()) } returns setOf()
        every { studentSearchPort.getById(any()) } returns candidatureOwner
        every { subjectSearchPort.getById(any(), false) } returns verifiedSubject
        every { candidatureMutationPort.insert(any()) } answers { i ->
            (i.invocation.args[0] as Candidature).copy(candidatureId = candidatureId)
        }
        every { candidatureMutationPort.insertAcceptances(any()) } returns setOf()

        // when
        val result = candidatureServiceAdapter.createCandidature(candidatureCreation)

        // then
        verifySequence {
            subjectSearchPort.getById(verifiedSubject.subjectId!!, false)
            studentSearchPort.existsAllBySubjectId(verifiedSubject.subjectId!!)
            studentSearchPort.findAllByStudentIdInAndSubjectIdNotNull(setOf(candidatureOwner.studentId!!))
            studentSearchPort.getById(candidatureOwner.studentId!!)
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
            subjectId = verifiedSubject.subjectId!!,
            coauthors = setOf(studentWithoutSubject.studentId!!)
        )

        val candidatureId = 1L
        val candidature = Candidature(
            student = candidatureOwner,
            subject = verifiedSubject,
            creationDate = now
        )

        val expectedResult = candidature.copy(candidatureId = 1L)

        val candidatureAcceptance = CandidatureAcceptance(
            student = studentWithoutSubject,
            candidature = expectedResult
        )

        every { clock.instant() } returns now
        every { subjectSearchPort.getById(any(), any()) } returns verifiedSubject
        every { studentSearchPort.existsAllBySubjectId(any()) } returns false
        every { studentSearchPort.findAllByStudentIdInAndSubjectIdNotNull(any()) } returns setOf()
        every { studentSearchPort.getById(any()) } returns candidatureOwner andThen studentWithoutSubject
        every { subjectSearchPort.getById(any(), false) } returns verifiedSubject
        every { candidatureMutationPort.insert(any()) } answers { i ->
            (i.invocation.args[0] as Candidature).copy(candidatureId = candidatureId)
        }
        every { candidatureMutationPort.insertAcceptances(any()) } returns setOf()

        // when
        val result = candidatureServiceAdapter.createCandidature(candidatureCreation)

        // then
        verifySequence {
            subjectSearchPort.getById(verifiedSubject.subjectId!!, false)
            studentSearchPort.existsAllBySubjectId(verifiedSubject.subjectId!!)
            studentSearchPort.findAllByStudentIdInAndSubjectIdNotNull(
                setOf(
                    studentWithoutSubject.studentId!!,
                    candidatureOwner.studentId!!
                )
            )
            studentSearchPort.getById(candidatureOwner.studentId!!)
            clock.instant()
            candidatureMutationPort.insert(candidature)
            studentSearchPort.getById(studentWithoutSubject.studentId!!)
            candidatureMutationPort.insertAcceptances(setOf(candidatureAcceptance))
        }

        assertEquals(expectedResult, result)
    }

    @Test
    fun `should try to create simple candidature to not verified subject and throw error`() {
        // given
        val candidatureCreation = CandidatureCreation(
            studentId = candidatureOwner.studentId!!,
            subjectId = draftSubject.subjectId!!,
            coauthors = setOf(studentWithoutSubject.studentId!!)
        )
        val expectedExceptionMessage =
            "Could not create a candidature because a subject ${draftSubject.subjectId} is not ${SubjectStatus.VERIFIED}"

        every { subjectSearchPort.getById(any(), any()) } returns draftSubject

        // when
        val result = assertThrows<CandidatureConstraintViolationException> {
            candidatureServiceAdapter.createCandidature(candidatureCreation)
        }

        // then
        verify(exactly = 1) { subjectSearchPort.getById(draftSubject.subjectId!!, false) }
        verify(exactly = 0) { studentSearchPort.existsAllBySubjectId(any()) }
        verify(exactly = 0) { studentSearchPort.findAllByStudentIdInAndSubjectIdNotNull(any()) }
        verify(exactly = 0) { studentSearchPort.getById(any()) }
        verify(exactly = 0) { candidatureMutationPort.insert(any()) }
        verify(exactly = 0) { candidatureMutationPort.insertAcceptances(any()) }

        assertEquals(expectedExceptionMessage, result.message)
    }

    @Test
    fun `should try to create simple candidature to verified subject and throw error because subject is assigned to students`() {
        // given
        val candidatureCreation = CandidatureCreation(
            studentId = candidatureOwner.studentId!!,
            subjectId = verifiedSubject.subjectId!!,
            coauthors = setOf(studentWithSubject.studentId!!)
        )

        val expectedExceptionMessage =
            "Could not create a candidature because a subject ${verifiedSubject.subjectId} has already assigned students"

        every { subjectSearchPort.getById(any(), any()) } returns verifiedSubject
        every { studentSearchPort.existsAllBySubjectId(any()) } returns true

        // when
        val result = assertThrows<CandidatureConstraintViolationException> {
            candidatureServiceAdapter.createCandidature(candidatureCreation)
        }

        // then
        verify(exactly = 1) { subjectSearchPort.getById(verifiedSubject.subjectId!!, false) }
        verify(exactly = 1) { studentSearchPort.existsAllBySubjectId(verifiedSubject.subjectId!!) }
        verify(exactly = 0) { studentSearchPort.findAllByStudentIdInAndSubjectIdNotNull(any()) }
        verify(exactly = 0) { studentSearchPort.getById(any()) }
        verify(exactly = 0) { candidatureMutationPort.insert(any()) }
        verify(exactly = 0) { candidatureMutationPort.insertAcceptances(any()) }

        assertEquals(expectedExceptionMessage, result.message)
    }


    @Test
    fun `should try to create simple candidature with coauthor that is attached to subject and throw error`() {
        // given
        val candidatureCreation = CandidatureCreation(
            studentId = candidatureOwner.studentId!!,
            subjectId = verifiedSubject.subjectId!!,
            coauthors = setOf(studentWithSubject.studentId!!)
        )
        val expectedExceptionMessage =
            "Can not create a candidature when one of its students [${studentWithSubject.studentId}] realize a subject ${verifiedSubject.subjectId}"

        every { subjectSearchPort.getById(any(), any()) } returns verifiedSubject
        every { studentSearchPort.existsAllBySubjectId(any()) } returns false
        every { studentSearchPort.findAllByStudentIdInAndSubjectIdNotNull(any()) } returns setOf(studentWithSubject)

        // when
        val result = assertThrows<CandidatureConstraintViolationException> {
            candidatureServiceAdapter.createCandidature(candidatureCreation)
        }

        // then
        verify(exactly = 1) { subjectSearchPort.getById(verifiedSubject.subjectId!!, false) }
        verify(exactly = 1) { studentSearchPort.existsAllBySubjectId(verifiedSubject.subjectId!!) }
        verify(exactly = 1) {
            studentSearchPort.findAllByStudentIdInAndSubjectIdNotNull(
                setOf(
                    studentWithSubject.studentId!!,
                    candidatureOwner.studentId!!
                )
            )
        }
        verify(exactly = 0) { studentSearchPort.getById(any()) }
        verify(exactly = 0) { candidatureMutationPort.insert(any()) }
        verify(exactly = 0) { candidatureMutationPort.insertAcceptances(any()) }

        assertEquals(expectedExceptionMessage, result.message)
    }

    @Test
    fun `should decide about candidature - accept`() {
        // given
        val accepted = true
        val candidatureId = candidatureToVerifiedSubject.candidatureId!!
        val subjectId = verifiedSubject.subjectId!!
        val studentId = candidatureOwner.studentId!!
        val subjectStatusUpdate = SubjectStatusUpdate(
            subjectId = subjectId,
            status = SubjectStatus.RESERVED
        )

        every { candidatureSearchPort.existsCandidatureAcceptancesByCandidatureIdAndAcceptedIsFalseOrAcceptedIsNull(any()) } returns false
        every { candidatureSearchPort.getCandidatureById(any()) } returns candidatureToVerifiedSubject
        every { candidatureMutationPort.updateAcceptedById(any(), any()) } returns 1L
        every { candidatureSearchPort.getCandidatureAcceptanceByCandidatureId(any()) } returns setOf()
        every { subjectMutationPort.updateStatus(any()) } returns subjectStatusUpdate
        every { candidatureMutationPort.updateAcceptedToFalseWithExclusiveIdBySubjectId(any(), any()) } returns 0
        every { studentMutationPort.updateSubjectIdByStudentIdIn(any(), any()) } returns setOf(studentId)

        // when
        val result = candidatureServiceAdapter.decideAboutCandidature(
            candidatureId,
            accepted
        )

        // then
        verifySequence {
            candidatureSearchPort.existsCandidatureAcceptancesByCandidatureIdAndAcceptedIsFalseOrAcceptedIsNull(
                candidatureId
            )
            candidatureSearchPort.getCandidatureById(candidatureId)
            candidatureMutationPort.updateAcceptedById(candidatureId, accepted)
            subjectMutationPort.updateStatus(subjectStatusUpdate)
            candidatureMutationPort.updateAcceptedToFalseWithExclusiveIdBySubjectId(subjectId, candidatureId)
            candidatureSearchPort.getCandidatureAcceptanceByCandidatureId(candidatureId)
            studentMutationPort.updateSubjectIdByStudentIdIn(setOf(studentId), subjectId)
        }

        assertEquals(candidatureId, result)
    }

    @Test
    fun `should decide about candidature - reject`() {
        // given
        val accepted = false
        val candidatureId = candidatureToVerifiedSubject.candidatureId!!

        every { candidatureSearchPort.existsCandidatureAcceptancesByCandidatureIdAndAcceptedIsFalseOrAcceptedIsNull(any()) } returns false
        every { candidatureSearchPort.getCandidatureById(any()) } returns candidatureToVerifiedSubject
        every { candidatureMutationPort.updateAcceptedById(any(), any()) } returns 1L

        // when
        val result = candidatureServiceAdapter.decideAboutCandidature(
            candidatureId,
            accepted
        )

        // then
        verifySequence {
            candidatureSearchPort.existsCandidatureAcceptancesByCandidatureIdAndAcceptedIsFalseOrAcceptedIsNull(
                candidatureId
            )
            candidatureSearchPort.getCandidatureById(candidatureId)
            candidatureMutationPort.updateAcceptedById(candidatureId, accepted)
        }

        assertEquals(candidatureId, result)
    }

    @Test
    fun `should try to decide about candidature but some not accepted acceptances exist`() {
        // given
        val accepted = true
        val candidatureId = candidatureToVerifiedSubject.candidatureId!!

        val expectedExceptionMessage =
            "Could not decide about a candidature with id $candidatureId because some not accepted candidature acceptances exist"

        every { candidatureSearchPort.existsCandidatureAcceptancesByCandidatureIdAndAcceptedIsFalseOrAcceptedIsNull(any()) } returns true

        // when
        val result = assertThrows<CandidatureConstraintViolationException> {
            candidatureServiceAdapter.decideAboutCandidature(
                candidatureId,
                accepted
            )
        }

        // then
        verifySequence {
            candidatureSearchPort.existsCandidatureAcceptancesByCandidatureIdAndAcceptedIsFalseOrAcceptedIsNull(
                candidatureId
            )
        }

        assertEquals(expectedExceptionMessage, result.message)
    }

    @Test
    fun `should try to decide about candidature but a subject is not verified`() {
        // given
        val accepted = true
        val candidatureId = candidatureToNotVerifiedSubject.candidatureId!!

        val expectedExceptionMessage =
            "Could not decide about a candidature with id $candidatureId because a subject is not ${SubjectStatus.VERIFIED}"

        every { candidatureSearchPort.existsCandidatureAcceptancesByCandidatureIdAndAcceptedIsFalseOrAcceptedIsNull(any()) } returns false
        every { candidatureSearchPort.getCandidatureById(any()) } returns candidatureToNotVerifiedSubject

        // when
        val result = assertThrows<CandidatureConstraintViolationException> {
            candidatureServiceAdapter.decideAboutCandidature(
                candidatureId,
                accepted
            )
        }

        // then
        verifySequence {
            candidatureSearchPort.existsCandidatureAcceptancesByCandidatureIdAndAcceptedIsFalseOrAcceptedIsNull(
                candidatureId
            )
            candidatureSearchPort.getCandidatureById(candidatureId)
        }

        assertEquals(expectedExceptionMessage, result.message)
    }

    @Test
    fun `should try to decide about candidature but a subject has an initiator`() {
        // given
        val accepted = true
        val candidature = candidatureToVerifiedSubjectWithInitiator
        val candidatureId = candidature.candidatureId!!

        val expectedExceptionMessage =
            "Could not decide about a candidature with id $candidatureId because a subject has an initiator"

        every { candidatureSearchPort.existsCandidatureAcceptancesByCandidatureIdAndAcceptedIsFalseOrAcceptedIsNull(any()) } returns false
        every { candidatureSearchPort.getCandidatureById(any()) } returns candidature

        // when
        val result = assertThrows<CandidatureConstraintViolationException> {
            candidatureServiceAdapter.decideAboutCandidature(
                candidatureId,
                accepted
            )
        }

        // then
        verifySequence {
            candidatureSearchPort.existsCandidatureAcceptancesByCandidatureIdAndAcceptedIsFalseOrAcceptedIsNull(
                candidatureId
            )
            candidatureSearchPort.getCandidatureById(candidatureId)
        }
        assertEquals(expectedExceptionMessage, result.message)
    }

    @Test
    fun `should decide about candidature acceptance`() {
        // given
        val accepted = true

        val candidature = Candidature(
            candidatureId = 1,
            student = candidatureOwner,
            subject = verifiedSubject,
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
        val result = candidatureServiceAdapter.decideAboutCandidatureAcceptance(
            candidatureAcceptance.candidatureAcceptanceId!!,
            accepted
        )

        // then
        verifySequence {
            candidatureSearchPort.getCandidatureAcceptanceById(candidatureAcceptance.candidatureAcceptanceId!!)
            studentSearchPort.existsByStudentIdAndSubjectIdNotNull(studentWithoutSubject.studentId!!)
            candidatureMutationPort.updateAcceptanceAcceptedById(
                candidatureAcceptance.candidatureAcceptanceId!!,
                accepted
            )
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
            subject = verifiedSubject,
            creationDate = now
        )

        val candidatureAcceptance = CandidatureAcceptance(
            candidatureAcceptanceId = 1,
            student = studentWithoutSubject,
            candidature = candidature
        )

        val expectedExceptionMessage =
            "Can not decide about candidature acceptance with id ${candidatureAcceptance.candidatureAcceptanceId} because it have not been updated"

        every { candidatureSearchPort.getCandidatureAcceptanceById(any()) } returns candidatureAcceptance
        every { studentSearchPort.existsByStudentIdAndSubjectIdNotNull(any()) } returns false
        every { candidatureMutationPort.updateAcceptanceAcceptedById(any(), any()) } returns 0L

        // when
        val result = assertThrows<CandidatureAcceptanceConstraintViolationException> {
            candidatureServiceAdapter.decideAboutCandidatureAcceptance(
                candidatureAcceptance.candidatureAcceptanceId!!,
                accepted
            )
        }

        // then
        verifySequence {
            candidatureSearchPort.getCandidatureAcceptanceById(candidatureAcceptance.candidatureAcceptanceId!!)
            studentSearchPort.existsByStudentIdAndSubjectIdNotNull(studentWithoutSubject.studentId!!)
            candidatureMutationPort.updateAcceptanceAcceptedById(
                candidatureAcceptance.candidatureAcceptanceId!!,
                accepted
            )
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
            subject = verifiedSubject,
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
            candidatureServiceAdapter.decideAboutCandidatureAcceptance(
                candidatureAcceptance.candidatureAcceptanceId!!,
                accepted
            )
        }

        // then
        verifySequence {
            candidatureSearchPort.getCandidatureAcceptanceById(candidatureAcceptance.candidatureAcceptanceId!!)
            studentSearchPort.existsByStudentIdAndSubjectIdNotNull(studentWithSubject.studentId!!)
        }

        assertEquals(expectedExceptionMessage, result.message)
    }


}
