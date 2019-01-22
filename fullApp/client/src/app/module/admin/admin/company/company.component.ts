import { Component, OnInit } from '@angular/core';
import { Route, ActivatedRoute, Router } from "@angular/router";
import { Company } from "../../../shared/shared/models";
import { SessionService } from "../../../shared/shared/SessionService";
import { HttpService } from "../../../shared/shared/httpService";

@Component({
  selector: 'company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.scss']
})
export class CompanyComponent implements OnInit {

  constructor(private apiService: HttpService, private router: Router, private activatedRoute: ActivatedRoute, private session: SessionService) {

  }
  public companies: Company[];
  ngOnInit() {
    this.apiService.getData("/Company/").subscribe((data) => {
      this.companies = data
    });
  }

  selectCompany(i) {
    console.log(i);
    this.session.shareObj.selectedCompany = i.id;
    console.log(this.session.shareObj);
    this.router.navigate(['admin/company']);
  }
  generateSetting(i) {
    this.apiService.postData("/Company/generate/setting",i).subscribe(
      result=>{
        alert("done");
      }
    )
  }

}
