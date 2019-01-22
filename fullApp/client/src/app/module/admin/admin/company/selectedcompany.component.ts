import { Component, OnInit } from '@angular/core';
import {Route, ActivatedRoute, Router} from "@angular/router";
import { SessionService } from "../../../shared/shared/SessionService";
import { Company } from "../../../shared/shared/models";
import { HttpService } from "../../../shared/shared/httpService";

@Component({
  selector: 'selectedcompany',
  templateUrl: './selectedcompany.component.html',
  styleUrls: ['./selectedcompany.component.scss'],
  providers:[]
})
export class SelectedCompanyComponent implements OnInit {

   constructor(private apiService:HttpService,private router: Router,private activatedRoute: ActivatedRoute,private session:SessionService) { 

  }
public companies:  Company;
  ngOnInit() {
     console.log(this.session.shareObj);
     this.apiService.getData("/Company/"+this.session.shareObj.selectedCompany).subscribe((data) => { 
      this.companies= data
    });
  }

}
