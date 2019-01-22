import { Component, OnInit, ViewChild, ViewContainerRef, ComponentFactoryResolver } from '@angular/core';
import { AddressComponent } from '../profile/items/address.component';
import { ProfessionComponent } from '../profile/items/profession.component';
import { EducationComponent } from '../profile/items/education.component';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { FileUploadComponent } from "../../shared/shared/file-upload/file-upload.component";
import { HttpService } from "../../shared/shared/httpService";
declare var jQuery: any;

@Component({
  selector: 'profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  providers: [FileUploadComponent]
})

export class ProfileComponent implements OnInit {
  @ViewChild("imgUploader1") mymodel;
  @ViewChild("container", { read: ViewContainerRef }) container;
  popupComp: any;
  userName: string = '';
  type: number = 1;
  user: User;
  showEditImage: boolean = false;
  showEditPersonalImage: boolean = false;
  constructor(private apiService: HttpService, private router: Router,
    private rp: ActivatedRoute, private session: SessionService, protected componentResolver: ComponentFactoryResolver,
    protected customComponentBuilder: FileUploadComponent) {
    let data = this.router.url.split("/");
    if (data.length == 3) {
      this.userName = data[data.length - 1];
    }
     if (data.length == 4) {
      this.userName = data[data.length - 2];
    }
    this.fetchUserDetails();
  }
  fetchUserDetails() {
    if(this.userName!=''){
      this.apiService.getData("/User/userName/" + this.userName).subscribe((data) => {

        this.user = data;
      //  this.user.backgroundPhoto = this.session.updatePhotolarge(this.user.backgroundPhoto);
        //this.user.photo = this.session.updatePhotoIcon(this.user.photo);
      });
    }
  }
  showProfilePicChange() {
    if (this.popupComp != null) {
      this.popupComp.destroy();
    }
    this.popupComp = this.container.createComponent(this.componentResolver.resolveComponentFactory(FileUploadComponent));
    this.popupComp.instance.user = this.user;
    this.popupComp.instance.type = 'profile';
    this.mymodel.show();
  }
  showUserPicChange() {
    if (this.popupComp != null) {
      this.popupComp.destroy();
    }
    this.popupComp = this.container.createComponent(this.componentResolver.resolveComponentFactory(FileUploadComponent));
    this.popupComp.instance.user = this.user;
    this.popupComp.instance.type = 'personal';
    this.mymodel.show();
  }
  hideEditIcon() {
    this.showEditImage = false;
  }
  showEditIcon() {
    if (this.user!=null && this.user.id == this.session.shareObj['user']) {
      this.showEditImage = true;
    }

  }
  hideEditPersonalIcon() {
    this.showEditPersonalImage = false;
  }
  showEditPersonalIcon() {
    if (this.user!=null && this.user.id == this.session.shareObj['user']) {
      this.showEditPersonalImage = true;
    }
  }
  ngOnInit() {
  }

  showAddress() {
    this.type = 1;
    jQuery("#addressItems").attr('class', 'active item');
    jQuery("#professionItems").attr('class', 'item');
    jQuery("#educationItems").attr('class', 'item');
  }
  showProfession() {
    this.type = 2;
    jQuery("#professionItems").attr('class', 'active item');
    jQuery("#addressItems").attr('class', 'item');
    jQuery("#educationItems").attr('class', 'item');
  }
  showEducation() {
    this.type = 3;

    jQuery("#addressItems").attr('class', 'item');
    jQuery("#professionItems").attr('class', 'item');
    jQuery("#educationItems").attr('class', 'active item');
  }

}
