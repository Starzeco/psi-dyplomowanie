import { Candidature, CandidaturePartialInfo, Status, Subject } from 'src/app/shared/model';

export function getSubjectStatusTranslation(subject: Subject): string {
    const status = subject.status;
    if (status == Status.DRAFT) {
        if (subject.realiseresNumber == 1) {
            return 'draft_waiting';
        } else if (subject.realiseresNumber > 1) {
            const acceptances = subject.propositionAcceptances;
            const anyRejection = acceptances.find(a => a.accepted === false);
            if (anyRejection) {
                return 'rejected_status';
            }
            const allAccepted = acceptances.every(a => a.accepted);
            if (allAccepted) {
                return 'draft_waiting';
            } else if (subject.initiator?.studentId == 1) {
                return 'draft_waiting_co-realisers';
            } else {
                return 'draft_waiting_for_you';
            }
        } else return 'wtf';
    } else if (status == Status.ACCEPTED_BY_SUPERVISOR) {
        return 'accepted_by_supervisor_status';
    } else if (status == Status.ACCEPTED_BY_INITIATOR) {
        return 'accepted_by_initiator_status';
    } else if (status == Status.IN_VERIFICATION) {
        return 'in_verification_status';
    } else if (status == Status.IN_CORRECTION) {
        return 'in_correction_status';
    } else if (status == Status.VERIFIED) {
        return 'verified_status';
    } else if (status == Status.REJECTED) {
        return 'rejected_status';
    } else if (status == Status.RESERVED) {
        return 'reserved_status';
    } else return 'Unknown';
}


export function getCandidaturePartialInfoStatusTranslation(candidature: CandidaturePartialInfo): string {
    switch (candidature.status) {
        case 'TO_ACCEPT_BY_STUDENTS':
            return 'to_accept_by_students';
        case 'TO_ACCEPT_BY_SUPERVISOR':
            return 'to_accept_by_supervisor';
        case 'ACCEPTED':
            return 'accepted';
        case 'REJECTED':
            return 'rejected';
        default:
            return ''
    }
}

export function getCandidatureStatusTranslation(candidature: Candidature): string {
    switch (candidature.accepted) {
        case true:
            return 'accepted';
        case false:
            return 'rejected';
        default:
            if (candidature.candidatureAcceptances.every(ca => ca.accepted === true)) return 'to_accept_by_supervisor'
            else if (candidature.candidatureAcceptances.every(ca => ca.accepted === false)) return 'rejected'
            else return 'to_accept_by_students'
    }
}