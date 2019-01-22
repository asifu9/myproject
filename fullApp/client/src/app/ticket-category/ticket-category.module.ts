import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TicketCategoryListComponent } from "./ticket-category-list/ticket-category-list.component";
import { TicketCategoryHomeComponent } from "./ticket-category-home/ticket-category-home.component";
import { TicketCategoryCreateComponent } from "./ticket-category-create/ticket-category-create.component";
import { SharedModule } from "../module/shared/shared.module";

@NgModule({
  imports: [
    CommonModule,
     SharedModule
  ],
  declarations: [TicketCategoryHomeComponent, TicketCategoryListComponent, TicketCategoryCreateComponent],
  exports :[TicketCategoryCreateComponent, TicketCategoryListComponent,TicketCategoryHomeComponent],
})
export class TicketCategoryModule { }
