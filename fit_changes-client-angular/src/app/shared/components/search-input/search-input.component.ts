import {Component, EventEmitter, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {Product} from "../../interfaces/product";

@Component({
  selector: 'app-search-input',
  templateUrl: './search-input.component.html',
  styleUrls: ['./search-input.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class SearchInputComponent implements OnInit {

  @Output() queryEvent = new EventEmitter<string>()

  query: string = "";

  products: Product[] = []

  constructor() {
  }

  ngOnInit() {
  }

  searchProduct() {
    this.queryEvent.emit(this.query)
  }
}
