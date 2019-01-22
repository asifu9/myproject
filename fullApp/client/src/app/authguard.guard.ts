import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { SessionService } from "./module/shared/shared/SessionService";

@Injectable()
export class AuthguardGuard implements CanActivate {

constructor(private auth: SessionService,private router:Router){
  
} 

  canActivate(
    next: ActivatedRouteSnapshot,
  state: RouterStateSnapshot):  Promise<boolean> | boolean {
    return this.auth.isUserAuthenticated();
  }

  checkLoggedInUser(){
    if(localStorage.getItem('user')){
      this.auth.setCurrentDetails(JSON.parse(localStorage.getItem('user')),JSON.parse( localStorage.getItem('company')),JSON.parse( localStorage.getItem('token')),JSON.parse( localStorage.getItem('roles')),JSON.parse( localStorage.getItem('setting')));
    }
  }
}
