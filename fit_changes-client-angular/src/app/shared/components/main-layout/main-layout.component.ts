import {Component, HostListener, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {MatDrawerMode, MatSidenav} from "@angular/material/sidenav";
import {animate, state, style, transition, trigger} from "@angular/animations";

@Component({
  selector: 'app-main-layout',
  templateUrl: './main-layout.component.html',
  styleUrls: ['./main-layout.component.scss'],
  encapsulation: ViewEncapsulation.None,
  animations: [
    trigger('sidenavAnimation', [
      state('open', style({
        width: '240px',
      })),
      state('close', style({
        width: '34px'
      })),
      transition('open <=> close', animate('400ms ease'))
    ])
  ]
})
export class MainLayoutComponent implements OnInit {

  @ViewChild('sidenav') sidenav!: MatSidenav

  opacityElement: string = '0.6';
  widthWindow: number = window.innerWidth;
  isFullPanel = true;
  isFocusSidenav = false;
  animating = false;
  stateSidenav = 'open';
  sidenavMode: MatDrawerMode = 'over'

  constructor() {
  }

  ngOnInit(): void {
    this.setSidenavMode()
  }

  isLargeScreen(): boolean {
    return this.widthWindow >= 600;
  }

  setSidenavMode() {
    if (this.isLargeScreen()) {
      this.sidenavMode = 'side';
    } else this.sidenavMode = 'over';
  }

  onToggle() {
    if (!this.isLargeScreen()) {
      this.sidenav.toggle().then(r => r)
    }
  }

  onCustomToggle() {
    this.isFullPanel = !this.isFullPanel
    if (this.isFullPanel) {
      this.stateSidenav = 'open'
    } else this.stateSidenav = 'close'
  }

  startAnimation() {
    this.animating = true;
    this.tick();
  }

  doneAnimation() {
    this.animating = false;
  }

  tick() {
    if (this.animating) requestAnimationFrame(() => this.tick());
  }

  @HostListener('window:resize', ['$event'])
  changeWidthWindow(event: Event) {
    this.widthWindow = (<Window>event.target).innerWidth;
    this.setSidenavMode()
  }
}
