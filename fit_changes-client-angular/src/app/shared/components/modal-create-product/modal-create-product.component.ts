import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Product} from "../../interfaces/product";
import {map, Subscription} from "rxjs";
import {ProductValidators} from "../../validators/productValidators";
import {HttpClient} from "@angular/common/http";


@Component({
  selector: 'app-modal-create-product',
  templateUrl: './modal-create-product.component.html',
  styleUrls: ['./modal-create-product.component.scss']
})
export class ModalCreateProductComponent implements OnInit, OnDestroy {

  form!: FormGroup
  product: Product = {
    productName: "",
    caloriesPerHundredGrams: 0,
    proteinsPerHundredGrams: 0,
    fatsPerHundredGrams: 0,
    carbohydratesPerHundredGrams: 0
  }

  private subscriptions: Subscription[] = []

  @Output() close = new EventEmitter<void>();

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.form = new FormGroup(
      {
        productName: new FormControl(''),
        productCalories: new FormControl('', [
          Validators.required,
          ProductValidators.maxValue(900),
        ]),
        productProteins: new FormControl('', [
          Validators.required,
          ProductValidators.maxValue(100),
        ]),
        productFats: new FormControl('', [
          Validators.required,
          ProductValidators.maxValue(100),
        ]),
        productCarbohydrates: new FormControl('', [
          Validators.required,
          ProductValidators.maxValue(100),
        ])
      },
      [ProductValidators.calculating(this.product, 0.05)]
    )

    Object.keys(this.form.controls).forEach(key => {
      if (key != 'productName') {
        this.subscriptions.push(this.form.get(key)?.valueChanges
          .pipe(
            map(
              value => {
                let productValue: number = ModalCreateProductComponent.getProductValue(key, this.product)
                value = ModalCreateProductComponent.removeExtraZero(value as string)
                value = ModalCreateProductComponent.replacedSymbols(value as string)
                value = ModalCreateProductComponent.handleCommaBeginning(value as string)
                value = ModalCreateProductComponent.formattedDecimal(value as string, 1)
                value = ModalCreateProductComponent.controlMax(value as string, this.form.get(key) as FormControl, productValue)
                return value
              }
            )
          )
          .subscribe(value => {
            this.form.get(key)?.setValue(value, {emitEvent: false})

            switch (key) {
              case 'productCalories':
                this.product.caloriesPerHundredGrams = +value.replace(',', '.')
                break;
              case 'productProteins':
                this.product.proteinsPerHundredGrams = +value.replace(',', '.')
                break;
              case 'productFats':
                this.product.fatsPerHundredGrams = +value.replace(',', '.')
                break;
              case 'productCarbohydrates':
                this.product.carbohydratesPerHundredGrams = +value.replace(',', '.')
                break;
            }
          }) as Subscription)
      } else {
        this.subscriptions.push(this.form.get(key)?.valueChanges
          .subscribe(value => {
            this.product.productName = value
          }) as Subscription
        )
      }
    })
  }

  submit() {
    this.close.emit()
    const formData = {...this.form.value}
    this.http.post('http://localhost:8080/product/create',
      {
        messageType: "CreateProductRequest",
        requestId: "id:0001",
        createProduct: this.product,
        debug: {
          mode: "stub",
          stubCase: "success"
        }
      }
      , {
        responseType: 'text'
      })
      .subscribe(value => console.log(value))
    console.log(this.product)
  }

  focusout(val: string, field: string) {
    let decimals = val.split(',')
    if (decimals.length > 1) {
      val = val + '0'
    }

    if (!val.includes(',')) {
      if (val.length == 0) {
        val = val + '0,0'
      } else {
        val = val + ',0'
      }
    }
    this.form.get(field)?.setValue(val)
  }

  private static removeExtraZero(value: string): string {
    if (value.length == 2 && value.substring(0, 1) == '0') {
      if (value.substring(1, 2) != ',') {
        return value.substring(1)
      }
    }
    return value;
  }

  private static handleCommaBeginning(value: string): string {
    if (value.length == 1 && value.includes(',')) {
      console.log('exist comma')
      return '0' + value
    }
    return value
  }

  private static formattedDecimal(value: string, symbols: number): string {
    let decimals = value.split(',')
    if (decimals.length > 1) {
      if (decimals[1].length > symbols) {
        return value.slice(0, -1)
      }
    }
    return value;
  }

  private static controlMax(value: string, control: FormControl, productValue: number): string {
    if (control.errors != null) {
      Object.keys(control.errors).forEach((error) => {
        if (error === 'maxValue') {
          const decimals = value.split(',')

          if (decimals.length > 1) {
            if (decimals[1].length == 0) {
              value = productValue.toString() + ','
            } else {
              if (!productValue.toString().includes('.')) {
                value = productValue.toString() + ',0'
              } else {
                value = productValue.toString().replace('.', ',')
              }
            }
          } else {
            value = productValue.toString().replace('.', ',')
          }
        }
      })
    }
    return value;
  }

  private static getProductValue(key: string, product: Product): number {
    switch (key) {
      case 'productCalories':
        return product.caloriesPerHundredGrams
      case 'productProteins':
        return product.proteinsPerHundredGrams
      case 'productFats':
        return product.fatsPerHundredGrams
      case 'productCarbohydrates':
        return product.carbohydratesPerHundredGrams
      default:
        throw new Error()
    }
  }

  private static replacedSymbols(value: string): string {
    return value
      .replace(/[^\d,.]/g, '')
      .replace('.', ',')
      .replace(/(^[^,]*,)|,+/g, '$1')
  }

  ngOnDestroy(): void {
    console.log('onDestroy')
    this.subscriptions.forEach((sub) => sub.unsubscribe())
    this.close.unsubscribe()
  }
}
