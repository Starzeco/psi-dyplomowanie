package com.example.dyplomowaniebackend

import com.example.dyplomowaniebackend.domain.model.Degree
import com.example.dyplomowaniebackend.domain.model.RealizationLanguage
import com.example.dyplomowaniebackend.domain.model.SubjectStatus
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
    val verifierRepository: VerifierRepository,
    val subjectRepository: SubjectRepository,
    val candidatureRepository: CandidatureRepository,
    val propositionAcceptanceRepository: PropositionAcceptanceRepository,
) :
    ApplicationListener<ContextRefreshedEvent> {
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        if(!studentRepository.existsById(1)) populateDB()
    }

    private fun populateDB() {

        val faculty = FacultyEntity(
            name = "Wydział infy i zarządznia",
            shortName = "WIZ"
        )

        val facultySaved = facultyRepository.save(faculty)

        val supervisor = StaffMemberEntity(
            email = "super.prowadzacy@pwr.edu.pl",
            name = "Super",
            surname = "Prowadzacy",
            title = Title.DOCTOR,
            currentWorkload = 24,
            absoluteWorkload = 180,
            facultyId = facultySaved.facultyId!!,
        )
        staffMemberRepository.save(supervisor)

        val degreeCourse = DegreeCourseEntity(
            name = "Infa",
            facultyId = facultySaved.facultyId,
        )

        val savedDegreeCourse = degreeCourseRepository.save(degreeCourse)

        val student0 = StudentEntity(  // WARN: ON MUSI BYC PEIRWSZY STWORZONY I MIEC ID 1 i indeks 242422 !!!!!!!!!!!!!!!!!!!
            index = "242422",
            email = "242422@student.pwr.edu.pl",
            name = "Marcel",
            surname = "Krakowiak",
            graduationProcesses = setOf(),
        )
        val student1 = StudentEntity(
            index = "242421",
            email = "242421@student.pwr.edu.pl",
            name = "Kacper",
            surname = "Kowalski",
            graduationProcesses = setOf(),
        )
        val student2 = StudentEntity(
            index = "242420",
            email = "242420@student.pwr.edu.pl",
            name = "Stasiek",
            surname = "Kolorowy",
            graduationProcesses = setOf(),
        )

        val graduationProcess = GraduationProcessEntity(
            cSDeadline = Instant.now(),
            vFDeadline = Instant.now(),
            cADeadline = Instant.now(),
            sPDeadline = Instant.now(),
            initialSemester = "Jakiś semssetrs 5",
            finalSemester = "Jakis semestr 6",
            degree = Degree.BATCHELOR,
            hCPerSubject = 10,
            students = setOf(student0, student1, student2),
            degreeCourseId = savedDegreeCourse.degreeCourseId!!,
        )

        graduationProcessRepository.save(graduationProcess)
        studentRepository.saveAll(setOf(student0, student1, student2))

        val verificationStaffMember0 = StaffMemberEntity(
            email = "super.weryfikator@pwr.edu.pl",
            name = "Super",
            surname = "Weryfikator",
            title = Title.DOCTOR,
            currentWorkload = 24,
            absoluteWorkload = 180,
            facultyId = facultySaved.facultyId,
        )

        val verificationStaffMember1 = StaffMemberEntity(
            email = "super.weryfikator1@pwr.edu.pl",
            name = "Super1",
            surname = "Weryfikator",
            title = Title.DOCTOR,
            currentWorkload = 24,
            absoluteWorkload = 180,
            facultyId = facultySaved.facultyId,
        )

        val verificationStaffMember2 = StaffMemberEntity(
            email = "super.weryfikator2@pwr.edu.pl",
            name = "Super2",
            surname = "Weryfikator",
            title = Title.DOCTOR,
            currentWorkload = 24,
            absoluteWorkload = 180,
            facultyId = facultySaved.facultyId,
        )
        staffMemberRepository.saveAll(listOf(verificationStaffMember0, verificationStaffMember1, verificationStaffMember2))

        val verifier0 = VerifierEntity(
            name = "Weryfikator poziomu 0",
            verificationsDeadline = Instant.now(),
            staffMemberId = verificationStaffMember0.staffMemberId!!,
            graduationProcessId = graduationProcess.graduationProcessId!!
        )

        val verifier1 = VerifierEntity(
            name = "Weryfikator poziomu 1",
            verificationsDeadline = Instant.now(),
            staffMemberId = verificationStaffMember1.staffMemberId!!,
            graduationProcessId = graduationProcess.graduationProcessId
        )

        val verifier2 = VerifierEntity(
            name = "Weryfikator poziomu 2",
            verificationsDeadline = Instant.now(),
            staffMemberId = verificationStaffMember2.staffMemberId!!,
            graduationProcessId = graduationProcess.graduationProcessId
        )

        verifierRepository.saveAll(listOf(verifier0, verifier1, verifier2))

        val verifiedSubject = SubjectEntity(
            topic = "temat1",
            topicInEnglish = "tematAngielski",
            objective = "cel",
            objectiveInEnglish = "celPoAngielsku",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 1,
            status = SubjectStatus.VERIFIED,
            staffMemberId = 1,
            graduationProcessId = 1,
            studentId = null,
            realiser = setOf()
        )
        val verifiedSubject2 = SubjectEntity(
            topic = "temat2",
            topicInEnglish = "tematAngielski2",
            objective = "cel2",
            objectiveInEnglish = "celPoAngielsku2",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 3,
            status = SubjectStatus.VERIFIED,
            staffMemberId = 2,
            graduationProcessId = 1,
            studentId = null,
            realiser = setOf()
        )
        subjectRepository.saveAll(listOf(verifiedSubject, verifiedSubject2))

        val candidature = CandidatureEntity(
            studentId = 1,
            subjectId = verifiedSubject.subjectId!!
        )

        candidatureRepository.save(candidature)

        val subjectToAccept1 = SubjectEntity(
            topic = "temat2",
            topicInEnglish = "tematAngielski2",
            objective = "cel2",
            objectiveInEnglish = "celPoAngielsku2",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 3,
            status = SubjectStatus.DRAFT,
            staffMemberId = 2,
            graduationProcessId = 1,
            studentId = 2,
            realiser = setOf()
        )

        val subjectToAccept2 = SubjectEntity(
            topic = "temat2",
            topicInEnglish = "tematAngielski2",
            objective = "cel2",
            objectiveInEnglish = "celPoAngielsku2",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 3,
            status = SubjectStatus.DRAFT,
            staffMemberId = 2,
            graduationProcessId = 1,
            studentId = 3,
            realiser = setOf()
        )

        subjectRepository.saveAll(listOf(subjectToAccept1, subjectToAccept2))

        val proposition1 = PropositionAcceptanceEntity(
            accepted = null,
            studentId = 1,
            subjectId = subjectToAccept1.subjectId!!,
        )

        val proposition2 = PropositionAcceptanceEntity(
            accepted = true,
            studentId = 3,
            subjectId = subjectToAccept1.subjectId,
        )

        val proposition3 = PropositionAcceptanceEntity(
            accepted = null,
            studentId = 1,
            subjectId = subjectToAccept2.subjectId!!,
        )

        val proposition4 = PropositionAcceptanceEntity(
            accepted = false,
            studentId = 2,
            subjectId = subjectToAccept2.subjectId,
        )

        propositionAcceptanceRepository.saveAll(listOf(proposition1, proposition2, proposition3, proposition4))
    }
}
