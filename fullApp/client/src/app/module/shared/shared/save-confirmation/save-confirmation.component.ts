import { Component, OnInit } from '@angular/core';
import { Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogActions, MatDialogContent } from "@angular/material";
import { TranslationService } from "../../../shared/shared/translate.service";
@Component({
  selector: 'app-save-confirmation',
  templateUrl: './save-confirmation.component.html',
  styleUrls: ['./save-confirmation.component.css']
})
export class SaveConfirmationComponent implements OnInit {

  message: string;
  title: string;
  constructor(private translation:TranslationService,public dialogRef: MatDialogRef<SaveConfirmationComponent>,
    @Inject(MAT_DIALOG_DATA) public type: any) {
    console.log("initialized data ok cool inconstructoer  " + this.type);
    this.createMessage();
  }



  ngOnInit() {
    console.log(this.type);
    this.createMessage();
  }
  onNoClick(): void {
    this.dialogRef.close();
  }

  createMessage() {
    if (this.type) {
      if (this.type.createUpdate == 'create') {
        this.title = "Saved !!!";
        this.translation.getAsyn("saveMessage").subscribe(result=>{
          this.translation.getAsyn(this.type.type).subscribe(result2=>{
            this.message=result2+ result;
            })
        })

        // if (this.type.type == 'leave') {
        //   this.message = "Leave details saved Successfully";
        // } else if (this.type.type == 'user') {
        //   this.message = "User details saved Successfully";
        // } else if (this.type.type == 'designation') {
        //   this.message = "Designation details saved Successfully";
        // } else if (this.type.type == 'department') {
        //   this.message = "Department details saved Successfully";
        // } else if (this.type.type == 'team') {
        //   this.message = "Team details saved Successfully";
        // } else if (this.type.type == 'lov') {
        //   this.message = "List of Value details saved Successfully";
        // } else if (this.type.type == 'ticketCategory') {
        //   this.message = "Ticket Category details saved Successfully";
        // } else if (this.type.type == 'role') {
        //   this.message = "Role details saved Successfully";
        // } else if (this.type.type == 'project') {
        //   this.message = "Project details saved Successfully";
        // } else if (this.type.type == 'event') {
        //   this.message = "Event details saved Successfully";
        // } else if (this.type.type == 'leavetype') {
        //   this.message = "Leave Type details saved Successfully";
        // } else if (this.type.type == 'ticket') {
        //   this.message = "Ticket details saved Successfully";
        // } else if (this.type.type == 'album') {
        //   this.message = "Album details saved Successfully";
        // }

      }else if(this.type.createUpdate=="delete"){
        this.translation.getAsyn("deletMessage").subscribe(result=>{
          this.message=result;
        });

      } else if(this.type.create=="upload") {
            this.translation.getAsyn("uploadMessage").subscribe(result=>{
          this.translation.getAsyn(this.type.type).subscribe(result2=>{
            this.message=result2+ result;
            })
        })


      }else{
        this.title = "Updated !!!";
        this.translation.getAsyn("updateMessage").subscribe(result=>{
          this.translation.getAsyn(this.type.type).subscribe(result2=>{
            this.message=result2+ result;
            })
        })
      }
    }
  }


}
