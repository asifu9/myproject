import { Component, OnInit } from '@angular/core';
import { RouterModule, Routes, Router, ActivatedRoute } from '@angular/router';
import { SessionService } from "../module/shared/shared/SessionService";
@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  providers:[]
})
export class HomeComponent implements OnInit {
  compName:string;
  constructor(private router:Router,private activatedRoute:ActivatedRoute,private sessionService:SessionService) { 
  }

  ngOnInit() {
   // this.router.navigate(['feed']);

  }

}
