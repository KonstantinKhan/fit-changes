import {APP_INITIALIZER, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {MainLayoutComponent} from './shared/components/main-layout/main-layout.component';
import {HomePageComponent} from './home-page/home-page.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatToolbarModule} from "@angular/material/toolbar";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatSidenavModule} from "@angular/material/sidenav";
import {ProductsPageComponent} from './products/components/products-page/products-page.component';
import {DishesPageComponent} from './dishes-page/dishes-page.component';
import {MatInputModule} from "@angular/material/input";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {SearchInputComponent} from './shared/components/search-input/search-input.component';
import {MatCardModule} from "@angular/material/card";
import {ChartModule} from "primeng/chart";
import {EnergyStatisticComponent} from './shared/components/energy-statistic/energy-statistic.component'
import {SearchProductPipe} from "./shared/pipes/search-product.pipe";
import {RationPageComponent} from './ration/ration-page/ration-page.component';
import {ModalCreateProductComponent} from './shared/components/modal-create-product/modal-create-product.component';
import {DynamicModalLoader} from "./shared/directives/load-modal.directive";
import {ButtonModule} from "primeng/button";
import {RippleModule} from "primeng/ripple";
import {TableModule} from "primeng/table";
import {ToolbarModule} from "primeng/toolbar";
import {ProductCardTwinComponent} from "./products/components/product-card-twin/product-card-twin.component";
import {initializeKeycloak} from "./init/keycloak-init.factory";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {ToastModule} from "primeng/toast";
import {InputTextModule} from "primeng/inputtext";

@NgModule({
  declarations: [
    AppComponent,
    MainLayoutComponent,
    HomePageComponent,
    ProductsPageComponent,
    DishesPageComponent,
    SearchInputComponent,
    EnergyStatisticComponent,
    SearchProductPipe,
    RationPageComponent,
    ModalCreateProductComponent,
    DynamicModalLoader,
    ProductCardTwinComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    KeycloakAngularModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    FlexLayoutModule,
    MatIconModule,
    MatButtonModule,
    MatSidenavModule,
    MatInputModule,
    FormsModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    MatCardModule,
    ChartModule,
    ButtonModule,
    RippleModule,
    TableModule,
    ToolbarModule,
    ToastModule,
    InputTextModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
