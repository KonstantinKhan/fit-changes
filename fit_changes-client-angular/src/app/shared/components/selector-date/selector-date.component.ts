import {Component, OnInit} from '@angular/core';
import {DateService} from "../../../services/date.service";
import {BehaviorSubject} from "rxjs";
import * as moment from "moment";

@Component({
  selector: 'app-selector-date',
  templateUrl: './selector-date.component.html',
  styleUrls: ['./selector-date.component.scss']
})
export class SelectorDateComponent implements OnInit {

  date: BehaviorSubject<moment.Moment>
  isShowCalendar: boolean = false

  constructor(private dateService: DateService) {
    this.date = this.dateService.date
  }

  ngOnInit(): void {
  }

  previousValue() {
    this.dateService.changeValue(-1, this.isShowCalendar)
  }

  nextValue() {
    this.dateService.changeValue(1, this.isShowCalendar)
  }

  showCalendar() {
    this.isShowCalendar = !this.isShowCalendar
  }
}
