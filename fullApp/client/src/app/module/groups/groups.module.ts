import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GroupsComponent } from './groups/groups.component';
import {GroupComponent} from './groups/group.component';
import {NewgroupComponent} from './groups/newgroup.component';
import { GroupfeedComponent } from './groupfeed/groupfeed.component';
import { GroupLeftComponent } from './groupfeed/groupleft.component';
import {GroupMiddleComponent} from './groupfeed/groupmiddle.component';
import {GroupfeedListComponent} from './groupfeed/groupfeedList.component';
import {GroupusersComponent} from './groupfeed/groupusers.component';
import { SharedModule } from "../shared/shared.module";
import { FeedModule } from "../feed/feed.module";
import { PeopleModule } from "../people/people.module";
@NgModule({
  imports: [
    SharedModule,
    FeedModule,
    PeopleModule
  ],
  declarations: [GroupsComponent,GroupComponent,NewgroupComponent,
  GroupfeedComponent,GroupLeftComponent,GroupMiddleComponent,GroupfeedListComponent,GroupusersComponent]
})
export class GroupsModule { }
