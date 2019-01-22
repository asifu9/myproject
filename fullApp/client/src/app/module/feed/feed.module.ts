import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FeedListComponent } from './feedlist.component';
import { PostComponent } from './post.component';
import { PostInnerComponent } from './postinner.component';
import {PostShareComponent} from './postshare.component';
import { FeedsComponent } from "./feeds.component";
import { PostShowComponent } from "./postshow.component";
import { SharedModule } from "../shared/shared.module";
import { CommentsModule } from "../comments/comments.module";
import { AppModule } from "../../app.module";
import { PeopleModule } from "../people/people.module";
@NgModule({
  imports: [
    SharedModule,
    CommentsModule,
    PeopleModule
  ],
  declarations: [
    FeedListComponent,
    FeedsComponent,
    PostComponent,
    PostInnerComponent,
    PostShowComponent,
    PostShareComponent
    
    ],
    exports:[
       FeedListComponent,
    FeedsComponent,
    PostComponent,
    PostInnerComponent,
    PostShowComponent,
    PostShareComponent
    ]
})
export class FeedModule { }
