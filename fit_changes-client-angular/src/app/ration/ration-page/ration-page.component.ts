import {Component, OnInit, ViewChild} from '@angular/core';
import {DynamicModalLoader} from "../../shared/directives/load-modal.directive";
import {ModalCreateProductComponent} from "../../shared/components/modal-create-product/modal-create-product.component";
import {Subscription} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Product} from "../../shared/interfaces/product";

@Component({
  selector: 'app-ration-page',
  templateUrl: './ration-page.component.html',
  styleUrls: ['./ration-page.component.scss']
})
export class RationPageComponent implements OnInit {

  products: Product[] = [];

  @ViewChild(DynamicModalLoader) dynamicModal!: DynamicModalLoader

  subscriptions: Subscription[] = [];

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    // this.showModal()
    this.http.post('http://localhost:8080/product/search', {
        messageType: "SearchProductRequest",
        requestId: "rID:0006",
        query: "",
        debug: {
          mode: "test"
        },
      },
      {
        responseType: "json"
      }).subscribe(products => {
      Object.entries(products).find(([key, value]) => {
        if (key === 'foundProducts') {
          this.products = value as Product[]
          this.products.forEach(product => {
            console.log(product.productName)
          })
        }
      })
    })
  }

  showModal() {
    const component = this.dynamicModal.viewContainerRef.createComponent(ModalCreateProductComponent)
    component.instance.close.subscribe(() => {

      this.http.post('http://localhost:8080/product/search', {
          messageType: "SearchProductRequest",
          requestId: "rID:0006",
          query: "",
          debug: {
            mode: "test"
          },
        },
        {
          responseType: "json"
        }).subscribe(products => {
        Object.entries(products).find(([key, value]) => {
          if (key === 'foundProducts') {
            this.products = value as Product[]
            this.products.forEach(product => {
              console.log(product.productName)
            })
          }
        })
      })


      this.dynamicModal.viewContainerRef.clear()
      // component.instance.close.unsubscribe()
    })
  }

  testClick() {
    this.http.get('http://localhost:8080', {responseType: 'text'}).subscribe(text => {
      console.log(text)
    })
    this.http.post('http://localhost:8080/product/create',
      {},
    ).subscribe(response => console.log(response))
  }
}
