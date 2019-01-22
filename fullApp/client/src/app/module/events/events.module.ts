import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventsHomeComponent } from '../events/events-home/events-home.component';
import { EventsListComponent } from '../events/events-list/events-list.component';
import { EventsCreateComponent } from '../events/events-create/events-create.component';
import { SharedModule } from "../shared/shared.module";

@NgModule({
  imports: [
    CommonModule,
    SharedModule
  ],
  exports:[EventsHomeComponent],
  declarations: [EventsHomeComponent, EventsListComponent, EventsCreateComponent]
})
export class EventsModule { }
