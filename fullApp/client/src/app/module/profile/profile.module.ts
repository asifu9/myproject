import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from "./profile/profile.component";
import { UserComponent } from "./userprofile/user.component";
import { SharedModule } from "../shared/shared.module";
import { EducationComponent } from "./profile/items/education.component";
import { AddressComponent } from "./profile/items/address.component";
import { ProfessionComponent } from "./profile/items/profession.component";
import { AdminModule } from "../admin/admin.module";

@NgModule({
  imports: [
    SharedModule,
    AdminModule
  ],
  declarations: [ProfileComponent,UserComponent,EducationComponent,AddressComponent,ProfessionComponent],
  exports: [ProfileComponent,UserComponent,EducationComponent,AddressComponent,ProfessionComponent]
})
export class ProfileModule { }
