import {Component, ElementRef, HostListener, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {MatDrawerMode, MatSidenav} from "@angular/material/sidenav";

@Component({
  selector: 'app-main-layout',
  templateUrl: './main-layout.component.html',
  styleUrls: ['./main-layout.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class MainLayoutComponent implements OnInit {

  @ViewChild('sidenav', {static: true}) sidenav!: MatSidenav

  opacityElement: string = '0.6';
  widthWindow: number = window.innerWidth;
  isMenuOpen: boolean;
  isFullPanel = true;
  isFocusSidenav = false;

  constructor() {
    this.isMenuOpen = false
  }

  ngOnInit(): void {
    this.positionSideNav()
  }

  positionSideNav() {
    this.isMenuOpen = this.isLargeScreen();
  }

  isLargeScreen(): boolean {
    return this.widthWindow >= 600;
  }

  getSidenavMode(): MatDrawerMode {
    if (this.isLargeScreen()) {
      return 'side';
    } else return 'over';
  }

  onToolBarMenuToggle() {
    this.isFullPanel = !this.isFullPanel;
  }

  onToggle() {
    if (!this.isLargeScreen()) {
      this.sidenav.toggle()
    }
  }

  @HostListener('window:resize', ['$event'])
  getWidthWindow(event: Event) {
    this.widthWindow = (<Window>event.target).innerWidth;
    this.positionSideNav()
  }
}
