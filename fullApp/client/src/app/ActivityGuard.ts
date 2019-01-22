import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, CanActivateChild, RouterStateSnapshot } from '@angular/router';
import { SessionService } from "./module/shared/shared/SessionService";
import { Observable } from "rxjs";

@Injectable()
export class ActivityGuard implements CanActivateChild {
 
  constructor(public auth: SessionService, public router: Router) {}

 

     canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
            const mainRole = route.data.mainRole;
    const subRole = route.data.subRole;
   // const token = localStorage.getItem('token');
    // decode the token to get its payload
    //const tokenPayload = decode(token);
    if(!mainRole){
      return true;
    }
    if (!this.auth.isUserAuthenticated() || !this.auth.isActivityExists(mainRole,subRole)) {
      if(mainRole=='noaccess'){
        return true;
      }
      if(mainRole=='department'||mainRole=='organization'||mainRole=='designation'||mainRole=='team'||mainRole=='user'){
        this.router.navigate(['admin/setting/noaccess']);
      }else{
        this.router.navigate(['/noaccess']);
      }
      
      return false;
    }
    return true;
    }


}