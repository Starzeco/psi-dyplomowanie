export interface Dictionary<T> {
  [Key: string]: T;
}

export const SUBJECT_TYPE: Dictionary<string> = {
  "GROUP": "Group",
  "INDIVIDUAL": "Individual"
}

export const SUBJECT_TYPE_POLISH: Dictionary<string> = {
  "GROUP": "Grupowy",
  "INDIVIDUAL": "Indywidualny"
}

export const CANDIDATURE_STATUS: Dictionary<string> = {
  "TO_ACCEPT_BY_STUDENTS": "To be accepted by co-realisers",
  "TO_ACCEPT_BY_SUPERVISOR": "To be accepted by supervisor",
  "ACCEPTED": "Accepted",
  "REJECTED": "Rejected",
}

export const CANDIDATURE_STATUS_POLISH: Dictionary<string> = {
  "TO_ACCEPT_BY_STUDENTS": "Do zaakcpetowania przez współrealizatorów",
  "TO_ACCEPT_BY_SUPERVISOR": "Do zaakcpetowania przez promotora",
  "ACCEPTED": "Zaakceptowana",
  "REJECTED": "Odrzucona",
}

export const TITLE_TRANSLATION: Dictionary<string> = {
  "DOCTOR": "dr",
  "PROFESSOR": "prof.",
  "MASTER": "mgr",
}
