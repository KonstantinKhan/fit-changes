import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Product} from "../../../shared/interfaces/product";
import {ProductService} from "../../../services/product.service";

@Component({
  selector: 'app-product-card-twin',
  templateUrl: './product-card-twin.component.html',
  styleUrls: ['./product-card-twin.component.scss']
})
export class ProductCardTwinComponent implements OnInit {

  @Input() product!: Product
  @Output() deleteEvent = new EventEmitter<string>()

  constructor() {
  }

  ngOnInit(): void {
  }

  deleteProduct() {
    this.deleteEvent.emit(this.product.productId)
  }
}
