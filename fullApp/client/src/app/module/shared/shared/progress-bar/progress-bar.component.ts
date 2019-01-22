import { Component, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogActions, MatDialogContent } from "@angular/material";
import { Inject } from "@angular/core";

@Component({
  selector: 'app-progress-bar',
  templateUrl: './progress-bar.component.html',
  styleUrls: ['./progress-bar.component.css']
})
export class ProgressBarComponent implements OnInit {


  message: string;
  title: string;
  constructor() {
    //this.createMessage();
  }



  ngOnInit() {
    setTimeout(() => {
    this.createMessage();
    },0);
  }
  onNoClick(): void {
    //this.dialogRef.close();
  }

  createMessage() {
  }


}
