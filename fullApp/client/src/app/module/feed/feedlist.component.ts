import { Component, OnInit ,Input} from '@angular/core';
import {PostComponent} from './post.component';
import { Feed, User } from "../shared/shared/models";
declare var jQuery: any;
@Component({
  selector: 'feedlist',
  templateUrl: './feedlist.component.html',
  styleUrls: ['./feedlist.component.scss'],
  providers:[]
})
export class FeedListComponent implements OnInit {

  @Input()
  private posts:Feed[];

  @Input() users:any;

  @Input() photos;any;
  constructor() { }
  @Input()
  user:User;
  ngOnInit() {
    console.log("user in feed list " + this.user);
  }

}
