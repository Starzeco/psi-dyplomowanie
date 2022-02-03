package com.example.dyplomowaniebackend.domain.graduationProcess.adapter

import com.example.dyplomowaniebackend.domain.graduationProcess.port.mail.MailSenderPort
import com.example.dyplomowaniebackend.domain.graduationProcess.port.persistence.*
import com.example.dyplomowaniebackend.domain.model.*
import com.example.dyplomowaniebackend.domain.model.exception.SubjectConstraintViolationException
import com.example.dyplomowaniebackend.domain.model.exception.SubjectStatusChangeException
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
    private val verifierSearchPort: VerifierSearchPort = mockk()
    private val verificationMutationPort: VerificationMutationPort = mockk()
    private val mailSenderPort: MailSenderPort = mockk(relaxed = true)

    private val subjectCreationAdapter = SubjectCreationAdapter(
        studentSearchPort,
        studentMutationPort,
        staffSearchPort,
        graduationProcessSearchPort,
        propositionAcceptanceMutationPort,
        propositionAcceptanceSearchPort,
        subjectMutationPort,
        subjectSearchPort,
        clock,
        verifierSearchPort,
        verificationMutationPort,
        mailSenderPort,
    )

    private lateinit var initiator: Student
    private lateinit var realisers: Set<Student>
    private lateinit var supervisor: StaffMember
    private lateinit var graduationProcess: GraduationProcess
    private lateinit var verifiers: List<Verifier>

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
            graduationProcessId = 1,
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
        val verifierStaffMember0 = StaffMember(
            staffMemberId = 1,
            email = "super.weryfikator0@pwr.edu.pl",
            name = "Super",
            surname = "Weryfikator0",
            title = Title.DOCTOR,
            currentWorkload = 24,
            absoluteWorkload = 180,
            faculty = faculty
        )
        val verifierStaffMember1 = StaffMember(
            staffMemberId = 2,
            email = "super.weryfikator1@pwr.edu.pl",
            name = "Super",
            surname = "Weryfikator1",
            title = Title.DOCTOR,
            currentWorkload = 24,
            absoluteWorkload = 180,
            faculty = faculty
        )
        val verifierStaffMember2 = StaffMember(
            staffMemberId = 3,
            email = "super.weryfikator2@pwr.edu.pl",
            name = "Super",
            surname = "Weryfikator2",
            title = Title.DOCTOR,
            currentWorkload = 24,
            absoluteWorkload = 180,
            faculty = faculty
        )
        val verifier0 = Verifier(
            verifierId = 1,
            name = "Weryfikator poziomu 0",
            verificationsDeadline = Instant.now(),
            verifier = verifierStaffMember0,
            graduationProcess = graduationProcess
        )

        val verifier1 = Verifier(
            verifierId = 2,
            name = "Weryfikator poziomu 1",
            verificationsDeadline = Instant.now(),
            verifier = verifierStaffMember1,
            graduationProcess = graduationProcess
        )

        val verifier2 = Verifier(
            verifierId = 3,
            name = "Weryfikator poziomu 2",
            verificationsDeadline = Instant.now(),
            verifier = verifierStaffMember2,
            graduationProcess = graduationProcess
        )
        verifiers = listOf(verifier0, verifier1, verifier2)
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
            realiseresNumber = 1,
            initiator = initiator,
            supervisor = supervisor,
            graduationProcess = graduationProcess,
            status = SubjectStatus.DRAFT,
            creationDate = now,
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        every { studentSearchPort.findById(any()) } returns initiator
        every { staffSearchPort.getStaffMemberById(any()) } returns supervisor
        every { graduationProcessSearchPort.getGraduationProcessById(any()) } returns graduationProcess
        every { propositionAcceptanceMutationPort.insertAll(any()) } returns setOf()
        every { clock.instant() } returns now
        every { subjectMutationPort.insert(any()) } answers { i ->
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
        verify(exactly = 1) { subjectMutationPort.insert(any()) }
        verify(exactly = 1) { studentSearchPort.findById(any()) }
        verify(exactly = 1) { staffSearchPort.getStaffMemberById(any()) }
        verify(exactly = 1) { graduationProcessSearchPort.getGraduationProcessById(any()) }
        verify(exactly = 1) { propositionAcceptanceMutationPort.insertAll(any()) }
        verify(exactly = 0) { studentSearchPort.getById(any()) }
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
            realiseresNumber = 3,
            initiator = initiator,
            supervisor = supervisor,
            graduationProcess = graduationProcess,
            status = SubjectStatus.DRAFT,
            creationDate = now,
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        every { studentSearchPort.findById(any()) } returns initiator
        every { staffSearchPort.getStaffMemberById(any()) } returns supervisor
        every { graduationProcessSearchPort.getGraduationProcessById(any()) } returns graduationProcess
        every { propositionAcceptanceMutationPort.insertAll(any()) } returns setOf()
        every { studentSearchPort.getById(any()) } returnsMany realisers.toList()
        every { clock.instant() } returns now
        every { subjectMutationPort.insert(any()) } answers { i ->
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
        verify(exactly = 1) { subjectMutationPort.insert(any()) }
        verify(exactly = 1) { studentSearchPort.findById(any()) }
        verify(exactly = 1) { staffSearchPort.getStaffMemberById(any()) }
        verify(exactly = 1) { graduationProcessSearchPort.getGraduationProcessById(any()) }
        verify(exactly = 1) { propositionAcceptanceMutationPort.insertAll(any()) }
        verify(exactly = 2) { studentSearchPort.getById(any()) }
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
            creationDate = now,
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        every { staffSearchPort.getStaffMemberById(any()) } returns supervisor
        every { graduationProcessSearchPort.getGraduationProcessById(any()) } returns graduationProcess
        every { propositionAcceptanceMutationPort.insertAll(any()) } returns setOf()
        every { clock.instant() } returns now
        every { subjectMutationPort.insert(any()) } answers { i ->
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
        verify(exactly = 1) { subjectMutationPort.insert(any()) }
        verify(exactly = 0) { studentSearchPort.findById(any()) }
        verify(exactly = 1) { staffSearchPort.getStaffMemberById(any()) }
        verify(exactly = 1) { graduationProcessSearchPort.getGraduationProcessById(any()) }
        verify(exactly = 1) { propositionAcceptanceMutationPort.insertAll(any()) }
        verify(exactly = 0) { studentSearchPort.getById(any()) }
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
            creationDate = now,
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        every { staffSearchPort.getStaffMemberById(any()) } returns supervisor
        every { graduationProcessSearchPort.getGraduationProcessById(any()) } returns graduationProcess
        every { propositionAcceptanceMutationPort.insertAll(any()) } returns setOf()
        every { clock.instant() } returns now
        every { subjectMutationPort.insert(any()) } answers { i ->
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
        verify(exactly = 1) { subjectMutationPort.insert(any()) }
        verify(exactly = 0) { studentSearchPort.findById(any()) }
        verify(exactly = 1) { staffSearchPort.getStaffMemberById(any()) }
        verify(exactly = 1) { graduationProcessSearchPort.getGraduationProcessById(any()) }
        verify(exactly = 1) { propositionAcceptanceMutationPort.insertAll(any()) }
        verify(exactly = 0) { studentSearchPort.getById(any()) }
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
        verify(exactly = 0) { subjectMutationPort.insert(any()) }
        verify(exactly = 0) { studentSearchPort.findById(any()) }
        verify(exactly = 0) { staffSearchPort.getStaffMemberById(any()) }
        verify(exactly = 0) { graduationProcessSearchPort.getGraduationProcessById(any()) }
        verify(exactly = 0) { propositionAcceptanceMutationPort.insertAll(any()) }
        verify(exactly = 0) { studentSearchPort.getById(any()) }
    }

    @Test
    fun `should return reject status and subjectId - created by supervisor`() {
        // given
        val subjectId: Long = 1
        val expected = SubjectStatusUpdate(subjectId, SubjectStatus.REJECTED)
        val subjectToUpdate = Subject(
            subjectId = subjectId,
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
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        every { subjectSearchPort.getById(any(), false) } returns subjectToUpdate
        every { subjectMutationPort.updateStatus(any()) } answers { it.invocation.args[0] as SubjectStatusUpdate }

        // when
        val result: SubjectStatusUpdate = subjectCreationAdapter.rejectSubject(1)

        // then
        verify(exactly = 1) { subjectSearchPort.getById(any(), false) }
        verify(exactly = 1) { subjectMutationPort.updateStatus(any()) }
        assertEquals(expected, result)
    }

    @Test
    fun `should return reject status and subjectId - created by initiator draft`() {
        // given
        val subjectId: Long = 1
        val expected = SubjectStatusUpdate(subjectId, SubjectStatus.REJECTED)
        val subjectToUpdate = Subject(
            subjectId = subjectId,
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
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        every { subjectSearchPort.getById(any(), false) } returns subjectToUpdate
        every { subjectMutationPort.updateStatus(any()) } answers { it.invocation.args[0] as SubjectStatusUpdate }

        // when
        val result: SubjectStatusUpdate = subjectCreationAdapter.rejectSubject(1)

        // then
        verify(exactly = 1) { subjectSearchPort.getById(any(), false) }
        verify(exactly = 1) { subjectMutationPort.updateStatus(any()) }
        assertEquals(expected, result)
    }

    @Test
    fun `should return reject status and subjectId - created by initiator accepted_by_supervisor`() {
        // given
        val subjectId: Long = 1
        val expected = SubjectStatusUpdate(subjectId, SubjectStatus.REJECTED)
        val subjectToUpdate = Subject(
            subjectId = subjectId,
            topic = "Przyklad temat",
            topicInEnglish = "Przyklad temat english",
            objective = "Cel pracy",
            objectiveInEnglish = "Cel pracy english",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 0,
            initiator = initiator,
            supervisor = supervisor,
            graduationProcess = graduationProcess,
            status = SubjectStatus.ACCEPTED_BY_SUPERVISOR,
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        every { subjectSearchPort.getById(any(), false) } returns subjectToUpdate
        every { subjectMutationPort.updateStatus(any()) } answers { it.invocation.args[0] as SubjectStatusUpdate }

        // when
        val result: SubjectStatusUpdate = subjectCreationAdapter.rejectSubject(1)

        // then
        verify(exactly = 1) { subjectSearchPort.getById(any(), false) }
        verify(exactly = 1) { subjectMutationPort.updateStatus(any()) }
        assertEquals(expected, result)
    }

    @Test
    fun `should throw cannot reject`() {
        // given
        val subjectId: Long = 1
        val subjectToUpdate = Subject(
            subjectId = subjectId,
            topic = "Przyklad temat",
            topicInEnglish = "Przyklad temat english",
            objective = "Cel pracy",
            objectiveInEnglish = "Cel pracy english",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 0,
            initiator = initiator,
            supervisor = supervisor,
            graduationProcess = graduationProcess,
            status = SubjectStatus.ACCEPTED_BY_INITIATOR,
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        every { subjectSearchPort.getById(any(), false) } returns subjectToUpdate

        // when
        assertThrows(SubjectStatusChangeException::class.java) {
            subjectCreationAdapter.rejectSubject(1)
        }

        // then
        verify(exactly = 1) { subjectSearchPort.getById(any(), false) }
        verify(exactly = 0) { subjectMutationPort.updateStatus(any()) }
    }

    @Test
    fun `should return accepted_by_supervisor status and subjectId - individual`() {
        // given
        val subjectId: Long = 1
        val expected = SubjectStatusUpdate(subjectId, SubjectStatus.ACCEPTED_BY_SUPERVISOR)
        val subjectToUpdate = Subject(
            subjectId = subjectId,
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
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        every { subjectSearchPort.getById(any(), false) } returns subjectToUpdate
        every { propositionAcceptanceSearchPort.getAllBySubjectId(any()) } returns setOf()
        every { subjectMutationPort.updateStatus(any()) } answers { it.invocation.args[0] as SubjectStatusUpdate }

        // when
        val result: SubjectStatusUpdate = subjectCreationAdapter.acceptSubjectPreparedBySupervisor(1)

        // then
        verify(exactly = 1) { subjectSearchPort.getById(any(), false) }
        verify(exactly = 1) { propositionAcceptanceSearchPort.getAllBySubjectId(any()) }
        verify(exactly = 1) { subjectMutationPort.updateStatus(any()) }
        assertEquals(expected, result)
    }

    @Test
    fun `should return accepted_by_supervisor status and subjectId - group`() {
        // given
        val subjectId: Long = 1
        val expected = SubjectStatusUpdate(subjectId, SubjectStatus.ACCEPTED_BY_SUPERVISOR)
        val subjectToUpdate = Subject(
            subjectId = subjectId,
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
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        val propositionsAcceptances: Set<PropositionAcceptance> = setOf(
            PropositionAcceptance(
                propositionAcceptanceId = 1,
                student = realisers.elementAt(0),
                subject = subjectToUpdate,
                accepted = true
            ),
            PropositionAcceptance(
                propositionAcceptanceId = 2,
                student = realisers.elementAt(1),
                subject = subjectToUpdate,
                accepted = true
            )
        )
        every { subjectSearchPort.getById(any(), false) } returns subjectToUpdate
        every { propositionAcceptanceSearchPort.getAllBySubjectId(any()) } returns propositionsAcceptances
        every { subjectMutationPort.updateStatus(any()) } answers { it.invocation.args[0] as SubjectStatusUpdate }

        // when
        val result: SubjectStatusUpdate = subjectCreationAdapter.acceptSubjectPreparedBySupervisor(1)

        // then
        verify(exactly = 1) { subjectSearchPort.getById(any(), false) }
        verify(exactly = 1) { propositionAcceptanceSearchPort.getAllBySubjectId(any()) }
        verify(exactly = 1) { subjectMutationPort.updateStatus(any()) }
        assertEquals(expected, result)
    }

    @Test
    fun `should throw cannot accept by supervisor - not accepted acceptance`() {
        // given
        val subjectId: Long = 1
        val subjectToUpdate = Subject(
            subjectId = subjectId,
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
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        val propositionsAcceptances: Set<PropositionAcceptance> = setOf(
            PropositionAcceptance(
                propositionAcceptanceId = 1,
                student = realisers.elementAt(0),
                subject = subjectToUpdate,
                accepted = false
            ),
            PropositionAcceptance(
                propositionAcceptanceId = 2,
                student = realisers.elementAt(1),
                subject = subjectToUpdate,
                accepted = null
            )
        )
        every { subjectSearchPort.getById(any(), false) } returns subjectToUpdate
        every { propositionAcceptanceSearchPort.getAllBySubjectId(any()) } returns propositionsAcceptances

        // when
        assertThrows(SubjectStatusChangeException::class.java) { subjectCreationAdapter.acceptSubjectPreparedBySupervisor(1) }

        // then
        verify(exactly = 1) { subjectSearchPort.getById(any(), false) }
        verify(exactly = 1) { propositionAcceptanceSearchPort.getAllBySubjectId(any()) }
        verify(exactly = 0) { subjectMutationPort.updateStatus(any()) }
    }

    @Test
    fun `should throw cannot accept by supervisor - wrong status`() {
        // given
        val subjectId: Long = 1
        val subjectToUpdate = Subject(
            subjectId = subjectId,
            topic = "Przyklad temat",
            topicInEnglish = "Przyklad temat english",
            objective = "Cel pracy",
            objectiveInEnglish = "Cel pracy english",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 0,
            initiator = initiator,
            supervisor = supervisor,
            graduationProcess = graduationProcess,
            status = SubjectStatus.RESERVED,
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        val propositionsAcceptances: Set<PropositionAcceptance> = setOf(
            PropositionAcceptance(
                propositionAcceptanceId = 1,
                student = realisers.elementAt(0),
                subject = subjectToUpdate,
                accepted = true
            ),
            PropositionAcceptance(
                propositionAcceptanceId = 2,
                student = realisers.elementAt(1),
                subject = subjectToUpdate,
                accepted = true
            )
        )
        every { subjectSearchPort.getById(any(), false) } returns subjectToUpdate
        every { propositionAcceptanceSearchPort.getAllBySubjectId(any()) } returns propositionsAcceptances

        // when
        assertThrows(SubjectStatusChangeException::class.java) { subjectCreationAdapter.acceptSubjectPreparedBySupervisor(1) }

        // then
        verify(exactly = 1) { subjectSearchPort.getById(any(), false) }
        verify(exactly = 1) { propositionAcceptanceSearchPort.getAllBySubjectId(any()) }
        verify(exactly = 0) { subjectMutationPort.updateStatus(any()) }
    }

    @Test
    fun `should return accepted_by_initiator status and subjectId - none realisers have subject`() {
        // given
        val subjectId: Long = 1
        val expected = SubjectStatusUpdate(subjectId, SubjectStatus.ACCEPTED_BY_INITIATOR)
        val subjectToUpdate = Subject(
            subjectId = subjectId,
            topic = "Przyklad temat",
            topicInEnglish = "Przyklad temat english",
            objective = "Cel pracy",
            objectiveInEnglish = "Cel pracy english",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 0,
            initiator = initiator,
            supervisor = supervisor,
            graduationProcess = graduationProcess,
            status = SubjectStatus.ACCEPTED_BY_SUPERVISOR,
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        val propositionsAcceptances: Set<PropositionAcceptance> = setOf(
            PropositionAcceptance(
                propositionAcceptanceId = 1,
                student = realisers.elementAt(0),
                subject = subjectToUpdate,
                accepted = true
            ),
            PropositionAcceptance(
                propositionAcceptanceId = 2,
                student = realisers.elementAt(1),
                subject = subjectToUpdate,
                accepted = true
            )
        )
        every { subjectSearchPort.getById(any(), false) } returns subjectToUpdate
        every { propositionAcceptanceSearchPort.getAllBySubjectId(any()) } returns propositionsAcceptances
        every { studentMutationPort.updateSubjectIdByStudentIdIn(any(), any()) } returns setOf()
        every { subjectMutationPort.updateStatus(any()) } answers { it.invocation.args[0] as SubjectStatusUpdate }

        // when
        val result: SubjectStatusUpdate = subjectCreationAdapter.acceptSubjectPreparedByInitiator(1)

        // then
        verify(exactly = 1) { subjectSearchPort.getById(any(), false) }
        verify(exactly = 1) { subjectMutationPort.updateStatus(any()) }
        verify(exactly = 1) { propositionAcceptanceSearchPort.getAllBySubjectId(any()) }
        verify(exactly = 1) { studentMutationPort.updateSubjectIdByStudentIdIn(any(), any()) }
        assertEquals(expected, result)
    }

    @Test
    fun `should throw cannot accept by initiator - initiator has subject`() {
        // given
        val subjectId: Long = 1
        val otherSubject = Subject(
            subjectId = 2,
            topic = "Przyklad temat",
            topicInEnglish = "Przyklad temat english",
            objective = "Cel pracy",
            objectiveInEnglish = "Cel pracy english",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 0,
            initiator = initiator,
            supervisor = supervisor,
            graduationProcess = graduationProcess,
            status = SubjectStatus.IN_VERIFICATION,
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        val subjectToUpdate = Subject(
            subjectId = subjectId,
            topic = "Przyklad temat",
            topicInEnglish = "Przyklad temat english",
            objective = "Cel pracy",
            objectiveInEnglish = "Cel pracy english",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 0,
            initiator = initiator.copy(subject = otherSubject),
            supervisor = supervisor,
            graduationProcess = graduationProcess,
            status = SubjectStatus.ACCEPTED_BY_SUPERVISOR,
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        val propositionsAcceptances: Set<PropositionAcceptance> = setOf(
            PropositionAcceptance(
                propositionAcceptanceId = 1,
                student = realisers.elementAt(0),
                subject = subjectToUpdate,
                accepted = true
            ),
            PropositionAcceptance(
                propositionAcceptanceId = 2,
                student = realisers.elementAt(1),
                subject = subjectToUpdate,
                accepted = true
            )
        )
        every { subjectSearchPort.getById(any(), false) } returns subjectToUpdate
        every { propositionAcceptanceSearchPort.getAllBySubjectId(any()) } returns propositionsAcceptances

        // when
        assertThrows(SubjectStatusChangeException::class.java) { subjectCreationAdapter.acceptSubjectPreparedByInitiator(1) }

        // then
        verify(exactly = 1) { subjectSearchPort.getById(any(), false) }
        verify(exactly = 1) { propositionAcceptanceSearchPort.getAllBySubjectId(any()) }
        verify(exactly = 0) { subjectMutationPort.updateStatus(any()) }
        verify(exactly = 0) { studentMutationPort.updateSubjectIdByStudentIdIn(any(), any()) }
    }

    @Test
    fun `should throw cannot accept by initiator - one of realisers has subject`() {
        // given
        val subjectId: Long = 1
        val otherSubject = Subject(
            subjectId = 2,
            topic = "Przyklad temat",
            topicInEnglish = "Przyklad temat english",
            objective = "Cel pracy",
            objectiveInEnglish = "Cel pracy english",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 0,
            initiator = initiator,
            supervisor = supervisor,
            graduationProcess = graduationProcess,
            status = SubjectStatus.IN_VERIFICATION,
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        val subjectToUpdate = Subject(
            subjectId = subjectId,
            topic = "Przyklad temat",
            topicInEnglish = "Przyklad temat english",
            objective = "Cel pracy",
            objectiveInEnglish = "Cel pracy english",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 0,
            initiator = initiator,
            supervisor = supervisor,
            graduationProcess = graduationProcess,
            status = SubjectStatus.ACCEPTED_BY_SUPERVISOR,
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        val propositionsAcceptances: Set<PropositionAcceptance> = setOf(
            PropositionAcceptance(
                propositionAcceptanceId = 1,
                student = realisers.elementAt(0),
                subject = subjectToUpdate,
                accepted = true
            ),
            PropositionAcceptance(
                propositionAcceptanceId = 2,
                student = realisers.elementAt(1).copy(subject = otherSubject),
                subject = subjectToUpdate,
                accepted = true
            )
        )
        every { subjectSearchPort.getById(any(), false) } returns subjectToUpdate
        every { propositionAcceptanceSearchPort.getAllBySubjectId(any()) } returns propositionsAcceptances

        // when
        assertThrows(SubjectStatusChangeException::class.java) { subjectCreationAdapter.acceptSubjectPreparedByInitiator(1) }

        // then
        verify(exactly = 1) { subjectSearchPort.getById(any(), false) }
        verify(exactly = 1) { propositionAcceptanceSearchPort.getAllBySubjectId(any()) }
        verify(exactly = 0) { subjectMutationPort.updateStatus(any()) }
        verify(exactly = 0) { studentMutationPort.updateSubjectIdByStudentIdIn(any(), any()) }
    }

    @Test
    fun `should throw cannot accept by initiator - wrong status`() {
        // given
        val subjectId: Long = 1
        val subjectToUpdate = Subject(
            subjectId = subjectId,
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
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        val propositionsAcceptances: Set<PropositionAcceptance> = setOf(
            PropositionAcceptance(
                propositionAcceptanceId = 1,
                student = realisers.elementAt(0),
                subject = subjectToUpdate,
                accepted = true
            ),
            PropositionAcceptance(
                propositionAcceptanceId = 2,
                student = realisers.elementAt(1),
                subject = subjectToUpdate,
                accepted = true
            )
        )
        every { subjectSearchPort.getById(any(), false) } returns subjectToUpdate
        every { propositionAcceptanceSearchPort.getAllBySubjectId(any()) } returns propositionsAcceptances

        // when
        assertThrows(SubjectStatusChangeException::class.java) { subjectCreationAdapter.acceptSubjectPreparedByInitiator(1) }

        // then
        verify(exactly = 1) { subjectSearchPort.getById(any(), false) }
        verify(exactly = 1) { propositionAcceptanceSearchPort.getAllBySubjectId(any()) }
        verify(exactly = 0) { subjectMutationPort.updateStatus(any()) }
        verify(exactly = 0) { studentMutationPort.updateSubjectIdByStudentIdIn(any(), any()) }
    }

    @Test
    fun `should return in_verification status and subjectId - created by supervisor`() {
        // given
        val subjectId: Long = 1
        val expected = SubjectStatusUpdate(subjectId, SubjectStatus.IN_VERIFICATION)
        val subjectToUpdate = Subject(
            subjectId = subjectId,
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
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        every { subjectSearchPort.getById(any(), true) } returns subjectToUpdate
        every { subjectMutationPort.updateStatus(any()) } answers { it.invocation.args[0] as SubjectStatusUpdate }
        every { verifierSearchPort.findVerifiersByGraduationProcessId(any()) } returns verifiers
        every { verificationMutationPort.insertAll(any()) } answers {
            val verifications = it.invocation.args[0] as List<Verification>
            verifications.mapIndexed { i, verification -> verification.copy(verificationId = i.toLong()) }
        }

        // when
        val result: SubjectStatusUpdate = subjectCreationAdapter.sendSubjectToVerification(1)

        // then
        verify(exactly = 1) { subjectSearchPort.getById(any(), true) }
        verify(exactly = 1) { subjectMutationPort.updateStatus(any()) }
        verify(exactly = 1) { verificationMutationPort.insertAll(any()) }
        verify(exactly = 1) { verificationMutationPort.insertAll(any()) }
        assertEquals(expected, result)
    }

    @Test
    fun `should return in_verification status and subjectId - created by initiator`() {
        // given
        val subjectId: Long = 1
        val expected = SubjectStatusUpdate(subjectId, SubjectStatus.IN_VERIFICATION)
        val subjectToUpdate = Subject(
            subjectId = subjectId,
            topic = "Przyklad temat",
            topicInEnglish = "Przyklad temat english",
            objective = "Cel pracy",
            objectiveInEnglish = "Cel pracy english",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 0,
            initiator = initiator,
            supervisor = supervisor,
            graduationProcess = graduationProcess,
            status = SubjectStatus.ACCEPTED_BY_INITIATOR,
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        every { subjectSearchPort.getById(any(), true) } returns subjectToUpdate
        every { subjectMutationPort.updateStatus(any()) } answers { it.invocation.args[0] as SubjectStatusUpdate }
        every { verifierSearchPort.findVerifiersByGraduationProcessId(any()) } returns verifiers
        every { verificationMutationPort.insertAll(any()) } answers {
            val verifications = it.invocation.args[0] as List<Verification>
            verifications.mapIndexed { i, verification -> verification.copy(verificationId = i.toLong()) }
        }

        // when
        val result: SubjectStatusUpdate = subjectCreationAdapter.sendSubjectToVerification(1)

        // then
        verify(exactly = 1) { subjectSearchPort.getById(any(), true) }
        verify(exactly = 1) { subjectMutationPort.updateStatus(any()) }
        verify(exactly = 1) { verificationMutationPort.insertAll(any()) }
        verify(exactly = 1) { verificationMutationPort.insertAll(any()) }
        assertEquals(expected, result)
    }

    @Test
    fun `should throw cannot send to verification - wrong status`() {
        // given
        val subjectId: Long = 1
        val subjectToUpdate = Subject(
            subjectId = subjectId,
            topic = "Przyklad temat",
            topicInEnglish = "Przyklad temat english",
            objective = "Cel pracy",
            objectiveInEnglish = "Cel pracy english",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 0,
            initiator = initiator,
            supervisor = supervisor,
            graduationProcess = graduationProcess,
            status = SubjectStatus.ACCEPTED_BY_SUPERVISOR,
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        every { subjectSearchPort.getById(any(), true) } returns subjectToUpdate

        // when
        assertThrows(SubjectStatusChangeException::class.java) { subjectCreationAdapter.sendSubjectToVerification(1) }

        // then
        verify(exactly = 1) { subjectSearchPort.getById(any(), true) }
        verify(exactly = 0) { subjectMutationPort.updateStatus(any()) }
    }

    @Test
    fun `should update subject`() {
        // given
        val subjectToUpdate = Subject(
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
            status = SubjectStatus.ACCEPTED_BY_SUPERVISOR,
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        val expectedResult = SubjectUpdate(
            subjectId = 1,
            topic = "zmiana",
            topicInEnglish = "zmiana",
            objective = "zmiana",
            objectiveInEnglish = "zmiana",
            realiseresNumber = 2,
        )

        every { subjectSearchPort.getById(any(), true) } returns subjectToUpdate
        every { subjectMutationPort.updateSubject(any()) } answers { it.invocation.args[0] as SubjectUpdate }

        // when
        val result: SubjectUpdate = subjectCreationAdapter.updateSubject(expectedResult)

        // then
        verify(exactly = 1) { subjectSearchPort.getById(any(), true) }
        verify(exactly = 1) { subjectMutationPort.updateSubject(any()) }
        assertEquals(expectedResult, result)
    }

    @Test
    fun `should thrown cannot update realisers number`() {
        // given
        val subjectToUpdate = Subject(
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
            status = SubjectStatus.ACCEPTED_BY_SUPERVISOR,
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )
        val expectedResult = SubjectUpdate(
            subjectId = 1,
            topic = "zmiana",
            topicInEnglish = "zmiana",
            objective = "zmiana",
            objectiveInEnglish = "zmiana",
            realiseresNumber = 2,
        )

        every { subjectSearchPort.getById(any(), true) } returns subjectToUpdate

        // when
        assertThrows(SubjectConstraintViolationException::class.java) { subjectCreationAdapter.updateSubject(expectedResult) }

        // then
        verify(exactly = 1) { subjectSearchPort.getById(any(), true) }
        verify(exactly = 0) { subjectMutationPort.updateSubject(any()) }
    }
}
