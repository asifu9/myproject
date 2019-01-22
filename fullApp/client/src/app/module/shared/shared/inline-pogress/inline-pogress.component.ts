import { Component, OnInit,Input } from '@angular/core';

@Component({
  selector: 'app-inline-pogress',
  templateUrl: './inline-pogress.component.html',
  styleUrls: ['./inline-pogress.component.css']
})
export class InlinePogressComponent implements OnInit {

  constructor() { }
  @Input() inProgress:boolean=false;
  ngOnInit() {
  }

}
