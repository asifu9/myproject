import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminSettingComponent } from '../admin-setting/admin-setting.component';
import { SharedModule } from "../shared/shared.module";
import { DepartmentComponent } from '../admin-setting/department/department.component';
import { DesignationComponent } from '../admin-setting/designation/designation.component';
import { OrganizationComponent } from '../admin-setting/organization/organization.component';
import { DepartmentCreateComponent } from '../admin-setting/department-create/department-create.component';
import { OrganizationCreateComponent } from '../admin-setting/organization-create/organization-create.component';
import { DesignationCreateComponent } from '../admin-setting/designation-create/designation-create.component';
import { AdminService } from './admin.service';
import { UserComponent } from '../admin-setting/user/user.component';
import { UserListComponent } from '../admin-setting/user/user-list/user-list.component';
import { UserCreateComponent } from "../admin-setting/user/user-create/user-create.component";
import { UserBasicComponent } from '../admin-setting/user/user-create/user-basic/user-basic.component';
import { UserPhotoComponent } from '../admin-setting/user/user-create/user-photo/user-photo.component';
import { UserLinkComponent } from '../admin-setting/user/user-create/user-link/user-link.component';
import { UserSettingComponent } from '../admin-setting/user/user-create/user-setting/user-setting.component';
import { UserCredentialComponent } from '../admin-setting/user/user-create/user-credential/user-credential.component';
import { UserDetailsComponent } from '../admin-setting/user/user-create/user-details/user-details.component';
import { TeamComponent } from '../admin-setting/team/team.component';
import { TeamCreateComponent } from '../admin-setting/team-create/team-create.component';
import { UserActivitiesComponent } from "./user/user-create/user-activities/user-activities.component";
import { LovHomeComponent } from '../admin-setting/lov/lov-home/lov-home.component';
import { LovCreateComponent } from '../admin-setting/lov/lov-create/lov-create.component';
import { LovListComponent } from '../admin-setting/lov/lov-list/lov-list.component';
import { FormModule } from "../form/form.module";
import { RolesComponent } from '../admin-setting/roles/roles.component';
import { RoleCreateComponent } from '../admin-setting/role-create/role-create.component';
import { UserHeaderComponent } from '../admin-setting/user/user-create/user-header/user-header.component';
import { UserMapActionComponent } from '../admin-setting/user/user-create/user-map-action/user-map-action.component';
import { LeaveTypeComponent } from '../admin-setting/leave-type/leave-type.component';
import { LeaveTypeCreateComponent } from '../admin-setting/leave-type-create/leave-type-create.component';
import { CompanySettingComponent } from '../admin-setting/company-setting/company-setting.component';
import { DisplaySettingComponent } from './display-setting/display-setting.component';

@NgModule({
  imports: [
    SharedModule,
    FormModule
  ],
  exports :[AdminSettingComponent,DepartmentComponent,UserComponent,UserCreateComponent, DesignationComponent,TeamComponent, TeamCreateComponent, OrganizationComponent,DepartmentCreateComponent, OrganizationCreateComponent, DesignationCreateComponent,DisplaySettingComponent,UserListComponent,UserActivitiesComponent,LovHomeComponent, LovCreateComponent, LovListComponent],
  declarations: [AdminSettingComponent,DisplaySettingComponent, DepartmentComponent,UserComponent,UserCreateComponent, DesignationComponent, OrganizationComponent, DepartmentCreateComponent, OrganizationCreateComponent, DesignationCreateComponent, UserListComponent, UserBasicComponent, UserPhotoComponent, UserLinkComponent, UserSettingComponent, UserCredentialComponent, UserDetailsComponent, TeamComponent, TeamCreateComponent, UserActivitiesComponent, LovHomeComponent, LovCreateComponent, LovListComponent, RolesComponent, RoleCreateComponent, UserHeaderComponent, UserMapActionComponent, LeaveTypeComponent, LeaveTypeCreateComponent, CompanySettingComponent, DisplaySettingComponent],
  providers: [AdminService]
})
export class AdminSettingModule { }
