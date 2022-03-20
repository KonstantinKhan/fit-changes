import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Product} from "../shared/interfaces/product";
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProductSearchService {

  public filteredProducts$ = new Subject<Product[]>()

  constructor(private http: HttpClient) {
  }

  searchProducts(query: string) {
    return this.http.post<Product[]>("", {query: query})
  }
}
