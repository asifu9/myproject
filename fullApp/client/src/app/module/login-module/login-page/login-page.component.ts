import { Component, OnInit } from '@angular/core';
import { HttpService } from "../../shared/shared/httpService";
import { SessionService } from "../../shared/shared/SessionService";
import { Router, ActivatedRoute } from "@angular/router";
import { NgZone } from '@angular/core';
import { HttpEventType } from "@angular/common/http";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
compName:string;
userName:string="superuser";
password:string="superuser";
  constructor(public zone: NgZone,private httpService:HttpService,private sessionService:SessionService,private router:Router,private activatedRoute:ActivatedRoute) { 
    this.compName=this.activatedRoute.snapshot.params["compName"];
  }

  ngOnInit() {
      if(localStorage.getItem('user')){
        this.sessionService.setCurrentDetails(localStorage.getItem('user'),localStorage.getItem('company'),
        localStorage.getItem('token'),localStorage.getItem('roles'),localStorage.getItem('setting'));
      }
  }
login(){
  if(this.userName && this.password){
    localStorage.clear();
    this.httpService.postData("/UserLogin/login",{"userName":this.userName,"password":this.password,"comp":this.compName}).subscribe
    (event=>{
    
        let result:any= event;
        this.sessionService.setCurrentDetails(result.user,result.company,result.token,result.roles,result.setting);
        let subs=this.sessionService.dataLoaded.subscribe(result=>{
          if(result){
            //login to other page
            //this.router.navigate(['/home']);
            
             this.zone.run(() => { this.router.navigate(['/home']); })
          }else{
            //show error message
          }
          subs.unsubscribe();
        })
        console.log("uup logged in");

    });
  }
}
}
