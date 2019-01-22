import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { GroupfeedListComponent } from './groupfeedList.component';
import { Group } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";
@Component({
  selector: 'groupmiddle',
  templateUrl: './groupmiddle.component.html',
  styleUrls: ['./groupmiddle.component.scss'],
  providers: []
})
export class GroupMiddleComponent implements OnInit {
  groupId: string = '';
  group: Group;
  profilePhotoPath: string = '';
  path: string = '/assets/uploads';
  createdByPath: string = '';
  constructor(private apiService: HttpService, private router: Router, private rp: ActivatedRoute, private session: SessionService) {
    //this.groupId = this.rp.snapshot.params['id'];
    let data = this.router.url.split("/");
    if (data.length == 4) {
      this.groupId = data[data.length - 2];
    }
    //this.fetchGroup();

  }

  ngOnInit() {
    let data = this.router.url.split("/");
    if (data.length == 3) {
      this.groupId = data[data.length - 1];
    }
    //this.fetchGroup();
  }
  fetchGroup() {
    this.apiService.getData( "/Group/name/" + this.groupId).subscribe((data) => {

      this.group = data;
      if (this.group != null) {
        this.group.profilePhoto = this.session.updatePhoto(this.group.profilePhoto);

        this.profilePhotoPath = this.group.profilePhoto.displayPath;

        //ok cool
        this.group.createdByUser.photo = this.session.updatePhoto(this.group.createdByUser.photo);
        this.createdByPath = this.group.createdByUser.photo.displayPath;
      }
    });
  }

}
