import { Component, OnInit } from '@angular/core';
import { Input } from "@angular/core";
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { HttpService } from "../../shared/shared/httpService";
import { Router } from "@angular/router";
import { SessionService } from "../../shared/shared/SessionService";
import { ActivatedRoute } from "@angular/router";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";
import { User, ReportMeta } from "../../shared/shared/models";
import { Inject } from "@angular/core";

@Component({
  selector: 'app-search-option',
  templateUrl: './search-option.component.html',
  styleUrls: ['./search-option.component.css']
})
export class SearchOptionComponent implements OnInit {

  constructor(public dialog: MatDialog,
    private httpService: HttpService, 
    private router: Router, 
    private session: SessionService,
    private activatedRoute: ActivatedRoute, 
    private notificaitonSerivce: NotificationServiceService,
    public dialogRef: MatDialogRef<SearchOptionComponent>,
    @Inject(MAT_DIALOG_DATA) public inputData: any) { }


  users:User[]=[];
  groups:MessageChannel[]=[];
  metaUser: ReportMeta[] = [];
  metaGroup: ReportMeta[] = [];
  ngOnInit() {
    this.fetchData();
  }

  fetchData(){
    if(this.inputData.searchType=='user'){
      this.notificaitonSerivce.showProgressBar();
      this.httpService.getData("/User/wall/"+this.session.shareObj['wall']).subscribe(
        result=>{
            this.metaUser = [];
            this.metaUser.push(new ReportMeta("photoPath", "photoPath", undefined, "image"));
            this.metaUser.push(new ReportMeta("name", "name"));
            this.metaUser.push(new ReportMeta("", "id", undefined, "select"));
          this.notificaitonSerivce.hideProgressBar();
          this.users= result;

        },error=>{
          this.notificaitonSerivce.hideProgressBar();
          this.notificaitonSerivce.showErrorDialog(error);
        }
      )
    }else if(this.inputData.searchType=='group'){
       this.httpService.getData("/MessageChannel/tenant/"+this.session.shareObj['wall']).subscribe(
        result=>{
           this.metaUser = [];
            this.metaUser.push(new ReportMeta("photoPath", "photo", undefined, "imageObj"));
            this.metaUser.push(new ReportMeta("name", "name"));
            this.metaUser.push(new ReportMeta("", "id", undefined, "select"));
          this.notificaitonSerivce.hideProgressBar();
          this.groups= result;
        },error=>{
          this.notificaitonSerivce.hideProgressBar();
          this.notificaitonSerivce.showErrorDialog(error);
        }
      )
    }
  }

  updateParentUser(event){
    this.dialogRef.close(event);
  }

  updateParentGroups(event){
    this.dialogRef.close(event);
  }

}
