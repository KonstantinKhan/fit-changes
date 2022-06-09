import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Product} from "../../../shared/interfaces/product";
import {ProductService} from "../../../services/product.service";
import {delay, Subject, Subscription, takeUntil} from "rxjs";
import {DynamicModalLoader} from "../../../shared/directives/load-modal.directive";
import {
  ModalCreateProductComponent
} from "../../../shared/components/modal-create-product/modal-create-product.component";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-products-page',
  templateUrl: './products-page.component.html',
  styleUrls: ['./products-page.component.scss'],
  providers: [MessageService]
})
export class ProductsPageComponent implements OnInit, OnDestroy {

  @ViewChild(DynamicModalLoader) dynamicModal!: DynamicModalLoader

  modalDestroy$: Subject<boolean> = new Subject<boolean>()

  products: Product[] = []
  unSubs: Subscription[] = []
  modalSubscriptions: Subscription[] = [];

  constructor(
    private productService: ProductService,
    private messageService: MessageService
  ) {
  }

  ngOnInit(): void {
    this.unSubs.push(this.productService.searchProducts().subscribe(products => this.products = products))
  }

  showModalCreateProduct(product: Product = {
    productName: "",
    caloriesPerHundredGrams: 0,
    proteinsPerHundredGrams: 0,
    fatsPerHundredGrams: 0,
    carbohydratesPerHundredGrams: 0
  }) {
    const component = this.dynamicModal.viewContainerRef.createComponent(ModalCreateProductComponent)
    component.instance.product = product
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

  deleteProduct(productId: string) {
    this.productService.deleteProduct(productId).subscribe({
      next: (value) => {
        this.productService.searchProducts().subscribe(products => {
          this.products = products
        })
      },
      error: (err) => {
        console.log(err)
        this.messageService.add({key: 'bc', severity: 'error', summary: 'Error', detail: err.error.errors[0].message})
        // todo Добавить уведомление о запрете на удаление публичных записей.
      }
    })
  }

  editProduct(product: Product) {
    this.showModalCreateProduct(product)
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

  searchProduct(query: string) {
    this.unSubs.push(this.productService.searchProducts(query).subscribe(products => this.products = products))
  }
}
