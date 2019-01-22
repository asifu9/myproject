import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationComponent } from './notification/notification.component';
import { ReqNotifyComponent } from './notification/req-notify/req-notify.component';
import { LikeNotifyComponent } from './notification/like-notify/like-notify.component';
import { SharedModule } from "../shared/shared.module";
@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [NotificationComponent,ReqNotifyComponent,LikeNotifyComponent],
  exports: [NotificationComponent,ReqNotifyComponent,LikeNotifyComponent]
})
export class NotificationModule { }
