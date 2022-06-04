import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Product} from "../../../shared/interfaces/product";

@Component({
  selector: 'app-product-card-twin',
  templateUrl: './product-card-twin.component.html',
  styleUrls: ['./product-card-twin.component.scss']
})
export class ProductCardTwinComponent implements OnInit {

  @Input() product!: Product
  @Output() deleteProductEvent = new EventEmitter<string>()
  @Output() editProductEvent = new EventEmitter<Product>()

  constructor() {
  }

  ngOnInit(): void {
  }

  deleteProduct() {
    this.deleteProductEvent.emit(this.product.productId)
  }

  updateProduct() {
    this.editProductEvent.emit(this.product)
  }
}
