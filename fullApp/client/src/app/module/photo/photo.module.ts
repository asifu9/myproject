import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PhotosComponent } from './photos/photos.component';
import { PhotoalbumsComponent } from './photoalbums/photoalbums.component';
import { PhotoalbumComponent } from './photoalbums/photoalbum.component';
import {PhotoItemComponent} from './photos/photoitem.component';
import { PhotoComponent } from './photos/photo.component';
import {PhotoCommentsComponent} from './photocomments/photocomments.component';
import {PhotoCommentComponent} from './photocomments/photocomment.component';
import { FormsModule } from "@angular/forms";
import { BrowserModule } from '@angular/platform-browser';
import { CommentsModule } from "../comments/comments.module";
import { UserslideComponent } from "./userslide/userslide.component";
import { SliderComponent } from "./slider/slider.component";
import { SliderPhotoComponent } from "./slider/sliderphoto.component";
import { SharedModule } from "../shared/shared.module";
@NgModule({
  imports: [
    
    SharedModule,
    CommentsModule
  
    
  ],
  entryComponents:[SliderComponent],
  exports:[UserslideComponent,SliderPhotoComponent,SliderComponent,PhotoCommentComponent,PhotoCommentsComponent,PhotosComponent,PhotoComponent,PhotoItemComponent,PhotoalbumsComponent,PhotoalbumComponent],
  declarations: [UserslideComponent,SliderPhotoComponent,SliderComponent,PhotoCommentComponent,PhotoCommentsComponent,PhotosComponent,PhotoComponent,PhotoItemComponent,PhotoalbumsComponent,PhotoalbumComponent]
})
export class PhotoModule { }
