import { Injectable } from '@angular/core';
import { TranslateLoader } from "@ngx-translate/core";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class HttpLoaderService implements TranslateLoader{

   private hostUrl = '';
    constructor(private http: HttpClient, private prefix: string = '/assets/i18n', private suffix: string = 'json'){
        const bases = document.getElementsByTagName('base');
        if (bases.length > 0 && bases[0].href) {
            this.hostUrl = bases[0].href;
        }
    }
    public getTranslation(lang: string): any {
         return this.http.get(`${this.hostUrl}${this.prefix}/${lang}/${this.suffix}`)
            .subscribe((res: any) => res.json());
    }
}


