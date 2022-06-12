import {Injectable} from "@angular/core";
import * as moment from "moment";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DateService {
  public date: BehaviorSubject<moment.Moment> = new BehaviorSubject(moment().locale('ru'))

  changeValue(changeValue: number, isShowCalendar: boolean) {
    let value;
    if (isShowCalendar) {
      value = this.date.value.add(changeValue, 'month')
    } else {
      value = this.date.value.add(changeValue, 'day')
    }

    this.date.next(value)
  }

  changeDate(date: moment.Moment) {
    const value = this.date.value.set({
      date: date.date(),
      month: date.month()
    })
    this.date.next(value)
  }
}
