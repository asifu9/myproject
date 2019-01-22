import { Component, OnInit } from '@angular/core';
import {Route, ActivatedRoute, Router} from "@angular/router";
import { User } from "../../../shared/shared/models";
import { SessionService } from "../../../shared/shared/SessionService";
import { HttpService } from "../../../shared/shared/httpService";


@Component({
  selector: 'users', 
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss'],
  providers: []
})
export class UsersComponent implements OnInit {

 public users:  User[];
  constructor(private apiService:HttpService,private router: Router,private activatedRoute: ActivatedRoute,private session:SessionService) { 
  
   
  }

  ngOnInit() {
    console.log("sssssfffff");
    this.apiService.getData("/User/wall/"+this.session.shareObj['wall']).subscribe((data) => { 
      this.users= data
  });
  }

}
