import { Component, OnInit,Input } from '@angular/core';

@Component({
  selector: 'loader',
  templateUrl: './loader.component.html',
  styleUrls: ['./loader.component.scss']
})
export class LoaderComponent implements OnInit {

  constructor() { }
  @Input()
  message:string;
  ngOnInit() {
    
  }

}
