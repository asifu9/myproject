import { Component, OnInit, Input } from '@angular/core';
import { HttpService } from "../../shared/shared/httpService";
import { Form } from "../../shared/shared/models";
import { FormStateService } from "../widgets/form-state.service";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";

import { forkJoin as observableForkJoin } from 'rxjs';
@Component({
  selector: 'app-form-view',
  templateUrl: './form-view.component.html',
  styleUrls: ['./form-view.component.scss']
})
export class FormViewComponent implements OnInit {

  private _name: string = '';
  private _mode: string = '';
  private _inputId;
  form: Form;
  data: any = {};

  constructor(private notificationService: NotificationServiceService, private httpService: HttpService, private formStateService: FormStateService) { }
  isFetched = false;
  get name(): string {
    return this._name;
  }
  @Input()
  set name(name: string) {
    this._name = name;
  }

  get mode(): string {
    return this._mode;
  }
  @Input()
  set mode(mode: string) {
    this._mode = mode;
    this.fetchFormDetails();
  }

  get inputId(): string {
    return this._inputId;
  }
  @Input()
  set inputId(inputId: string) {
    this._inputId = inputId;
    this.fetchFormDetails();
  }


  ngOnInit() {
    this.fetchFormDetails()
  }

  fetchFormDetails() {
    this.notificationService.showProgressBar("", "");
    if (this.mode && this.name && this.mode == '1' && !this.isFetched) {
      //fetch forms data
      this.isFetched = true;
      this.httpService.getData("/form/name/" + this.name).subscribe(
        result => {
          this.form = result;
          this.notificationService.hideProgressBar();
        },
        error => {
          this.notificationService.hideProgressBar();
          this.notificationService.showErrorDialog(error);
        }
      );


    } else if (this.mode && this.name && this.mode == '2' && this.inputId && !this.isFetched) {
      this.isFetched = true;
      let tasks$ = [];
      tasks$.push(this.httpService.getData("/form/name/" + this.name));
      tasks$.push(this.httpService.getData("/customform/" + this.name + "/" + this.inputId));
      observableForkJoin(...tasks$).subscribe(
        results => {
          this.notificationService.hideProgressBar();
          if (results[0]) {
            this.form = results[0];
          }
          if (results[1]) {
            this.data = results[1];
            this.formStateService.updateOtherDetails(this.data);
          } else {
            this.data = {};
          }

        },
        error => {
          this.notificationService.hideProgressBar();
          this.notificationService.showErrorDialog(error);
        }
      );


    }
  }

}
