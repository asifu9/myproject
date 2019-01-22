import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PhotoAlbum } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";
import { HttpEventType } from "@angular/common/http";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";
import { MatRadioModule } from '@angular/material/radio';


@Component({
  selector: 'photoalbum',
  templateUrl: './photoalbum.component.html',
  styleUrls: ['./photoalbum.component.scss']
})
export class PhotoalbumComponent implements OnInit {
  private id: string = '';
  private album: PhotoAlbum = new PhotoAlbum();
  isCreateMode:boolean=true;
  constructor(private notificationServiceService: NotificationServiceService, private apiService: HttpService,
    private router: Router, private rp: ActivatedRoute, private session: SessionService) {
    this.id = this.rp.snapshot.params['id'];
    if(this.id){
      this.isCreateMode=false;
    }
  }
  hereIam() {
    console.log("ok got me");
  }
  private textareaValue = '';
  doTextareaValueChange(ev) {
    try {
      this.textareaValue = ev.target.value;
    } catch (e) {
      console.info('could not set textarea-value');
    }
  }

  ngOnInit() {

  }

  save() {

    if (!this.session.isActivityExists('album', 'createalbum')) {
      return;
    }

    this.album.description = this.textareaValue;
    //this.user.dob=this.parseDate(""+this.user.dob);
    this.album.createdBy = this.session.shareObj['user'];
    this.notificationServiceService.showProgressBar();
    this.apiService.postData("/Albums/", this.album).subscribe((event) => {
      if (event) {
        let data: any = event;
        this.notificationServiceService.hideProgressBar();
        this.notificationServiceService.showSaveDailog("album", "create", false);
        this.router.navigate(['/home/albums/photos/', data.id]);

      }

    },error=>{
      this.notificationServiceService.hideProgressBar();
      this.notificationServiceService.showErrorDialog(error);
    });;
  }


  cancel(){
    this.router.navigate(['/home/albums']);
  }

}
