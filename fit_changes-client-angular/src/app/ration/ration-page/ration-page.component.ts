import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {Product} from "../../shared/interfaces/product";
import {RationService} from "../../services/ration.service";

@Component({
  selector: 'app-ration-page',
  templateUrl: './ration-page.component.html',
  styleUrls: ['./ration-page.component.scss']
})
export class RationPageComponent implements OnInit {
  products: Product[] = [];
  subscriptions: Subscription[] = [];

  constructor(private rationService: RationService) {
  }

  ngOnInit() {
    this.rationService.searchProducts('2022-06-12T08:00:00.0Z').subscribe(rations => console.log(rations))
  }
}
