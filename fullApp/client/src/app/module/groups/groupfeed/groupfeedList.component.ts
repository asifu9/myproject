import { Component, OnInit, ElementRef, ViewChild, Input } from '@angular/core';
import { Http } from "@angular/http";
import { FeedListComponent } from "../../feed/feedlist.component";
import { LoaderComponent } from "../../shared/shared/loader/loader.component";
import { SuccessComponent } from "../../shared/shared/success/success.component";
import { Feed, Group, User } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";
declare var jQuery: any;


@Component({
  selector: 'groupfeedList',
  templateUrl: './groupfeedList.component.html',
  styleUrls: ['./groupfeedList.component.scss'],
  providers: []
})
export class GroupfeedListComponent implements OnInit {
  //2e12753c-d249-45ae-a1df-f42bc367f79a
  private feed: Feed = new Feed();
  public feeds: Feed[];
  @ViewChild('fileInput') inputEl: ElementRef;
  oading: boolean = false;
  loading: boolean = false;
  savingMessage: string = 'Saving. Please wait..';
  selectedPhotos: string[];
  @Input() multiple: boolean = true;
  @Input()
  groupId: string;
  group: Group;
  user: User;
  //user:User;

  constructor(private apiService: HttpService, private session: SessionService) {
    console.log("ok i am hhhhh");
    this.apiService.getData("/User/" + this.session.shareObj['user']).subscribe((data) => {
      this.user = data
    });

  }

  ngOnInit() {
    this.apiService.getData("/Group/name/" + this.groupId).subscribe((data) => {
      this.group = data;
      this.groupId = this.group.id;
      this.populatePost();
    });

  }
  populatePost() {
    this.feed.createdBy = this.session.shareObj['user'];
    this.feed.tenantId = 1;
    this.apiService.getData("/Feed/wallId/" + this.groupId).subscribe((data) => {
      if (data) {
        this.feeds = data.feeds;
        this.session.users = data.users;
        this.session.photos = data.photos;
      }

    });
  }
  savePost() {
    this.feed.feedType = "NORMAL";
    this.feed.createdBy = this.session.shareObj['user'];
    this.loading = true;
    let inputEl: HTMLInputElement = this.inputEl.nativeElement;
    let fileCount: number = inputEl.files.length;
    if (fileCount > 0) {
      this.uploadImages();
    } else {
      this.apiService.postData("/Feed/" + this.groupId, this.feed).subscribe((data) => {
        this.populatePost();

      });;
    }
  }
  updaetList() {

    let inputEl: HTMLInputElement = this.inputEl.nativeElement;
    let fileCount: number = inputEl.files.length;
    this.selectedPhotos = new Array();
    for (let i = 0; i < fileCount; i++) {
      this.selectedPhotos[i] = inputEl.files.item(i).name;
    }
  }
  uploadImages() {
    this.loading = true;
    let inputEl: HTMLInputElement = this.inputEl.nativeElement;
    let fileCount: number = inputEl.files.length;
    let formData = new FormData();
    if (fileCount > 0) {
      for (let i = 0; i < fileCount; i++) {
        formData.append('file', inputEl.files.item(i), inputEl.files.item(i).name);
      }
    }
    let userId = this.session.shareObj['user'];
    let url = '/Upload/feed/photos/' + userId + '/' + userId + '/' + this.groupId;
    let headers = new Headers();
    headers.append('Content-Type', 'multipart/form-data');
    this.apiService.postDataWithOptions(url, formData, headers)
      .subscribe(
      (data1) => {
        //this.feeds.unshift(data1);
      },
      err => { console.log(err) },
      () => { this.populatePost(); }
      );


  }
}
