import {Pipe, PipeTransform} from "@angular/core";
import * as moment from "moment";

@Pipe({
  name: 'moment',
  pure: false
})
export class MomentPipe implements PipeTransform {

  constructor() {
    moment.locale('ru')
  }

  transform(m: moment.Moment | null, format: string = 'DD MMM YYYY'): string | undefined {
    return m?.format(format)
  }
}
