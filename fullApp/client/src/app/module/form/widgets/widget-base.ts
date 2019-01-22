import { Injectable, Component } from '@angular/core';
import { HttpService } from "../../shared/shared/httpService";
import { SessionService } from "../../shared/shared/SessionService";
import { WidgetMetaData } from "../../shared/shared/models";
import { BehaviorSubject } from "rxjs";

@Component({
  selector: 'af-base-widget',
  template: 'Base widget template'
})
export class WidgetBase {
  
    disabledValue:boolean=false;
    hideValue:boolean=false;
    isWidgetValid:boolean=true;
    required:boolean=false;
    errors:string[]=[];
    temp:any;
    //
    modeValue:string;
    widgetValue:WidgetMetaData;

  constructor() { 
    
  }
  setBaseRef(tempRef:any){
     this.temp=tempRef;
  }
  validate(){
    if(this.temp){
      this.temp.validate();
    }
    return "success";
  }
  setMode(modeValue){
      this.modeValue=modeValue;
  }
  setWidget(widgetValue){
    this.widgetValue=widgetValue;
  }
  updateValues(){
         if(this.widgetValue){
            this.disabledValue=((this.widgetValue.flowMode[this.modeValue]==true)?false:true);
            this.hideValue=this.widgetValue.hidden;
            this.required=this.widgetValue.required;
        }
  }


}