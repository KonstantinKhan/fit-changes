import {HttpClient, HttpHeaders} from "@angular/common/http";
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
        responseType: "json",
        // todo temporary token for testing
        headers: new HttpHeaders({
          Authorization: "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJwcm9kdWN0LXVzZXJzIiwiaXNzIjoiaHR0cDovLzAuMC4wLjA6ODA4MC8iLCJncm91cHMiOlsiVVNFUiIsIlRFU1QiXSwiZXhwIjoxNjcyNTMxMjAwfQ.Nhxc4RGba6ppuGfM_wIrejRSoS5K6_1C9604n-7LF_o"
        })
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
