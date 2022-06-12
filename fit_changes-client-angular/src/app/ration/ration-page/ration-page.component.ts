import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {Product} from "../../shared/interfaces/product";

@Component({
  selector: 'app-ration-page',
  templateUrl: './ration-page.component.html',
  styleUrls: ['./ration-page.component.scss']
})
export class RationPageComponent implements OnInit {
  products: Product[] = [];
  subscriptions: Subscription[] = [];

  constructor() {
  }

  ngOnInit() {

  }
}
