import { Component, OnInit, Input, AfterContentInit, AfterViewInit, ViewChild, ViewContainerRef, ComponentFactoryResolver } from '@angular/core';
import { User, FriendRequest } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { UserpopComponent } from "../../shared/shared/userpop/userpop.component";
import { HttpService } from "../../shared/shared/httpService";
declare var jQuery: any;
@Component({
  selector: 'app-group-list-people',
  templateUrl: './group-list-people.component.html',
  styleUrls: ['./group-list-people.component.scss']
})
export class GroupListPeopleComponent implements OnInit {

  private path: string = '';
  sss: string = '.test';
  users: User[] = new Array<User>();
  private _groupName: string;
  @ViewChild("userModel") mymodel;
  @ViewChild("container", { read: ViewContainerRef }) container;
  copom: any;

  get groupName():string{
    return this._groupName;
    
  }

@Input()
  set groupName(groupName:string){
    this._groupName=groupName;
    this.fetchPeople();

  }
  //Users/i340632/personal/angular/social/src/app/module/people/peoples/peoples.component.ts
  constructor(private apiService: HttpService, private session: SessionService, private componentResolver: ComponentFactoryResolver) {
    
  }

  ngOnInit() {
   
  }

  fetchPeople(){
     this.path = this.session.shareObj['url'] + "/uploads";
    this.apiService.getData("/Group/listuser/" + this.session.shareObj['wall'] + "/" + this.groupName).subscribe((data) => {
      this.users = data;
    });
  }

  popupMe(id, obj) {
    if (this.copom != null) {
      this.copom.destroy();
    }
    this.copom = this.container.createComponent(this.componentResolver.resolveComponentFactory(UserpopComponent));
    this.copom.instance.userId = id;
    jQuery("#temp").css('position', 'fixed');
    var x = parseInt(obj.screenX) - 500;
    jQuery("#temp").css('left', obj.clientX);
    jQuery("#temp").css('top', obj.clientY - 10);

    this.mymodel.show();
  }
  closeMe() {
    // this.mymodel.hide();
  }
  over() {
    jQuery(this.sss)
      .dimmer({
        on: 'hover'
      });
  }
  ngAfterContentInit() {

  }
  ngAfterViewInit() {

  }
  addFriendRequest(item) {
    let friend: FriendRequest = new FriendRequest();
    friend.tenantId = this.session.shareObj['wall'];
    friend.requestedFrom = this.session.shareObj['user'];
    friend.requestedTo = item.id;
    this.apiService.postData("/FriendRequest/", friend).subscribe((data) => {
      item.requestStatus = "REQUESTED";
    });;
  }
  followYou(item) {
    this.apiService.postData("/Follower/" + this.session.shareObj['user'] + "/" + this.session.shareObj['wall'], item.id).subscribe((data) => {
      item.follow = true;
    });
  }
  accept(i) {
    this.apiService.putData("/FriendRequest/UPDATE/ACCEPTED", i.friendRequest).subscribe((data) => {

      i.friendRequest = data;
      //i.requestStatus = data.status;
    });
  }
  reject(i) {
    this.apiService.putData("/FriendRequest/UPDATE/REJECTED", i.friendRequest).subscribe((data) => {

      i.friendRequest = data;
      //i.requestStatus = data.status;
    });
  }
}
