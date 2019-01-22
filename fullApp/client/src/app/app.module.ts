import { BrowserModule } from '@angular/platform-browser';
import { NgModule,Pipe } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, RouteReuseStrategy } from '@angular/router';
import { AppComponent } from './app.component';
import {routeConfig} from './router';
import { HomeComponent } from './home/home.component';
import { HttpModule } from "@angular/http";
import { FeedModule } from './module/feed/feed.module';
import { PhotoModule } from './module/photo/photo.module';
import { GroupsModule } from './module/groups/groups.module';
import { MessagesModule } from './module/messages/messages.module';
import { NotificationModule } from './module/notification/notification.module';
import { CommentsModule } from './module/comments/comments.module';
import { SharedModule } from './module/shared/shared.module'; 
import { SessionService } from "./module/shared/shared/SessionService";
import { PeopleModule } from './module/people/people.module';
import { ProfileModule } from './module/profile/profile.module';
import { AdminModule } from './module/admin/admin.module';
import { HttpService } from "./module/shared/shared/httpService";
import { HttpClientModule } from "@angular/common/http";
import { AdminSettingModule } from './module/admin-setting/admin-setting.module';
import { CustomReuseStrategy } from "./CustomReuseStrategy";
import { ConfigValueService } from "./ConfigValueService";
import { TicketCategoryModule } from "./ticket-category/ticket-category.module";
import { TicketModule } from "./module/ticket/ticket.module";
import { AuthguardGuard } from "./authguard.guard";
import { ActivityGuard } from "./ActivityGuard";
import { EventsModule } from "./module/events/events.module";
import { ProjectModule } from "./module/project/project.module";
import { LoginModuleModule } from "./module/login-module/login-module.module";
import { BasePageComponent } from './base-page/base-page.component';
import { BaseComponent } from './base/base.component';
import { HTTP_INTERCEPTORS } from "@angular/common/http";
import { TokenServiceService } from "./module/login-module/token-service.service";
import { LeaveDetailsComponent } from "./module/leaves/leave-details/leave-details.component";
import { LeavesModule } from "./module/leaves/leaves.module";
import { ScrollTopService } from "./module/shared/shared/scroll-top-service.service";
import { NotificationServiceService } from "./module/shared/shared/notification-service.service";
import { TranslateModule, TranslateLoader } from "@ngx-translate/core";
import { Http } from "@angular/http";
import { HttpClient } from "@angular/common/http";


import {TranslateHttpLoader} from '@ngx-translate/http-loader';
export function HttpLoaderFactory(http: HttpClient) {

    return new TranslateHttpLoader(http, '/assets/i18n/', '/ux-string.json');
}
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    BasePageComponent,
    BaseComponent,
    // AddressComponent,
    // ProfessionComponent,
    // EducationComponent,
    // LoginComponent,
    // HomeComponent,
    // PeoplesComponent,
    // ,SelectedCompanyComponent, 
    // CompanyComponent, 
    // AdminComponent
  ],
  
  imports: [

    RouterModule.forRoot(routeConfig
    ),
    TranslateModule.forRoot({
      loader:{
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    SharedModule,
    FeedModule,
    PhotoModule,
    LoginModuleModule,
    GroupsModule,
    MessagesModule,
    NotificationModule,
    CommentsModule,
    PeopleModule,
    LeavesModule,
    ProfileModule,
    AdminModule,
    LoginModuleModule,
    AdminSettingModule,
    TicketModule,
    TicketCategoryModule,
    EventsModule,
    ProjectModule
    
  ],
  exports:[
    TranslateModule
  ],
  providers: [{
      provide: HTTP_INTERCEPTORS,
      useClass: TokenServiceService,
      multi: true
    },ScrollTopService,NotificationServiceService,SessionService,HttpService,ConfigValueService,AuthguardGuard,ActivityGuard,{provide: RouteReuseStrategy, useClass: CustomReuseStrategy}],
  bootstrap: [AppComponent]
})
export class AppModule { }
