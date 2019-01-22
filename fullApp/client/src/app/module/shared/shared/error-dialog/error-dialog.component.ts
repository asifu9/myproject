import { Component, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogActions, MatDialogContent } from "@angular/material";
import { Inject } from "@angular/core";
import {MatIconModule} from '@angular/material/icon';
import { TranslationService } from "../../shared/translate.service";

@Component({
  selector: 'app-error-dialog',
  templateUrl: './error-dialog.component.html',
  styleUrls: ['./error-dialog.component.css']
})
export class ErrorDialogComponent implements OnInit {


  message: string;
  title: string;
  
  error:any;
  constructor(private translation:TranslationService, public dialogRef: MatDialogRef<ErrorDialogComponent>,
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
    if(this.type && this.type.error){
      if(this.type.error && this.type.error.message){
        this.message=this.translation.getError(this.type.error.code);
      }else{
        this.message=this.translation.getError("delete-generic-message");
      }
    }
  }


}
