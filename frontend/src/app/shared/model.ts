export type Subject = {
  subjectId: number,
  topic: string,
  topicInEnglish: string,
  objective: string,
  objectiveInEnglish: string,
  realizationLanguage: string,
  realiseresNumber: number,
  accepted: boolean,
  initiator: Student | null,
  status: Status,
  creationDate: Date,
  supervisor: StaffMember,
  propositionAcceptances: PropositionAcceptance[],
  realiser: Student[]
}

export enum Status {
  DRAFT = "DRAFT",
  ACCEPTED_BY_SUPERVISOR = "ACCEPTED_BY_SUPERVISOR",
  ACCEPTED_BY_INITIATOR = "ACCEPTED_BY_INITIATOR",
  IN_VERIFICATION = "IN_VERIFICATION",
  IN_CORRECTION = "IN_CORRECTION",
  VERIFIED = "VERIFIED",
  REJECTED = "REJECTED",
  RESERVED = "RESERVED"
}

export type PropositionAcceptance = {
  propositionAcceptanceId: number | null,
  accepted: boolean | null,
  student: Student,
  subject: Subject,
}

export enum SubjectType {
  INDIVIDUAL = 'INDIVIDUAL',
  GROUP = 'GROUP'
}

export type Verification = {
  verificationId: number,
  verified: boolean | null,
  justification: string | null,
  updateDate: string | null,
  subject: Subject,
  verifier: StaffMember
}


export type Candidature = {
  candidatureId: number,
  accepted: boolean | null,
  student: Student,
  subject: Subject,
  creationDate: Date
  candidatureAcceptances: CandidatureAcceptance[]
}

export type CandidatureAcceptance = {
  candidatureAcceptanceId: number,
  accepted: boolean | null,
  student: Student
}

export type CandidaturePartialInfo = {
  subjectId: number,
  candidatureId: number,
  subjectTopic: string,
  subjectTopicEnglish: string,
  supervisorName: string,
  type: string,
  status: string
}

export type StaffMember = {
  staffMemberId: number,
  email: string,
  name: string,
  surname: string,
  title: string,
  currentWorkload: number,
  absoluteWorkload: number,
  fullName: string
}

export type Student = {
  studentId: number,
  index: string,
  email: string,
  name: string,
  surname: string,
}

export type UserType = "student" | "supervisor" | "verifier"

export type User = {
  userId: number
  type: UserType
  userFullName: string,
}

export type GraduationProcess = {
  graduationProcessId: number,
  initialSemesterName: string,
  finalSemesterName: string,
  facultyShortName: string,
  degreeCourseNameKey: string,
  degreeNameKey: string,
}

export type VerificationDecision = {
  decision: boolean,
  justification: string,
}

enum Degree {
  MASTER = 'MASTER',
  BATCHELOR = 'BATCHELOR'
}

export type Verifier = {
  verifierId: number,
  name: string,
  verificationsDeadline: Date,
}

export type VerifierPartialInfo = {
  verifierId: number,
  name: string,
  verificationsDeadline: Date,
  graduationProcessPartialInfo: GraduationProcessPartialInfo
}

export type GraduationProcessPartialInfo = {
  graduationProcessId: number,
  cSDeadline: Date, //candidatures submitting deadline
  vFDeadline: Date, //verification forwarding
  cADeadline: Date, //candidatures approving deadline
  sPDeadline: Date, //subjects publication deadline
  initialSemester: string,
  finalSemester: string,
  degree: Degree,
  degreeCourseName: string,
  facultyName: string,
  facultyShortName: string,
}