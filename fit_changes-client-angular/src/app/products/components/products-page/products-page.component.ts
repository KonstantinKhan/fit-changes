import {Component, OnInit} from '@angular/core';
import {Product} from "../../../shared/interfaces/product";
import {MenuItem, PrimeNGConfig} from "primeng/api";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-products-page',
  templateUrl: './products-page.component.html',
  styleUrls: ['./products-page.component.scss']
})
export class ProductsPageComponent implements OnInit {

  items: MenuItem[] = []

  products: Product[] = []

  constructor(private primengConfig: PrimeNGConfig, private http: HttpClient) {
  }

  ngOnInit(): void {
    this.primengConfig.ripple = true

    this.products = [{
      productName: "Куриное филе",
      caloriesPerHundredGrams: 110.13,
      proteinsPerHundredGrams: 231.111,
      fatsPerHundredGrams: 200.50,
      carbohydratesPerHundredGrams: 100.13
    },
      {
        productName: "Chicken",
        caloriesPerHundredGrams: 110.0,
        proteinsPerHundredGrams: 21.0,
        fatsPerHundredGrams: 3.0,
        carbohydratesPerHundredGrams: 0.0
      },
      {
        productName: "Chicken",
        caloriesPerHundredGrams: 110.0,
        proteinsPerHundredGrams: 21.0,
        fatsPerHundredGrams: 3.0,
        carbohydratesPerHundredGrams: 0.0
      }
    ]

    // this.http.post('http://localhost:8080/product/search', {
    //     messageType: "SearchProductRequest",
    //     requestId: "rID:0006",
    //     query: "",
    //     debug: {
    //       mode: "test"
    //     },
    //   },
    //   {
    //     responseType: "json"
    //   }).subscribe(products => {
    //   Object.entries(products).find(([key, value]) => {
    //     if (key === 'foundProducts') {
    //       this.products = value as Product[]
    //       this.products.forEach(product => {
    //         console.log(product.productName)
    //       })
    //     }
    //   })
    // })

    this.items = [
      {
        label: 'Edit'
      },
      {
        label: 'delete'
      }
    ]
  }
}
