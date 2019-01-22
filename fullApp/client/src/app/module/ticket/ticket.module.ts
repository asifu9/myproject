import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TicketCreateComponent } from '../ticket/ticket-create/ticket-create.component';
import { TicketListComponent } from '../ticket/ticket-list/ticket-list.component';
import { TicketHomeComponent } from '../ticket/ticket-home/ticket-home.component';
import { SharedModule } from "../shared/shared.module";
import { TicketAssignComponent } from '../ticket/ticket-assign/ticket-assign.component';

@NgModule({
  imports: [
    CommonModule,
    SharedModule
  ],
  declarations: [TicketCreateComponent, TicketListComponent, TicketHomeComponent, TicketAssignComponent],
  exports:[TicketCreateComponent, TicketListComponent, TicketHomeComponent,TicketAssignComponent]
  
})
export class TicketModule { }
