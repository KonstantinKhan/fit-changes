import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Product} from "../../../shared/interfaces/product";
import {ProductService} from "../../../services/product.service";
import {delay, Subject, Subscription, takeUntil} from "rxjs";
import {DynamicModalLoader} from "../../../shared/directives/load-modal.directive";
import {
  ModalCreateProductComponent
} from "../../../shared/components/modal-create-product/modal-create-product.component";

@Component({
  selector: 'app-products-page',
  templateUrl: './products-page.component.html',
  styleUrls: ['./products-page.component.scss']
})
export class ProductsPageComponent implements OnInit, OnDestroy {

  @ViewChild(DynamicModalLoader) dynamicModal!: DynamicModalLoader

  modalDestroy$: Subject<boolean> = new Subject<boolean>()

  products: Product[] = []
  unSubs: Subscription[] = []
  modalSubscriptions: Subscription[] = [];

  constructor(
    private productService: ProductService
  ) {
  }

  ngOnInit(): void {

    this.unSubs.push(this.productService.searchProducts().subscribe(products => this.products = products))

    // this.products = [{
    //   productName: "Куриное филе",
    //   caloriesPerHundredGrams: 110.13,
    //   proteinsPerHundredGrams: 231.111,
    //   fatsPerHundredGrams: 200.50,
    //   carbohydratesPerHundredGrams: 100.13
    // },
    //   {
    //     productName: "Chicken",
    //     caloriesPerHundredGrams: 110.0,
    //     proteinsPerHundredGrams: 21.0,
    //     fatsPerHundredGrams: 3.0,
    //     carbohydratesPerHundredGrams: 0.0
    //   },
    //   {
    //     productName: "Chicken",
    //     caloriesPerHundredGrams: 110.0,
    //     proteinsPerHundredGrams: 21.0,
    //     fatsPerHundredGrams: 3.0,
    //     carbohydratesPerHundredGrams: 0.0
    //   }
    // ]

  }

  showModalCreateProduct() {
    const component = this.dynamicModal.viewContainerRef.createComponent(ModalCreateProductComponent)
    this.modalSubscriptions.push(component.instance.close

      // delay(250) добавлена, чтобы отображалась анимация перед закрытием модального окна.
      // todo: подумать, как привязаться к окончанию анимации кнопки.
      .pipe(
        delay(250),
        takeUntil(this.modalDestroy$)
      )

      .subscribe(() => {
          this.dynamicModal.viewContainerRef.clear()
          this.modalDestroy()
        }
      ))

    this.modalSubscriptions.push(component.instance.submitEvent

      // delay(250) добавлена, чтобы отображалась анимация перед закрытием модального окна.
      // todo: подумать, как привязаться к окончанию анимации кнопки.
      .pipe(
        delay(250),
        takeUntil(this.modalDestroy$)
      )

      .subscribe(() => {
        this.unSubs.push(this.productService.createProduct(component.instance.product)
          .subscribe(() => {
              this.unSubs.push(this.productService.searchProducts()
                .subscribe(products => {
                  this.products = products
                  this.dynamicModal.viewContainerRef.clear()
                  this.modalDestroy()
                }))
            }
          ))
      }))
  }


  addProduct() {
  }

  deleteProduct(productId: string) {
    this.productService.deleteProduct(productId).subscribe(() => {
      this.productService.searchProducts().subscribe(products => {
        this.products = products
      })
    })
  }

  editProduct() {

  }

  private modalUnsub() {
    this.modalSubscriptions.forEach(sub => {
      if (!sub.closed) {
        sub.unsubscribe()
      }
    })
  }

  private modalDestroy() {
    this.modalDestroy$.next(true)
    this.modalDestroy$.complete()
  }

  ngOnDestroy(): void {
    this.unSubs.forEach(sub => {
      if (!sub.closed) {
        sub.unsubscribe()
        console.log("Unsubscribed", sub)
      }
    })
    this.modalSubscriptions.forEach(sub => {
      if (!sub.closed) {
        console.log(sub)
      }
    })
  }
}
