import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CommentsComponent } from './comments/comments.component';
import { FormsModule } from "@angular/forms";
import { BrowserModule } from '@angular/platform-browser';
import { CommentComponent} from './comments/comment.component';
import { PhotoModule } from "../photo/photo.module";
import { SharedModule } from "../shared/shared.module";
@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    FormsModule,
    SharedModule
  ],
  declarations: [CommentsComponent,CommentComponent
  ],
  exports:[CommentsComponent,CommentComponent]
})
export class CommentsModule { }
