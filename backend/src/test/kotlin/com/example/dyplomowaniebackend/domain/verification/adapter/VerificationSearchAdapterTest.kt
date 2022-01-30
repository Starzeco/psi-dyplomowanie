package com.example.dyplomowaniebackend.domain.verification.adapter

import com.example.dyplomowaniebackend.domain.model.*
import com.example.dyplomowaniebackend.domain.verification.port.persistence.VerificationSearchPort
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.time.Instant

internal class VerificationSearchAdapterTest {

    private val verificationSearchPort: VerificationSearchPort = mockk()

    private val verificationSearchAdapter = VerificationSearchAdapter(
        verificationSearchPort
    )

    private lateinit var verifications: List<Verification>

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
        val initiator = Student(
            studentId = 1,
            index = "242422",
            email = "242422@student.pwr.edu.pl",
            name = "Marcel",
            surname = "Krakowiak",
            graduationProcesses = setOf(),
        )
        val realisers = setOf(
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
        val supervisor = StaffMember(
            staffMemberId = 1,
            email = "super.prowadzacy@pwr.edu.pl",
            name = "Super",
            surname = "Prowadzacy",
            title = Title.DOCTOR,
            currentWorkload = 24,
            absoluteWorkload = 180,
            faculty = faculty
        )
        val graduationProcess = GraduationProcess(
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
        val subject = Subject(
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
            creationDate = Instant.now(),
            propositionAcceptances = setOf(),
            realiser = setOf()
        )

        verifications = listOf(
            Verification(
                verificationId = 1,
                verified = false,
                justification = "teskt1",
                subject = subject,
                verifier = verifier0,
            ),
            Verification(
                verificationId = 2,
                verified = false,
                justification = "teskt2",
                subject = subject,
                verifier = verifier1,
            )
        )
    }

    @Test
    fun `should return empty list`() {
        // given
        every { verificationSearchPort.findAllVerifications(any(), any(), any(), any()) } returns listOf()

        // when
        val result = verificationSearchAdapter.findAllVerifications(1, "test", true, null)

        // then
        verify(exactly = 1) { verificationSearchPort.findAllVerifications(any(), any(), any(), any()) }
        assertEquals(listOf<Verification>(), result)
    }

    @Test
    fun `should return list of some verifications`() {
        // given
        every { verificationSearchPort.findAllVerifications(any(), any(), any(), any()) } returns verifications

        // when
        val result = verificationSearchAdapter.findAllVerifications(1, "test", true, null)

        // then
        verify(exactly = 1) { verificationSearchPort.findAllVerifications(any(), any(), any(), any()) }
        assertEquals(verifications, result)
    }
}
