import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddressesComponent } from "./addresses/addresses.component";
import { SharedModule } from "../shared/shared.module";
import { AdminComponent } from "./admin/admin/admin.component";
import { CompanyComponent } from "./admin/company/company.component";
import { CompanyLeftComponent } from "./admin/company/compLeft.component";
import { SelectedCompanyComponent } from "./admin/company/selectedcompany.component";
import { LoginComponent } from "./login/login.component";
import { UsersComponent } from "./admin/users/users.component";
import { NewUserComponent } from "./admin/users/newuser.component";
import { FormModule } from "../form/form.module";
import { CompanyCreateComponent } from './admin/company-create/company-create.component';

@NgModule({
  imports: [
    SharedModule,
    FormModule
  ],
  declarations: [AddressesComponent,AdminComponent,CompanyComponent,CompanyLeftComponent,SelectedCompanyComponent,LoginComponent,UsersComponent,NewUserComponent, CompanyCreateComponent],
  exports: [AddressesComponent,AdminComponent,CompanyComponent,CompanyLeftComponent,SelectedCompanyComponent,LoginComponent,UsersComponent,NewUserComponent]
})
export class AdminModule { }
