import { Injectable } from '@angular/core';
import { HttpInterceptor,HttpRequest,HttpHandler,HttpEvent } from "@angular/common/http";
import { Observable } from 'rxjs';
import { NotificationServiceService } from "../shared/shared/notification-service.service";

@Injectable()
export class TokenServiceService implements HttpInterceptor {

  constructor(private notificationServiceService:NotificationServiceService) { }
  

   intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    
    let str=localStorage.getItem('token');
    let obj = JSON.parse(str);
    if(obj){
       request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${obj.access_token}`
      }
    });
    }
  
    
    return next.handle(request);
  }

}
