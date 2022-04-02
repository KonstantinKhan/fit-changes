import {Directive, ViewContainerRef} from "@angular/core";

@Directive({
  selector: '[dynamicModalLoader]'
})
export class DynamicModalLoader {
  constructor(public viewContainerRef: ViewContainerRef) {
  }
}
