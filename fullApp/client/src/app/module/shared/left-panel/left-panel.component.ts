import { Component, OnInit } from '@angular/core';
import { User}  from '../shared/models';
import {SessionService} from '../shared/SessionService';
import {FileUploadComponent} from '../shared/file-upload/file-upload.component';
import { ComponentFactoryResolver, ViewChild, ViewContainerRef, PLATFORM_ID, APP_ID, Inject } from '@angular/core';
import { HttpService } from "../shared/httpService";
import { Util } from "../util";
import { ScrollTopService } from "../shared/scroll-top-service.service";
import { isPlatformBrowser } from "@angular/common";
import { Router } from "@angular/router";
import { BehaviorSubject } from "rxjs";

declare var jQuery:any;
// import {LocalStorage, SessionStorage} from "angular2-localstorage/WebStorage";
@Component({
  selector: 'leftPanel',
  templateUrl: './left-panel.component.html',
  styleUrls: ['./left-panel.component.scss'],
  providers:[FileUploadComponent]
})
export class LeftPanelComponent implements OnInit {
photoPath:string='';
path:string='/assets/uploads';
@ViewChild("modal2") mymodel;
@ViewChild("container",{read:ViewContainerRef}) container;
copom:any;
// @ViewChild('dynamicContentPlaceHolder', {read: ViewContainerRef}) 
//     protected dynamicComponentTarget: ViewContainerRef;
private user:User=new User();
  constructor( @Inject(PLATFORM_ID) private platformId: Object,
    @Inject(APP_ID) private appId: string,private scrollTopService:ScrollTopService,private apiService:HttpService,
        private session:SessionService, private router:Router,
        protected componentResolver: ComponentFactoryResolver,
        protected customComponentBuilder: FileUploadComponent) { 

  }
  selectedMenu:string="feed";


  ngOnInit() { 
    //this.scrollTopService.setScrollTop();
    let urlName=this.router.url.split("/")[this.router.url.split("/").length - 1]
    console.log(urlName);
     this.getUserById()
     this.session.leftMenuSelected.subscribe(result=>{
       this.selectedMenu=result;
     })
  }

  getUserById(){
            this.user= this.session.currentUser;
            this.photoPath= this.session.showPhoto(this.user.photo);
       // this.user.photo=this.session.updatePhotoIcon(this.user.photo);
      //  this.photoPath=this.user.photo.displayPath;
    // this.apiService.getData("/User/main/"+this.session.shareObj['user']).subscribe((data) => { 
    //     this.user= data;
    //     this.user.photo=this.session.updatePhotoIcon(this.user.photo);
    //     this.photoPath=this.user.photo.displayPath;
        
    // });
  }

  // onActivate(e, outlet){
  //   console.log("sssssss");
  //    if (isPlatformBrowser(this.platformId)) {
  //     let scrollToTop = window.setInterval(() => {
  //       let pos = window.pageYOffset;
  //       if (pos > 0) {
  //         window.scrollTo(0, pos - 50); // how far to scroll on each step
  //       } else {
  //         window.clearInterval(scrollToTop);
  //       }
  //     }, 16);
  //   }
  // }
  updateMe(){
    if(this.copom!=null){
      this.copom.destroy();
    }
    this.copom= this.container.createComponent(this.componentResolver.resolveComponentFactory(FileUploadComponent));
    this.copom.instance.user=this.user;
     this.copom.instance.type='personal';
    this.mymodel.show();

  }

  getDate(d){
    let date=new Date(d*1000);
    return Util.displayDateFormat(date);
  }


}
