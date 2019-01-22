import { Component, OnInit, Input } from '@angular/core';
import { User } from '../../shared/models';
import { SessionService } from '../../shared/SessionService';
import { HttpService } from "../httpService";
@Component({
  selector: 'app-userpop',
  templateUrl: './userpop.component.html',
  styleUrls: ['./userpop.component.scss']
})
export class UserpopComponent implements OnInit {

  constructor(private apiService: HttpService, private sessionService: SessionService) { }
  @Input()
  userId: string = '';
  userDetails: User;
  photoPath: string = '';
  path: string = '/assets/uploads';
  ngOnInit() {
    this.getUserDetails();
  }

  getUserDetails() {
    if (this.userId != '') {
      console.log("ok this is the problem");
      this.apiService.getData("/User/" + this.userId).subscribe((data) => {
        this.userDetails = data;
        if (null == this.userDetails.photoId) {
          this.photoPath = "/assets/profileimages/img1.jpg";
        } else {
          this.photoPath = this.path + '/' + this.userDetails.photo.createdBy + '/' + this.userDetails.photo.albumId + '/' + this.userDetails.photo.details[0].name;
        }
      });
    }
  }

}
