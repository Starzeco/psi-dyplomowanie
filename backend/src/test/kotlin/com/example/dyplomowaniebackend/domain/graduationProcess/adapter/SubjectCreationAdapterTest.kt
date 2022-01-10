package com.example.dyplomowaniebackend.domain.graduationProcess.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.*
import com.example.dyplomowaniebackend.domain.model.*
import com.example.dyplomowaniebackend.domain.model.exception.SubjectConstraintViolationException
import com.example.dyplomowaniebackend.domain.submission.port.persistence.PropositionAcceptanceSearchPort
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.time.Clock
import java.time.Instant

internal class SubjectCreationAdapterTest {

    private val studentSearchPort: StudentSearchPort = mockk()
    private val studentMutationPort: StudentMutationPort = mockk()
    private val staffSearchPort: StaffSearchPort = mockk()
    private val graduationProcessSearchPort: GraduationProcessSearchPort = mockk()
    private val propositionAcceptanceMutationPort: PropositionAcceptanceMutationPort = mockk()
    private val propositionAcceptanceSearchPort: PropositionAcceptanceSearchPort = mockk()
    private val subjectMutationPort: SubjectMutationPort = mockk()
    private val subjectSearchPort: SubjectSearchPort = mockk()
    private val clock: Clock = mockk()

    private val subjectCreationAdapter = SubjectCreationAdapter(
        studentSearchPort,
        studentMutationPort,
        staffSearchPort,
        graduationProcessSearchPort,
        propositionAcceptanceMutationPort,
        propositionAcceptanceSearchPort,
        subjectMutationPort,
        subjectSearchPort,
        clock
    )

    private lateinit var initiator: Student
    private lateinit var realisers: Set<Student>
    private lateinit var supervisor: StaffMember
    private lateinit var graduationProcess: GraduationProcess

    @BeforeEach
    internal fun setUp() {
        val faculty = Faculty(
            facultyId = 1,
            name = "Wydział infy i zarządznia",
            shortName = "WIZ"
        )
        val degreeCourse = DegreeCourse(
            name = "Infa",
            faculty = faculty,
        )
        initiator = Student(
            studentId = 1,
            index = "242422",
            email = "242422@student.pwr.edu.pl",
            name = "Marcel",
            surname = "Krakowiak",
            graduationProcesses = setOf(),
        )
        realisers = setOf(
            Student(
                studentId = 2,
                index = "242421",
                email = "242421@student.pwr.edu.pl",
                name = "Kacper",
                surname = "Kowalski",
                graduationProcesses = setOf(),
            ),
            Student(
                studentId = 3,
                index = "242420",
                email = "242420@student.pwr.edu.pl",
                name = "Stasiek",
                surname = "Kolorowy",
                graduationProcesses = setOf(),
            )
        )
        supervisor = StaffMember(
            staffMemberId = 1,
            email = "super.prowadzacy@pwr.edu.pl",
            name = "Super",
            surname = "Prowadzacy",
            title = Title.DOCTOR,
            currentWorkload = 24,
            absoluteWorkload = 180,
            faculty = faculty
        )
        graduationProcess = GraduationProcess(
            cSDeadline = Instant.now(),
            vFDeadline = Instant.now(),
            cADeadline = Instant.now(),
            sPDeadline = Instant.now(),
            initialSemester = "Jakiś semssetrs 5",
            finalSemester = "Jakis semestr 6",
            degree = Degree.BATCHELOR,
            hCPerSubject = 10,
            students = setOf(),
            degreeCourse = degreeCourse,
        )
    }

