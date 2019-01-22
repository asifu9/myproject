import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PeoplesComponent } from "./peoples/peoples.component";
import { SharedModule } from "../shared/shared.module";
import { GroupListPeopleComponent } from '../people/group-list-people/group-list-people.component';

@NgModule({
  imports: [
    CommonModule,
    SharedModule
  ],
  exports:[
     PeoplesComponent,GroupListPeopleComponent
  ],
  declarations: [PeoplesComponent, GroupListPeopleComponent]
})
export class PeopleModule { }
