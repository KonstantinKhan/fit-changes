import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  constructor(private http: HttpClient) {
  }

  title = 'fit_changes-client-angular';

  ngOnInit() {
    this.http.get('http://localhost:8080/', {responseType: 'text'})
      .subscribe(response => {
        console.log(response)
      })
  }
}
