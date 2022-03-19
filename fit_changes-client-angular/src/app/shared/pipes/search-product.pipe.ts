import {Pipe, PipeTransform} from "@angular/core";
import {Product} from "../interfaces/product";

@Pipe({
  name: 'searchProduct'
})
export class SearchProductPipe implements PipeTransform {
  transform(products: Product[], searchProduct = ''): Product[] {
    return products.filter(product => {
      return product.productName.toLowerCase().includes(searchProduct.toLowerCase())
    })
  }

}
