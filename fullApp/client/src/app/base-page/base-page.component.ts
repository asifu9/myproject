import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import { SessionService } from "../module/shared/shared/SessionService";

@Component({
  selector: 'app-base-page',
  templateUrl: './base-page.component.html',
  styleUrls: ['./base-page.component.scss']
})
export class BasePageComponent implements OnInit {

   compName:string;
  constructor(private router:Router,private activatedRoute:ActivatedRoute,private sessionService:SessionService) { 
    this.compName=this.activatedRoute.snapshot.params['compName'];
    console.log(this.compName);
   this.activatedRoute.params.subscribe(params => {
    console.log(params.compName);
    
  });
  }

  ngOnInit() {
    //this.router.navigate(['/feed']);
       this.compName=this.activatedRoute.snapshot.params['compName'];
    console.log(this.compName);
   this.activatedRoute.params.subscribe(params => {
    console.log(params.compName);
    
  });
    if(!this.sessionService.isLoggedIn()){
      
      this.router.navigate(['/login'])
    }
  }

}
