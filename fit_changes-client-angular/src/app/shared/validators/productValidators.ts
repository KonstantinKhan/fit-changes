import {AbstractControl, ValidatorFn} from "@angular/forms";
import {Product} from "../interfaces/product";

export class ProductValidators {

  static maxValue(maxValue: number): ValidatorFn {
    return (control: AbstractControl): { [key: string]: boolean } | null => {
      if (+control.value.replace(',', '.') > maxValue) {
        return {
          'maxValue': true
        }
      }
      return null
    }
  }

  static calculating(product: Product, delta: number): ValidatorFn {
    return (): { [key: string]: boolean } | null => {
      if (!this.isCorrectCalculatedCalories(product, delta) ||
        !this.isCorrectValues(product)
      ) {
        return {'incorrectValues': true}
      }
      return null
    }
  }

  private static isCorrectCalculatedCalories(product: Product, delta: number): boolean {
    return Math.abs((this.calculateCalories(product) - product.caloriesPerHundredGrams) / product.caloriesPerHundredGrams) < delta
  }

  private static isCorrectValues(product: Product): boolean {
    return (product.proteinsPerHundredGrams + product.fatsPerHundredGrams + product.carbohydratesPerHundredGrams) < 100
  }

  private static calculateCalories(product: Product): number {
    return product.proteinsPerHundredGrams * 4 + product.fatsPerHundredGrams * 9 + product.carbohydratesPerHundredGrams * 4
  }
}
