import { Component, OnInit, Input, ViewChild, ElementRef } from '@angular/core';
import { Http } from "@angular/http";
import { Router, ActivatedRoute } from '@angular/router';
import { PhotoItemComponent } from './photoitem.component';
import { LoaderComponent } from "../../shared/shared/loader/loader.component";
import { UploadFile, Photo, PhotoAlbum } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";
import { forkJoin as observableForkJoin } from 'rxjs';

declare var jQuery: any;
@Component({
  selector: 'photos',
  templateUrl: './photos.component.html',
  styleUrls: ['./photos.component.scss'],
  providers: [PhotoItemComponent, LoaderComponent]
})
export class PhotosComponent implements OnInit {

  private uploadObj: UploadFile = new UploadFile();
  @Input() multiple: boolean = true;
  @ViewChild('fileInput') inputEl: ElementRef;
  private albumId: string = '';
  private photos: Photo[];
  private albumInfo: PhotoAlbum;
  //loading: boolean = false;
  savingMessage: string = 'Saving. Please wait..';
  selectedPhotos: string[];
  constructor(private session: SessionService, public route: ActivatedRoute,
    private apiService: HttpService, private notificationService: NotificationServiceService) {
    this.route
      .params
      .subscribe(params => {
        // Récupération des valeurs de l'URL
        this.albumId = params['id']; // --> Name must match wanted paramter
      });

  }

  ngOnInit() {
    this.populateNow();
  }

  populateNow() {
    this.notificationService.showProgressBar();
    this.apiService.getData("/Albums/albums/" + this.albumId)
    let tasks$ = [];
    tasks$.push(this.apiService.getData("/Albums/albums/" + this.albumId));
    tasks$.push(this.apiService.getData("/Albums/photos/" + this.albumId));

    observableForkJoin(...tasks$).subscribe(results => {
      this.notificationService.hideProgressBar();
      if (results && results.length > 0) {
        this.albumInfo = results[0]
        this.photos = results[1];
      }


    }, error => {
      this.notificationService.hideProgressBar();
      this.notificationService.showErrorDialog(error[0]);
    }
    );



  }
  refreshMe(id){
    console.log("deleted id " + id);

   this.photos=  this.photos.filter(i=>i.id!=id);
  }
  updaetList() {

    let inputEl: HTMLInputElement = this.inputEl.nativeElement;
    let fileCount: number = inputEl.files.length;
    this.selectedPhotos = new Array();
    for (let i = 0; i < fileCount; i++) {
      this.selectedPhotos[i] = inputEl.files.item(i).name;
    }
  }
  uploadImages(file) {

    //this.loading = true;
    let inputEl: HTMLInputElement = this.inputEl.nativeElement;
    let fileCount: number = inputEl.files.length;
    if (fileCount == 0) {
      return;
    }
    this.notificationService.showProgressBar();
    let formData = new FormData();
    if (fileCount > 0) { // a file was selected
      for (let i = 0; i < fileCount; i++) {
        formData.append('file', inputEl.files.item(i), inputEl.files.item(i).name);
      }
    }
    let userId = this.session.shareObj['user'];


    let url = '/Upload/photos/' + userId + '/' + this.albumId;
    let headers = new Headers();
    headers.append('Content-Type', 'multipart/form-data');
    this.apiService.postDataWithOptions(url, formData, headers).subscribe(
      data => { },
      err => {
        this.notificationService.hideProgressBar();
        this.notificationService.showErrorDialog(err);
      },
      () => {
        this.notificationService.hideProgressBar();
        this.notificationService.showSaveDailog("photos","upload",false);
        this.populateNow();
      }
    );
  }

  upload() {

  }

}