    @Test
    fun `should return subject for individual and initiator`() {
        // given
        val now = Instant.now()
        val expectedResult = Subject(
            subjectId = 1,
            topic = "Przyklad temat",
            topicInEnglish = "Przyklad temat english",
            objective = "Cel pracy",
            objectiveInEnglish = "Cel pracy english",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 0,
            initiator = initiator,
            supervisor = supervisor,
            graduationProcess = graduationProcess,
            status = SubjectStatus.DRAFT,
            creationDate = now
        )
        every { studentSearchPort.findStudentById(any()) } returns initiator
        every { staffSearchPort.getStaffMemberById(any()) } returns supervisor
        every { graduationProcessSearchPort.getGraduationProcessById(any()) } returns graduationProcess
        every { propositionAcceptanceMutationPort.savePropositionAcceptances(any()) } returns setOf()
        every { clock.instant() } returns now
        every { subjectMutationPort.saveSubject(any()) } answers { i ->
            val subject = i.invocation.args[0] as Subject
            val subjectWithId = subject.copy(subjectId = 1)
            subjectWithId
        }

        // when
        val result: Subject = subjectCreationAdapter.createSubject(
            SubjectCreation(
                topic = "Przyklad temat",
                topicInEnglish = "Przyklad temat english",
                objective = "Cel pracy",
                objectiveInEnglish = "Cel pracy english",
                realizationLanguage = RealizationLanguage.POLISH,
                realiseresNumber = 0,
                initiatorId = 1,
                proposedRealiserIds = setOf(),
                supervisorId = 1,
                graduationProcessId = 1
            )
        )

        // then
        verify(exactly = 1) { subjectMutationPort.saveSubject(any()) }
        verify(exactly = 1) { studentSearchPort.findStudentById(any()) }
        verify(exactly = 1) { staffSearchPort.getStaffMemberById(any()) }
        verify(exactly = 1) { graduationProcessSearchPort.getGraduationProcessById(any()) }
        verify(exactly = 1) { propositionAcceptanceMutationPort.savePropositionAcceptances(any()) }
        verify(exactly = 0) { studentSearchPort.getStudentById(any()) }
        assertEquals(expectedResult, result)
    }

    @Test
    fun `should return subject for group and initiator`() {
        // given
        val now = Instant.now()
        val expectedResult = Subject(
            subjectId = 1,
            topic = "Przyklad temat",
            topicInEnglish = "Przyklad temat english",
            objective = "Cel pracy",
            objectiveInEnglish = "Cel pracy english",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 2,
            initiator = initiator,
            supervisor = supervisor,
            graduationProcess = graduationProcess,
            status = SubjectStatus.DRAFT,
            creationDate = now
        )
        every { studentSearchPort.findStudentById(any()) } returns initiator
        every { staffSearchPort.getStaffMemberById(any()) } returns supervisor
        every { graduationProcessSearchPort.getGraduationProcessById(any()) } returns graduationProcess
        every { propositionAcceptanceMutationPort.savePropositionAcceptances(any()) } returns setOf()
        every { studentSearchPort.getStudentById(any()) } returnsMany realisers.toList()
        every { clock.instant() } returns now
        every { subjectMutationPort.saveSubject(any()) } answers { i ->
            val subject = i.invocation.args[0] as Subject
            val subjectWithId = subject.copy(subjectId = 1)
            subjectWithId
        }

        // when
        val result: Subject = subjectCreationAdapter.createSubject(
            SubjectCreation(
                topic = "Przyklad temat",
                topicInEnglish = "Przyklad temat english",
                objective = "Cel pracy",
                objectiveInEnglish = "Cel pracy english",
                realizationLanguage = RealizationLanguage.POLISH,
                realiseresNumber = 2,
                initiatorId = 1,
                proposedRealiserIds = realisers.map { it.studentId!! }.toSet(),
                supervisorId = 1,
                graduationProcessId = 1
            )
        )

        // then
        verify(exactly = 1) { subjectMutationPort.saveSubject(any()) }
        verify(exactly = 1) { studentSearchPort.findStudentById(any()) }
        verify(exactly = 1) { staffSearchPort.getStaffMemberById(any()) }
        verify(exactly = 1) { graduationProcessSearchPort.getGraduationProcessById(any()) }
        verify(exactly = 1) { propositionAcceptanceMutationPort.savePropositionAcceptances(any()) }
        verify(exactly = 2) { studentSearchPort.getStudentById(any()) }
        assertEquals(expectedResult, result)
    }

    @Test
    fun `should return subject for individual and supervisor`() {
        // given
        val now = Instant.now()
        val expectedResult = Subject(
            subjectId = 1,
            topic = "Przyklad temat",
            topicInEnglish = "Przyklad temat english",
            objective = "Cel pracy",
            objectiveInEnglish = "Cel pracy english",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 0,
            initiator = null,
            supervisor = supervisor,
            graduationProcess = graduationProcess,
            status = SubjectStatus.DRAFT,
            creationDate = now
        )
        every { staffSearchPort.getStaffMemberById(any()) } returns supervisor
        every { graduationProcessSearchPort.getGraduationProcessById(any()) } returns graduationProcess
        every { propositionAcceptanceMutationPort.savePropositionAcceptances(any()) } returns setOf()
        every { clock.instant() } returns now
        every { subjectMutationPort.saveSubject(any()) } answers { i ->
            val subject = i.invocation.args[0] as Subject
            val subjectWithId = subject.copy(subjectId = 1)
            subjectWithId
        }

        // when
        val result: Subject = subjectCreationAdapter.createSubject(
            SubjectCreation(
                topic = "Przyklad temat",
                topicInEnglish = "Przyklad temat english",
                objective = "Cel pracy",
                objectiveInEnglish = "Cel pracy english",
                realizationLanguage = RealizationLanguage.POLISH,
                realiseresNumber = 0,
                initiatorId = null,
                proposedRealiserIds = setOf(),
                supervisorId = 1,
                graduationProcessId = 1
            )
        )

        // then
        verify(exactly = 1) { subjectMutationPort.saveSubject(any()) }
        verify(exactly = 0) { studentSearchPort.findStudentById(any()) }
        verify(exactly = 1) { staffSearchPort.getStaffMemberById(any()) }
        verify(exactly = 1) { graduationProcessSearchPort.getGraduationProcessById(any()) }
        verify(exactly = 1) { propositionAcceptanceMutationPort.savePropositionAcceptances(any()) }
        verify(exactly = 0) { studentSearchPort.getStudentById(any()) }
        assertEquals(expectedResult, result)
    }

