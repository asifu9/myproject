import { Component } from '@angular/core';
import {HomeComponent} from './home/home.component';
import { TranslateService } from "@ngx-translate/core";
declare var jQuery: any;
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers:[]
})
export class AppComponent {
  title = 'app works!';
  constructor(private translateService: TranslateService){
    //     this.translateService.addLangs(['en']);
    // const defaultLanguage = 'en';
    // translateService.setDefaultLang(defaultLanguage);
     translateService.setDefaultLang('en');
 
         // the lang to use, if the lang isn't available, it will use the current loader to get them
        translateService.use('en');
  }
}
