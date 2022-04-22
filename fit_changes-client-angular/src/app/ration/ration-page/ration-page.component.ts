import {Component, OnInit, ViewChild} from '@angular/core';
import {DynamicModalLoader} from "../../shared/directives/load-modal.directive";
import {ModalCreateProductComponent} from "../../shared/components/modal-create-product/modal-create-product.component";
import {Subscription} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-ration-page',
  templateUrl: './ration-page.component.html',
  styleUrls: ['./ration-page.component.scss']
})
export class RationPageComponent implements OnInit {

  products: string[] = [];

  @ViewChild(DynamicModalLoader) dynamicModal!: DynamicModalLoader

  subscriptions: Subscription[] = [];

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    // this.showModal()
  }

  showModal() {
    const component = this.dynamicModal.viewContainerRef.createComponent(ModalCreateProductComponent)
    component.instance.close.subscribe(() => {
      console.log(component.instance.product.productName)
      if (component.instance.product.productName != null) {
        this.products.push(component.instance.product.productName)
      }
      console.log(this.products)
      this.dynamicModal.viewContainerRef.clear()
      // component.instance.close.unsubscribe()
    })
  }

  testClick() {
    this.http.get('http://localhost:8080', {responseType: 'text'}).subscribe(text => {
      console.log(text)
    })
    this.http.post('http://localhost:8080/product/create',
      {
      },
    ).subscribe(response => console.log(response))
  }
}
