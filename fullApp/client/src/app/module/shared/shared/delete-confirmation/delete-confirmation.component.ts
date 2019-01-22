import { Component, OnInit } from '@angular/core';
import { Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA,MatDialogActions,MatDialogContent } from "@angular/material";
@Component({
  selector: 'app-delete-confirmation',
  templateUrl: './delete-confirmation.component.html',
  styleUrls: ['./delete-confirmation.component.css']
})
export class DeleteConfirmationComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<DeleteConfirmationComponent>,
    @Inject(MAT_DIALOG_DATA) public deleteConfirmation: boolean) { }

  ngOnInit() {
  }
   onNoClick(): void {
    this.dialogRef.close();
  }
  yesClicked(){
    this.deleteConfirmation=true;
     this.dialogRef.close(true);
  }

}
