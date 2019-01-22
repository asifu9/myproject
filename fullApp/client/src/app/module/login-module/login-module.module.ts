import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginPageComponent } from '../login-module/login-page/login-page.component';
import { SharedModule } from "../shared/shared.module";

@NgModule({
  imports: [
    CommonModule,
    SharedModule
  ],
  declarations: [LoginPageComponent],
  exports:[LoginPageComponent]
})
export class LoginModuleModule { }
