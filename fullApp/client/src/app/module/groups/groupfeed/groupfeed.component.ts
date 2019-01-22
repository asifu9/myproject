import { Component, OnInit } from '@angular/core';
import {GroupLeftComponent} from './groupleft.component';
@Component({
  selector: 'app-groupfeed',
  templateUrl: './groupfeed.component.html',
  styleUrls: ['./groupfeed.component.scss'],
  providers:[GroupLeftComponent]
})
export class GroupfeedComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
