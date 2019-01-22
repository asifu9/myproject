import { Component, OnInit,Input } from '@angular/core';
import { WidgetMetaData, Form } from "../../shared/shared/models";
import { FormStateService } from "./form-state.service";
import { Location } from '@angular/common';
@Component({
  selector: 'app-widget-wrapper',
  templateUrl: './widget-wrapper.component.html',
  styleUrls: ['./widget-wrapper.component.scss']
})
export class WidgetWrapperComponent implements OnInit {
 private _form:Form;
 private _data:any;
 private _mode:string;
 

     get mode(): string {
        return this._mode;
    }
    @Input()
    set  mode(mode: string) {

        this._mode = mode;
        console.log("mode is " + this.mode);
    }
    get form(): Form {
        return this._form;
    }
    @Input()
    set form(form: Form) {
        this._form = form;
    }

     get data(): any {
        return this._data;
    }
    @Input()
    set data(data: any) {
        this._data = data;
    }

  constructor(private formStateService:FormStateService,private location: Location) { }

  ngOnInit() {
  }

  saveState(){
    this.formStateService.saveState(this.form.name);
    // this.location.back();
  }

   updateState(){
     this.formStateService.updateState(this.form.name);
    // this.location.back();
   }

   cancelOperation(){
       this.location.back();
   }

}
