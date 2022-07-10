import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Ration} from "../shared/interfaces/ration";

@Injectable({
  providedIn: 'root'
})
export class RationService {

  constructor(private http: HttpClient) {
  }

  searchProducts(query: string = ""): Observable<Ration[]> {
    return this.http.post('http://localhost:8082/ration/search', {
        messageType: "SearchRationRequest",
        requestId: "rID:00007",
        query: query,
        debug: {
          mode: "test"
        }
      },
      {
        responseType: "json"
      }
    ).pipe(
      map(response => {
          let rations: Ration[] = []
          Object.entries(response).find(([key, value]) => {
            if (key === 'foundRations') {
              rations = value as Ration[]
            }
          })
          return rations
        }
      )
    )
  }
}