    @Test
    fun `should return subject for group and supervisor`() {
        // given
        val now = Instant.now()
        val expectedResult = Subject(
            subjectId = 1,
            topic = "Przyklad temat",
            topicInEnglish = "Przyklad temat english",
            objective = "Cel pracy",
            objectiveInEnglish = "Cel pracy english",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 2,
            initiator = null,
            supervisor = supervisor,
            graduationProcess = graduationProcess,
            status = SubjectStatus.DRAFT,
            creationDate = now
        )
        every { staffSearchPort.getStaffMemberById(any()) } returns supervisor
        every { graduationProcessSearchPort.getGraduationProcessById(any()) } returns graduationProcess
        every { propositionAcceptanceMutationPort.savePropositionAcceptances(any()) } returns setOf()
        every { clock.instant() } returns now
        every { subjectMutationPort.saveSubject(any()) } answers { i ->
            val subject = i.invocation.args[0] as Subject
            val subjectWithId = subject.copy(subjectId = 1)
            subjectWithId
        }

        // when
        val result: Subject = subjectCreationAdapter.createSubject(
            SubjectCreation(
                topic = "Przyklad temat",
                topicInEnglish = "Przyklad temat english",
                objective = "Cel pracy",
                objectiveInEnglish = "Cel pracy english",
                realizationLanguage = RealizationLanguage.POLISH,
                realiseresNumber = 2,
                initiatorId = null,
                proposedRealiserIds = setOf(),
                supervisorId = 1,
                graduationProcessId = 1
            )
        )

        // then
        verify(exactly = 1) { subjectMutationPort.saveSubject(any()) }
        verify(exactly = 0) { studentSearchPort.findStudentById(any()) }
        verify(exactly = 1) { staffSearchPort.getStaffMemberById(any()) }
        verify(exactly = 1) { graduationProcessSearchPort.getGraduationProcessById(any()) }
        verify(exactly = 1) { propositionAcceptanceMutationPort.savePropositionAcceptances(any()) }
        verify(exactly = 0) { studentSearchPort.getStudentById(any()) }
        assertEquals(expectedResult, result)
    }

    @Test
    fun `should throw subject constraint violation exception`() {
        // given
        // when
        assertThrows(SubjectConstraintViolationException::class.java) {
            subjectCreationAdapter.createSubject(
                SubjectCreation(
                    topic = "Przyklad temat",
                    topicInEnglish = "Przyklad temat english",
                    objective = "Cel pracy",
                    objectiveInEnglish = "Cel pracy english",
                    realizationLanguage = RealizationLanguage.POLISH,
                    realiseresNumber = 2,
                    initiatorId = null,
                    proposedRealiserIds = realisers.map { it.studentId!! }.toSet(),
                    supervisorId = 1,
                    graduationProcessId = 1
                )
            )
        }

        // then
        verify(exactly = 0) { subjectMutationPort.saveSubject(any()) }
        verify(exactly = 0) { studentSearchPort.findStudentById(any()) }
        verify(exactly = 0) { staffSearchPort.getStaffMemberById(any()) }
        verify(exactly = 0) { graduationProcessSearchPort.getGraduationProcessById(any()) }
        verify(exactly = 0) { propositionAcceptanceMutationPort.savePropositionAcceptances(any()) }
        verify(exactly = 0) { studentSearchPort.getStudentById(any()) }
    }

    @Test
    fun rejectSubject() {
    }

    @Test
    fun acceptSupervisorSubject() {
    }

    @Test
    fun acceptInitiatorSubject() {
    }

    @Test
    fun sendToVerificationSubject() {
    }
}