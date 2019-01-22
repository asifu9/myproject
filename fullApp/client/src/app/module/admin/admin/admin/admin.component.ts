import { Component, OnInit } from '@angular/core';
import {CompanyComponent} from '../company/company.component';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss'],
  providers:[CompanyComponent]
})
export class AdminComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
