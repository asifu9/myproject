import { Component, OnInit } from '@angular/core';
import { PhotoAlbum } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";

@Component({
  selector: 'photoalbums',
  templateUrl: './photoalbums.component.html',
  styleUrls: ['./photoalbums.component.scss']
})
export class PhotoalbumsComponent implements OnInit {


private path:string=this.session.shareObj['url']+'/uploads';
  public users: PhotoAlbum[];
  constructor(private notificationService:NotificationServiceService,private apiService: HttpService, private session: SessionService) { }

  ngOnInit() {
    this.session.updateMenu("photoAlbums");
    let userId = this.session.shareObj['user'];
    this.notificationService.showProgressBar();
    this.apiService.getData("/Albums/user/" + userId).subscribe((data) => {
      this.users = data
      if(this.users && this.users.length>0){
        this.users.forEach(i=>{
          if(i.photo){
            i.photoPath=this.path+"/"+i.photo.createdBy+"/"+i.photo.albumId+"/"+i.photo.path;
          }else{
            i.photoPath=this.session.defaultPhotoAlbum;
          }
        })
      }
      this.notificationService.hideProgressBar();
    },error=>{
      this.notificationService.hideProgressBar();
      this.notificationService.showErrorDialog(error);
    });
  }

}
