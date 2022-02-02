import { Component, Input } from '@angular/core';
import { Subject } from 'src/app/shared/model';

export type Content = {
  polish: string,
  english: string
}

export function topicContent(subject: Subject): Content {
  return ({
    polish: subject.topic,
    english: subject.topicInEnglish
  })
}


export function objectvieContent(subject: Subject): Content {
  return ({
    polish: subject.objective,
    english: subject.objectiveInEnglish
  })
}

@Component({
  selector: 'app-topic-objective',
  templateUrl: './topic-objective.component.html',
  styleUrls: ['./topic-objective.component.scss']
})
export class TopicObjectiveComponent {

  @Input() topic!: Content
  @Input() objective!: Content
  @Input() realizationLanguage!: string
}
