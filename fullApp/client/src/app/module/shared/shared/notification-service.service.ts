import { Injectable } from '@angular/core';
import { Location } from '@angular/common';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SaveConfirmationComponent } from "../shared/save-confirmation/save-confirmation.component";
import { MatDialog } from "@angular/material";
import { ProgressBarComponent } from "../shared/progress-bar/progress-bar.component";
import { ErrorDialogComponent } from "../../shared/shared/error-dialog/error-dialog.component";

@Injectable({
  providedIn: 'root'
})
export class NotificationServiceService {
progressShow: boolean = false;
  constructor(public dialog: MatDialog, private location: Location) { }
  progress: any;
  showSaveDailog(type, createUpdate, useLocatonBack) {

    const dialogRef = this.dialog.open(SaveConfirmationComponent, { height: '150px',
  width: '500px',
      data: { type: type, createUpdate: createUpdate }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (useLocatonBack) {
        this.location.back();
      }
    });
  }

  showProgressBar(type?:string, message?:string) {
    if (!this.progressShow) {
      this.progressShow = true;
      setTimeout(()=>{
          this.progress = this.dialog.open(ProgressBarComponent, {
         disableClose: true
      });
      },0);
    
    }

  }

  showErrorDialog(error){
    const dialogRef = this.dialog.open(ErrorDialogComponent, {
      data: { error: error}
    });
    dialogRef.afterClosed().subscribe(result => {
    });
  }

  hideProgressBar() {
    this.progressShow = false;
    this.progress.close();
    
  }

}
