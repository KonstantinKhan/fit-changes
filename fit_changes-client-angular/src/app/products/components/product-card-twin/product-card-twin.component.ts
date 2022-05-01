import {Component, Input, OnInit} from '@angular/core';
import {Product} from "../../../shared/interfaces/product";

@Component({
  selector: 'app-product-card-twin',
  templateUrl: './product-card-twin.component.html',
  styleUrls: ['./product-card-twin.component.scss']
})
export class ProductCardTwinComponent implements OnInit {

  @Input() product!: Product

  constructor() {
  }

  ngOnInit(): void {
  }

}
