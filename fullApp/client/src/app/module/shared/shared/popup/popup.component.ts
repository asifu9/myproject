import { Component, OnInit,ViewChild } from '@angular/core';
import { PostShareComponent } from "../../../feed/postshare.component";

@Component({
  selector: 'popup',
  templateUrl: './popup.component.html',
  styleUrls: ['./popup.component.scss'
  ]
})
export class PopupComponent {

 
 @ViewChild('share1') child:PostShareComponent;

  public visible = false;
  private visibleAnimate = false;

  constructor(){}

  public show(): void {
    this.visible = true;
    setTimeout(() => this.visibleAnimate = true, 100);
  }

  public hide(): void {
    this.visibleAnimate = false;
    setTimeout(() => this.visible = false, 300);
  }

  public onContainerClicked(event: MouseEvent): void {
    if ((<HTMLElement>event.target).classList.contains('modal')) {
      this.hide();
    }
  }
  //  public onContainerClicked(): void {
  //     this.hide();
  // }
   public onContainerClickedPop(event: MouseEvent): void {
      this.hide();
  }



}
