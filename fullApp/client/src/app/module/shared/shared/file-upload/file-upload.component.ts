import { Component, OnInit, Input, ElementRef, ViewChild, EventEmitter, Output } from '@angular/core';
import {User,Photo} from '../../shared/models';
import {LoaderComponent} from '../../shared/loader/loader.component';
import {SuccessComponent} from '../../shared/success/success.component';
import { Http } from "@angular/http";
import {SessionService} from '../../shared/SessionService';
import { HttpService } from "../httpService";



@Component({
  selector: 'fileUpload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss'],
  providers:[]
})
export class FileUploadComponent implements OnInit {

@Input()
single:boolean=false;
@Input()
type:string='';
@Input()
userId:string='';
items:string[]=[];
  @Input()
  user:User;
  loading:boolean=false;
   @ViewChild('fileInput') inputEl: ElementRef;
   @Output()
   uploadDone=new EventEmitter(false);
  constructor(private session:SessionService,private httpService:HttpService) { }

  ngOnInit() {

  }
  uploadImages(){
  this.loading=true;
      let inputEl: HTMLInputElement = this.inputEl.nativeElement;
      let fileCount: number = inputEl.files.length;
      let formData = new FormData();
       if (fileCount > 0) { // a file was selected
         this.items=[];
             for (let i = 0; i < fileCount; i++) {
              
             formData.append('file', inputEl.files.item(i), inputEl.files.item(i).name);
             }
       }  
       if(!this.userId){
         this.userId=this.session.shareObj['user'];
       }
       
     
 
        let url='';
      if(this.type=='personal'){
           url='/Upload/profile/photos/'+this.userId+'/'+this.userId+'/'+this.session.shareObj['wall'];
      }else if(this.type=='profile'){
           url='/Upload/backgroundprofile/photos/'+this.userId+'/'+this.userId+'/'+this.session.shareObj['wall'];
      }else if(this.type=='groupMessage'){
           url='/Upload/messagegroup/photos/'+this.userId+'/'+this.user.id+'/'+this.session.shareObj['wall'];
      }
       
         let headers = new Headers();
        headers.append('Content-Type', 'multipart/form-data');
        this.httpService.postDataWithOptions(url,formData,headers).subscribe(
                data => {this.uploadDone.emit(true);this.loading=false;},
                err => { console.log(err);this.uploadDone.emit(false);this.loading=false;},
                () =>{}
            );
    

  }

  updaetList(){
    let inputEl: HTMLInputElement = this.inputEl.nativeElement;
    let fileCount: number = inputEl.files.length;
     if (fileCount > 0) { // a file was selected
         this.items=[];
             for (let i = 0; i < fileCount; i++) {
               this.items.push(inputEl.files.item(i).name);
             }
     }
    
  }
}
