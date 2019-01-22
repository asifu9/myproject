import { Component, OnInit,ViewChild,ElementRef } from '@angular/core';
import { Group, User } from "../../shared/shared/models";
import { Http } from "@angular/http";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";
import { Router } from "@angular/router";

declare var jQuery:any;
@Component({
  selector: 'newgroup',
  templateUrl: './newgroup.component.html',
  styleUrls: ['./newgroup.component.scss']
})
export class NewgroupComponent implements OnInit {

  group:Group=new Group();
   @ViewChild('groupProfilePhoto') inputEl: ElementRef;
   @ViewChild('groupOtherPhoto')inputElGroup:ElementRef;
  constructor(private session:SessionService,private apiService:HttpService,private router:Router) {}
  users:User[]=[];
  ngOnInit() {
      jQuery('.ui.dropdown').dropdown();
        this.apiService.getData("/User/wall/" + this.session.shareObj['wall']+"/"+this.session.shareObj['user']).subscribe((data) => {
            this.users=data;
    });
  }

  saveGroup(){
    var allVals = '';;
    var access=jQuery('input[type="checkbox"]:checked').map(function() {
        allVals=this.value;
    });

    //other page code
          let inputEl: HTMLInputElement = this.inputEl.nativeElement;
      let fileCount: number = inputEl.files.length;
      let formData = new FormData();
       if (fileCount > 0) { // a file was selected
          /*  for (let i = 0; i < fileCount; i++) {
                formData.append('file[]', inputEl.files.item(i));

            }*/
             for (let i = 0; i < fileCount; i++) {
             formData.append('file', inputEl.files.item(i), '000-'+inputEl.files.item(i).name);
             }
       }  

               let inputElGroup: HTMLInputElement = this.inputElGroup.nativeElement;
      let fileCountGroup: number = inputElGroup.files.length;

       if (fileCountGroup > 0) { // a file was selected
          /*  for (let i = 0; i < fileCount; i++) {
                formData.append('file[]', inputEl.files.item(i));

            }*/
             for (let i = 0; i < fileCountGroup; i++) {
             formData.append('file', inputElGroup.files.item(i), '111-'+inputElGroup.files.item(i).name);
             }
       }  
       let userId=this.session.shareObj['user'];
       formData.append('name',this.group.name);
       formData.append('description',this.group.description);
       formData.append('uniqueName',this.group.groupUniqueName);
       formData.append('privacy',allVals);
       this.group.privacyStatus=allVals;
       let url='/Group/'+userId+'/'+this.session.shareObj['wall'];
       let headers = new Headers();
        headers.append('Content-Type', 'multipart/form-data');
       this.apiService.postDataWithOptions(url, formData,headers).subscribe(
                data => {},
                err => { console.log(err)},
                () =>{}
            );

  }

  changed(event){
      console.log(event);
  }

  cancel(){
      //home/group
      this.router.navigate(['home/group']);
  }

}
