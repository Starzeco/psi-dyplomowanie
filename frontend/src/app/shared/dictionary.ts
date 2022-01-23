interface Dictionary<T> {
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

export const SUBJECT_STATUS: Dictionary<string> = {
  "DRAFT": "Draft",
  "ACCEPTED_BY_SUPERVISOR": "Accepted by supervisor",
  "ACCEPTED_BY_INITIATOR": "Accepted by initiator",
  "IN_VERIFICATION": "In verification",
  "IN_CORRECTION": "In correction",
  "VERIFIED": "Verified",
  "REJECTED": "Rejected",
  "RESERVED": "Reserved"
}

export const SUBJECT_STATUS_POLISH: Dictionary<string> = {
  "DRAFT": "Roboczy",
  "ACCEPTED_BY_SUPERVISOR": "Zaakceptowane przez promotora",
  "ACCEPTED_BY_INITIATOR": "Zaakceptowane przez inicjatora",
  "IN_VERIFICATION": "W weryfikacji",
  "IN_CORRECTION": "W poprawie",
  "VERIFIED": "Zweryfikowany",
  "REJECTED": "Odrzucony",
  "RESERVED": "Zarezerwowany"
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
