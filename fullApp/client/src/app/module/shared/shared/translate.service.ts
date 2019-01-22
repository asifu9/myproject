import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { TranslateService } from '@ngx-translate/core';
@Injectable({
  providedIn: 'root'
})
export class TranslationService {

  data: any = {};

  constructor(private service: TranslateService) {
    //this.use("en");

    this.data = service.translations.en
  }
  getAsyn(key){
    return this.service.get("label-"+key)
  }
  get(key) {
    if (this.data) {
      let d = this.data["label-" + key];
      if (d)
        return d;
      else
        return d = key;
    } else {
      this.data = this.service.translations.en;
      if (this.data) {
        return this.get(key);
      } else {
        return key;
      }
    }
  }
  getError(key) {
    if (this.data) {
      let d = this.data["error-" + key];
      if (d)
        return d;
      else
        return key;
    }
    else {
      this.data = this.service.translations.en;
      if (this.data) {
        return this.getError(key);
      } else {
        return key;
      }
    }
  }
}



  // getMessage(key){
  //   console.log(this.data);
  //   if(this.data && this.data.length>0){
  //     return this.data["error."+key];
  //   }else{
  //     if(!this.isFetchInProgress){
  //       this.use("en").then(result=>{
  //         return this.data["error."+key];
  //       })
  //     }else{
  //       return this.getMessage(key);

  //     }
  //   }
  // }

  // use(lang: string): Promise<{}> {
  //   this.isFetchInProgress=true;
  //   return new Promise<{}>((resolve, reject) => {
  //     const langPath = `assets/i18n/${lang || 'en'}.json`;
  //     this.http.get<{}>(langPath).subscribe(
  //       translation => {
  //         this.isFetchInProgress=false;
  //         this.data = Object.assign({}, translation || {});
  //         resolve(this.data);
  //       },
  //       error => {
  //         this.data = {};
  //         this.isFetchInProgress=false;
  //         resolve(this.data);
  //       }
  //     );
  //   });
  // }

