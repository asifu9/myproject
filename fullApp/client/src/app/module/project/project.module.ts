import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectCreateComponent } from './project-create/project-create.component';
import { ProjectListComponent } from './project-list/project-list.component';
import { ProjectHomeComponent } from './project-home/project-home.component';
import { SharedModule } from "../shared/shared.module";
import { FormModule } from "../form/form.module";

@NgModule({
  imports: [
    SharedModule,
    FormModule
  ],
  exports:[ProjectHomeComponent,ProjectCreateComponent,ProjectListComponent],
  declarations: [ProjectCreateComponent, ProjectListComponent, ProjectHomeComponent]
})
export class ProjectModule { }
