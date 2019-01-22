import { Component, OnInit, ElementRef, ViewChild, Input } from '@angular/core';
import { Http } from '@angular/http';
import { FeedListComponent } from "./feedlist.component";
import { LoaderComponent } from "../shared/shared/loader/loader.component";
import { SuccessComponent } from "../shared/shared/success/success.component";
import { Feed, User, MyMap } from "../shared/shared/models";
import { SessionService } from "../shared/shared/SessionService";
import { HttpService } from "../shared/shared/httpService";
import { HttpEventType } from "@angular/common/http";
import { TranslationService } from "../shared/shared/translate.service";

declare var jQuery: any;


@Component({
  selector: 'feeds',
  templateUrl: './feeds.component.html',
  styleUrls: ['./feeds.component.scss'],
  providers: []
})
export class FeedsComponent implements OnInit {
  //2e12753c-d249-45ae-a1df-f42bc367f79a
  private feed: Feed = new Feed();
  public feeds: Feed[];
  public myMap:MyMap[]=[];
  @ViewChild('fileInput') inputEl: ElementRef;
  oading: boolean = false;
  loading: boolean = false;
  savingMessage: string = "";
  selectedPhotos: string[];
  @Input() multiple: boolean = true;
  user:User=new User();

  constructor(private transaltionService:TranslationService,public apiService: HttpService, public session: SessionService, 
  private http: Http,public httpService:HttpService) {
    this.user= this.session.currentUser;
    this.session.updateMenu("feeds");
  }

  ngOnInit() {
    this.transaltionService.getAsyn("savingPleaseWait").subscribe(result=>{
      this.savingMessage=result;
    })
    this.populatePost();

  }
  populatePost() {
       if(this.session.activities && this.session.activities.activities){
         let objj=this.session.activities.activities['feedSetting'];
        for(var k in objj){
            let imap=new MyMap();
            imap.key=k;
            imap.value=objj[k];
            this.myMap.push(imap);
          }
       }
      
    this.feed.createdBy = this.session.shareObj['user'];
    this.feed.tenantId = this.session.shareObj['wall'];
    this.httpService.getData("/Feed/wallId/"+this.session.shareObj['wall']).subscribe(
      result=>{
        this.feeds=result.feeds;
        if(result.users){

            this.session.users=result.users;
        }
        if(result.photos){

            this.session.photos=result.photos;
          
        }
      }
    )

  }
  savePost() {
    console.log(this.myMap);
    if(!this.feed.feedSetting){
      this.feed.feedSetting={};
    }
    for(var item of this.myMap){
      let result="true";
    
        if(!item.value){
          result="false";
        }
      
      this.feed.feedSetting[item.key]=result;
    }
    console.log(this.feed.feedSetting);
    this.feed.feedType = "NORMAL";
    this.loading = true;
    let inputEl: HTMLInputElement = this.inputEl.nativeElement;
    let fileCount: number = inputEl.files.length;
    if (fileCount > 0) {
      this.uploadImages();
    } else {
      this.apiService.postData("/Feed/" + this.session.shareObj['wall'], this.feed).subscribe((event) => {
        if(event){
          let data:any=event;
        if(!this.feeds){
          this.feeds=new Array<Feed>();
        }
        this.feeds.unshift(data);
        }
      });;
    }
  }

  updaetList() {

    let inputEl: HTMLInputElement = this.inputEl.nativeElement;
    let fileCount: number = inputEl.files.length;
    this.selectedPhotos = new Array();
    for (let i = 0; i < fileCount; i++) {
      this.selectedPhotos[i] = inputEl.files.item(i).name;
    }
  }
  uploadImages() {
    this.loading = true;
    let inputEl: HTMLInputElement = this.inputEl.nativeElement;
    let fileCount: number = inputEl.files.length;
    let formData = new FormData();
    if (fileCount > 0) {
      for (let i = 0; i < fileCount; i++) {
        formData.append('file', inputEl.files.item(i), inputEl.files.item(i).name);
      }
    }
    let userId = this.session.shareObj['user'];

    let headers = new Headers();
    headers.append('Content-Type', 'multipart/form-data');


    let url = '/Upload/feed/photos/' + userId + '/' + userId + '/' + this.session.shareObj['wall'];

    this.apiService.postData(url, formData).subscribe(
      (data1) => {
        //this.feeds.unshift(data1);
      },
      err => { console.log(err) },
      () => { this.populatePost(); }
    );

  }
}
