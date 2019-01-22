import { Component, OnInit,Input } from '@angular/core';
import {SliderPhotoComponent} from './sliderphoto.component';
@Component({
  selector: 'app-slider',
  templateUrl: './slider.component.html',
  styleUrls: ['./slider.component.scss'],
  providers:[]
})
export class SliderComponent implements OnInit {

  @Input()
  photoId:string;
  @Input()
  post:any;
  @Input()
  parent:any;
  constructor() { }

  ngOnInit() {
    console.log(" parent in slider " + this.parent);
  }

}
