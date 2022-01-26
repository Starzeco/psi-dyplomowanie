export type Subject = {
  subjectId: number,
  topic: string,
  topicInEnglish: string,
  objective: string,
  objectiveInEnglish: string,
  realizationLanguage: string,
  realiseresNumber: number,
  accepted: boolean,
  status: string,
  creationDate: Date,
  supervisor: StaffMember
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


export type CandidaturePartialInfo = {
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

