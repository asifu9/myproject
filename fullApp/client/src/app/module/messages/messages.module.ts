import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MessagesComponent } from './messages/messages.component';
import { MesdetailComponent } from './mesdetail/mesdetail.component';
import { SharedModule } from "../shared/shared.module";
import { MessageCommentCreateComponent } from '../messages/message-comments/message-comment-create/message-comment-create.component';
import { MessageCommentShowComponent } from '../messages/message-comments/message-comment-show/message-comment-show.component';
import { CreateMessageGroupComponent } from './create-message-group/create-message-group.component';
import { SearchOptionComponent } from './search-option/search-option.component';
import { MessageService } from "../messages/message.service";

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [MessagesComponent,MesdetailComponent, MessageCommentCreateComponent, MessageCommentShowComponent, CreateMessageGroupComponent, SearchOptionComponent],
  exports: [MessagesComponent,MesdetailComponent],
  providers:[MessageService],
  entryComponents:[CreateMessageGroupComponent,SearchOptionComponent]
})
export class MessagesModule { }
