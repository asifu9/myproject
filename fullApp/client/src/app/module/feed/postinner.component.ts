import { Component, OnInit, Input, ViewChild, ViewContainerRef, ComponentFactoryResolver } from '@angular/core';

import { PostShowComponent } from './postshow.component';
import { Feed, User } from "../shared/shared/models";
import { SessionService } from "../shared/shared/SessionService";
import { HttpService } from "../shared/shared/httpService";

declare var jQuery: any;
@Component({
  selector: 'postinner',
  templateUrl: './postinner.component.html',
  styleUrls: ['./postinner.component.scss'],
  providers: []

})
export class PostInnerComponent implements OnInit {
  //2e12753c-d249-45ae-a1df-f42bc367f79a
  @Input()
  private post: Feed;
  @Input()
  private user: User;
  path1: string = '';
  copom: any;
  commented: boolean = false;
  private path: string = '/assets/uploads';
  imgSize: number = 0;

  constructor(private apiService: HttpService, private session: SessionService, protected componentResolver: ComponentFactoryResolver
  ) {
  }

  ngOnInit() {
    if (this.post != null && this.post != undefined) {
      let data = this.post;
      if (this.post.createdByObj != null && this.post.createdByObj.photo != null) {
        //this.post.createdByObj.photo = this.session.updatePhotoIcon(this.post.createdByObj.photo);
        this.path1 = this.post.createdByObj.photo.displayPath;
      }

    }

  }


}
