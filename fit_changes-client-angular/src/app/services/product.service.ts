import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {map, Observable} from "rxjs";
import {Product} from "../shared/interfaces/product";

@Injectable({providedIn: 'root'})
export class ProductService {


  constructor(private http: HttpClient) {
  }

  searchProducts(): Observable<Product[]> {
    return this.http.post('http://localhost:8080/product/search', {
        messageType: "SearchProductRequest",
        requestId: "rID:0006",
        query: "",
        debug: {
          mode: "test"
        },
      },
      {
        responseType: "json"
      }).pipe(
      map(response => {
        let products: Product[] = []
        Object.entries(response).find(([key, value]) => {
          if (key === "foundProducts") {
            products = value as Product[]
          }
        })
        return products
      })
    )
  }

  createProduct(product: Product): Observable<Product> {
    return this.http.post<Product>('http://localhost:8080/product/create', {
      messageType: "CreateProductRequest",
      requestId: "rID:0001",
      createProduct: product,
      debug: {
        mode: "test"
      },
      responseType: "json"
    })
  }

  deleteProduct(productId: string): Observable<Product> {
    return this.http.post<Product>('http://localhost:8080/product/delete', {
      messageType: "DeleteProductRequest",
      requestId: "rID:0002",
      deleteProductId: productId,
      debug: {
        mode: "test"
      },
      responseType: "json"
    })
  }
}
