
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


import {Subject} from "rxjs";
import {
  Http,
  RequestOptions,
  RequestOptionsArgs,
  Response
} from '@angular/http';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { IData } from "./models";
import { HttpEventType } from "@angular/common/http";
import { HttpEvent } from "@angular/common/http";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";

@Injectable()
export class HttpService{

    private apiUrl = 'http://localhost:6542';
    private httpOptions: any;
     constructor(
    private http: HttpClient,
    private notificationServiceService:NotificationServiceService
  ) {

    console.log('Init. Form-Api-Service...');
    

    this.httpOptions = { headers: new HttpHeaders().set('Content-Type', 'application/json') };

  }


    getData(url) {
        let finalUrl=this.apiUrl+url;
        
        return this.http.get<any>(finalUrl);//.map((response: Response) => <FormModel>response.json());
    }

    postData(url: string,data:any ):Observable<HttpEvent<HttpEventType.Response>>  {
    
      let finalUrl=this.apiUrl+url;
      return this.http.post<any>(finalUrl, data, this.httpOptions)

    }

    delete(url: string) {
    
      let finalUrl=this.apiUrl+url;
      return this.http.delete<any>(finalUrl, this.httpOptions);

    }
      postDataString(url: string,data:any ):Observable<HttpEvent<HttpEventType.Response>>  {
    
      let finalUrl=this.apiUrl+url;
      return this.http.post<any>(finalUrl, data);

    }

    postDataWithOptions(url: string,data:any,options:any ) :Observable<HttpEvent<HttpEventType.Response>> {
    
      let finalUrl=this.apiUrl+url;
      return this.http.post<any>(finalUrl, data);

    }
     putData( url: string,data:any):Observable<HttpEvent<HttpEventType.Response>>  {
    
      let finalUrl=this.apiUrl+url;
      return this.http.put<any>(finalUrl, data, this.httpOptions)

    }

      makeFileRequest (url: string, params: string[], files: File[]) {
    return Observable.create(observer => {
      let formData: FormData = new FormData(),
        xhr: XMLHttpRequest = new XMLHttpRequest();

      for (let i = 0; i < files.length; i++) {
        formData.append("file", files[i], files[i].name);
      }

      xhr.onreadystatechange = () => {
        alert("ok me too here");
        if (xhr.readyState === 4) {
          if (xhr.status === 201) {
            alert("called me");
            observer.next(JSON.parse(xhr.response));
            observer.complete();
            
          } else {
            observer.error(xhr.response);
          }
        }
      };

      xhr.upload.onprogress = (event) => {
        console.log("progressing");
      };
      alert("ok good");
      xhr.open('POST', this.apiUrl+url, true);
      xhr.send(formData);
    });
  }

  
}