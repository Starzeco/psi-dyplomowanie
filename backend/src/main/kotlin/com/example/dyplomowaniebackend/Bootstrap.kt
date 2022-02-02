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
    val candidatureAcceptanceRepository: CandidatureAcceptanceRepository,
    val verificationRepository: VerificationRepository
) :
    ApplicationListener<ContextRefreshedEvent> {
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        if(!studentRepository.existsById(1)) populateDB()
    }

    private fun populateDB() {

        val faculty = FacultyEntity(
            name = "Wydział Informatyki i Zarządznia",
            shortName = "WIZ"
        )

        val facultySaved = facultyRepository.save(faculty)

        val supervisor = StaffMemberEntity(
            email = "marcin.nowak@pwr.edu.pl",
            name = "Marcin",
            surname = "Nowak",
            title = Title.DOCTOR,
            currentWorkload = 24,
            absoluteWorkload = 180,
            facultyId = facultySaved.facultyId!!,
        )
        staffMemberRepository.save(supervisor)

        val degreeCourse = DegreeCourseEntity(
            name = "Informatyka",
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
        val student3 = StudentEntity(
            index = "242423",
            email = "242423@student.pwr.edu.pl",
            name = "Grzegorz",
            surname = "Konfucjusz",
            graduationProcesses = setOf(),
        )
        val student4 = StudentEntity(
            index = "242424",
            email = "242424@student.pwr.edu.pl",
            name = "Katarzyna",
            surname = "Osa",
            graduationProcesses = setOf(),
        )
        val student5 = StudentEntity(
            index = "242425",
            email = "242425@student.pwr.edu.pl",
            name = "Joanna",
            surname = "Kaplica",
            graduationProcesses = setOf(),
        )

        val graduationProcess = GraduationProcessEntity(
            cSDeadline = Instant.now(),
            vFDeadline = Instant.now(),
            cADeadline = Instant.now(),
            sPDeadline = Instant.now(),
            initialSemester = "Semestr 6",
            finalSemester = "Semestr 7",
            degree = Degree.BATCHELOR,
            hCPerSubject = 10,
            students = setOf(student0, student1, student2, student3, student4, student5),
            degreeCourseId = savedDegreeCourse.degreeCourseId!!,
        )

        graduationProcessRepository.save(graduationProcess)
        studentRepository.saveAll(setOf(student0, student1, student2, student3, student4, student5))

        val verificationStaffMember0 = StaffMemberEntity(
            email = "krzysztof.kasprowicz@pwr.edu.pl",
            name = "Krzysztof",
            surname = "Kasprowicz",
            title = Title.DOCTOR,
            currentWorkload = 24,
            absoluteWorkload = 180,
            facultyId = facultySaved.facultyId,
        )

        val verificationStaffMember1 = StaffMemberEntity(
            email = "zuzanna.agrestowa@pwr.edu.pl",
            name = "Zuzanna",
            surname = "Agrestowa",
            title = Title.DOCTOR,
            currentWorkload = 24,
            absoluteWorkload = 180,
            facultyId = facultySaved.facultyId,
        )

        val verificationStaffMember2 = StaffMemberEntity(
            email = "barbara.tukiel@pwr.edu.pl",
            name = "Barbara",
            surname = "Tukiel",
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

        // TUTAJ ZACZYNA SIĘ TWORZENIE OBIEKTÓW POD POKAZ

        // JAKO student chce pokazac
        // ze moge odrzucic propozycje akceptacji tematu
        // ze moge zaakceptowac propozycje akceptacji tematu (i wtedy trafia do akceptacji przez promotora)
        // ze moge stworzyc temat grupowy i temat indywidualny ---
        // jak stworze temat grupowy to oczekuje na akceptacje innych studentow --- zakceptowac po kolei z API (jeden niech odrzuci)
        // powtorzyc to co wyzej tylko teraz niech wszyscy zaakceptują
        // pokazac ze student moze akceptowac lub odrzucac kandydatury stworzone przez innych studentow

        val subjectToDeclineProposition = SubjectEntity(
            topic = "Temat do odrzucenia",
            topicInEnglish = "Subject to decline",
            objective = "Przykładowy cel",
            objectiveInEnglish = "Example objective",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 3,
            status = SubjectStatus.DRAFT,
            staffMemberId = 1,
            graduationProcessId = 1,
            studentId = 2,
            realiser = setOf()
        )
        subjectRepository.save(subjectToDeclineProposition)

        val proposition1 = PropositionAcceptanceEntity(
            accepted = null,
            studentId = 1,
            subjectId = subjectToDeclineProposition.subjectId!!,
        )

        val proposition2 = PropositionAcceptanceEntity(
            accepted = null,
            studentId = 3,
            subjectId = subjectToDeclineProposition.subjectId,
        )

        propositionAcceptanceRepository.saveAll(listOf(proposition1, proposition2))

        val subjectToAcceptProposition = SubjectEntity(
            topic = "Temat do akceptacji",
            topicInEnglish = "Subject to accept",
            objective = "Przykładowy cel",
            objectiveInEnglish = "Example objective",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 3,
            status = SubjectStatus.DRAFT,
            staffMemberId = 1,
            graduationProcessId = 1,
            studentId = 2,
            realiser = setOf()
        )
        subjectRepository.save(subjectToAcceptProposition)

        val proposition3 = PropositionAcceptanceEntity(
            accepted = null,
            studentId = 1,
            subjectId = subjectToAcceptProposition.subjectId!!,
        )

        val proposition4 = PropositionAcceptanceEntity(
            accepted = null,
            studentId = 3,
            subjectId = subjectToAcceptProposition.subjectId,
        )

        propositionAcceptanceRepository.saveAll(listOf(proposition3, proposition4))


        // AVAILABLE SUBJECTS

        val supervisor2Subject = SubjectEntity(
            topic = "Temat do akceptacji kandydatury",
            topicInEnglish = "Subject to accept candidature",
            objective = "Przykładowy cel",
            objectiveInEnglish = "Example objective",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 2,
            status = SubjectStatus.VERIFIED,
            staffMemberId = 2,
            graduationProcessId = 1,
            studentId = null,
            realiser = setOf()
        )
        subjectRepository.save(supervisor2Subject)

        val candidature0 = CandidatureEntity(
            studentId = 2,
            subjectId = supervisor2Subject.subjectId!!,
            candidatureAcceptances = setOf()
        )
        candidatureRepository.save(candidature0)

        val candidatureAcceptance0 = CandidatureAcceptanceEntity(
            accepted = null,
            studentId = 1,
            candidatureId = candidature0.candidatureId!!
        )
        candidatureAcceptanceRepository.save(candidatureAcceptance0)

        val supervisor3Subject = SubjectEntity(
            topic = "Temat do odrzucenia kandydatury",
            topicInEnglish = "Subject to decline candidature",
            objective = "Przykładowy cel",
            objectiveInEnglish = "Example objective",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 2,
            status = SubjectStatus.VERIFIED,
            staffMemberId = 3,
            graduationProcessId = 1,
            studentId = null,
            realiser = setOf()
        )
        subjectRepository.save(supervisor3Subject)

        val candidature1 = CandidatureEntity(
            studentId = 3,
            subjectId = supervisor3Subject.subjectId!!,
            candidatureAcceptances = setOf()
        )
        candidatureRepository.save(candidature1)

        val candidatureAcceptance1 = CandidatureAcceptanceEntity(
            accepted = null,
            studentId = 1,
            candidatureId = candidature1.candidatureId!!
        )
        candidatureAcceptanceRepository.save(candidatureAcceptance1)

        val dostepnySubject1 = SubjectEntity(
            topic = "Temat dostepny grupowy",
            topicInEnglish = "Group subject available",
            objective = "Przykładowy cel",
            objectiveInEnglish = "Example objective",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 3,
            status = SubjectStatus.VERIFIED,
            staffMemberId = 1,
            graduationProcessId = 1,
            studentId = null,
            realiser = setOf()
        )

        val dostepnySubject2 = SubjectEntity(
            topic = "Temat dostepny indywidualny",
            topicInEnglish = "Individual subject available",
            objective = "Przykładowy cel",
            objectiveInEnglish = "Example objective",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 1,
            status = SubjectStatus.VERIFIED,
            staffMemberId = 1,
            graduationProcessId = 1,
            studentId = null,
            realiser = setOf()
        )
        subjectRepository.saveAll(listOf(dostepnySubject1, dostepnySubject2))

        val acceptedSubject = SubjectEntity(
            topic = "Temat zakceptowany w weryfikacji",
            topicInEnglish = "Subject accepted in verification",
            objective = "Przykładowy cel",
            objectiveInEnglish = "Example objective",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 1,
            status = SubjectStatus.IN_VERIFICATION,
            staffMemberId = 2,
            graduationProcessId = 1,
            studentId = null,
            realiser = setOf()
        )
        subjectRepository.save(acceptedSubject)

        val acceptedVerificationEntity = VerificationEntity(
            subjectId = acceptedSubject.subjectId!!,
            verifierId = verifier0.verifierId!!,
            verified = true,
            justification = "Wszystko sie zgadza",
            updateDate = Instant.now(),
        )

        val inCorrectionSubject = SubjectEntity(
            topic = "Temat zakceptowany w weryfikacji",
            topicInEnglish = "Subject accepted in verification",
            objective = "Przykładowy cel",
            objectiveInEnglish = "Example objective",
            realizationLanguage = RealizationLanguage.POLISH,
            realiseresNumber = 1,
            status = SubjectStatus.IN_CORRECTION,
            staffMemberId = 2,
            graduationProcessId = 1,
            studentId = null,
            realiser = setOf()
        )
        subjectRepository.save(inCorrectionSubject)

        val rejectedVerificationEntity = VerificationEntity(
            subjectId = inCorrectionSubject.subjectId!!,
            verifierId = verifier0.verifierId,
            verified = false,
            justification = "Prosze poprawic tytul w wersji angielskiej",
            updateDate = Instant.now(),
        )
        verificationRepository.save(acceptedVerificationEntity)
        verificationRepository.save(rejectedVerificationEntity)
    }
}
