import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LeaveDetailsComponent } from '../leaves/leave-details/leave-details.component';
import { LeaveCreateComponent } from '../leaves/leave-create/leave-create.component';
import { SharedModule } from "../shared/shared.module";
import { LeaveManageComponent } from '../leaves/leave-manage/leave-manage.component';

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [LeaveDetailsComponent, LeaveCreateComponent, LeaveManageComponent]
})
export class LeavesModule {
  
 }
