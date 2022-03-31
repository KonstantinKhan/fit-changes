import {
  Component,
  ElementRef, HostListener,
  OnInit, Renderer2,
  ViewChild,
  ViewEncapsulation
} from '@angular/core';
import {ProductSearchService} from "../../../services/product-search.service";
import {MatFormField} from "@angular/material/form-field";
import {Product} from "../../interfaces/product";

@Component({
  selector: 'app-search-input',
  templateUrl: './search-input.component.html',
  styleUrls: ['./search-input.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class SearchInputComponent implements OnInit {

  @ViewChild('search_results') searchResults!: ElementRef
  @ViewChild('search_input') searchInput!: MatFormField

  query: string = "";

  products: Product[] = []

  testProducts: Product[] = [
    {
      id: '0001',
      productName: "Филе куриное",
      caloriesPerHundredGrams: 110.0,
      proteinsPerHundredGrams: 21.0,
      fatsPerHundredGrams: 2.5,
      carbohydratesPerHundredGrams: 0.0
    },
    {
      id: '0002',
      productName: "Помидор",
      caloriesPerHundredGrams: 20.0,
      proteinsPerHundredGrams: 1.1,
      fatsPerHundredGrams: 0.2,
      carbohydratesPerHundredGrams: 3.7
    },
    {
      id: '0003',
      productName: "Гречка",
      caloriesPerHundredGrams: 313.0,
      proteinsPerHundredGrams: 12.6,
      fatsPerHundredGrams: 3.3,
      carbohydratesPerHundredGrams: 62.1
    },
  ]

  searchWidth = ''
  isRender = false;

  constructor(
    private searchService: ProductSearchService,
    private renderer: Renderer2
  ) {
  }

  ngOnInit() {
    this.products = this.testProducts
  }

  ngAfterViewInit() {
    this.searchWidth = this.searchInput._elementRef.nativeElement.offsetWidth
  }

  ngAfterViewChecked() {

    if (this.searchWidth != this.searchInput._elementRef.nativeElement.offsetWidth) {
      this.searchWidth = this.searchInput._elementRef.nativeElement.offsetWidth
      if (this.isRender) {
        this.isRender = false
      }
    }

    if (this.searchResults === undefined) {
      if (this.isRender) {
        this.isRender = false
      }
    } else {
      if (!this.isRender) {
        this.renderer.setStyle(
          this.searchResults.nativeElement,
          'width',
          `${this.searchWidth}px`
        )
        this.isRender = true
      }
    }
  }

  @HostListener('window:resize')
  changeWidthWindow() {
    this.searchWidth = this.searchInput._elementRef.nativeElement.offsetWidth
    if (this.isRender) {
      this.isRender = false
    }
  }
}
