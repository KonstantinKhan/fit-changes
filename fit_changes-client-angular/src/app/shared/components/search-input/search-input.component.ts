import {Component, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {map, Observable, startWith} from "rxjs";

@Component({
  selector: 'app-search-input',
  templateUrl: './search-input.component.html',
  styleUrls: ['./search-input.component.scss']
})
export class SearchInputComponent implements OnInit {
  value: string = "";
  searchControl = new FormControl();
  products: string[] = ['Помидор', 'Говядина', 'Молоко'];
  filteredProducts?: Observable<string[]>;

  ngOnInit() {
    this.filteredProducts = this.searchControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value)),
    );
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    if (!filterValue.trim()) {
      return []
    } else {
      return this.products.filter(option => option.toLowerCase().includes(filterValue));
    }
  }
}
