import { Component, OnInit,Input } from '@angular/core';

@Component({
  selector: 'success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.scss']
})
export class SuccessComponent implements OnInit {

  constructor() { }
  @Input()
  message:string;
  ngOnInit() {
  }

}
