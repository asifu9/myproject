import { Component, OnInit,Input } from '@angular/core';

import { BehaviorSubject } from "rxjs";
import { WidgetBase } from "../../widget-base";
import { FormStateService } from "../../form-state.service";
import { HttpService } from "../../../../shared/shared/httpService";
import { WidgetMetaData } from "../../../../shared/shared/models";
import { HttpEventType } from "@angular/common/http";

@Component({
  selector: 'app-widget-table-dropdown',
  templateUrl: './widget-table-dropdown.component.html',
  styleUrls: ['./widget-table-dropdown.component.scss']
})
export class WidgetTableDropdownComponent extends WidgetBase implements OnInit {
  
  constructor(private formStateService:FormStateService,private httpService:HttpService) { super()}


  textInput:string='';
  defaultValue:string='';
  private _widget:WidgetMetaData;
  private _data:any;
    private _mode:string;
   get mode(): string {
        return this._mode;
    }
    @Input()
    set mode(mode: string) {
        this._mode = mode;
         this.setValue();
    }
   get data(): any {
        return this._data;
    }
    @Input()
    set data(data: any) {
        this._data = data;
        this.setValue();
    }
    get widget(): WidgetMetaData {
        return this._widget;
    }
    @Input()
    set widget(widget: WidgetMetaData) {
        this._widget = widget;
        this.setValue();
    }
     private _rowId:string;
     get rowId(): string {
        return this._rowId;
    }
    @Input()
    set rowId(rowId: string) {
        this._rowId = rowId;
    }
    
         private _showHeader:boolean;
     get showHeader(): boolean {
        return this._showHeader;
    }
    
    @Input()
    set showHeader(showHeader: boolean) {
        this._showHeader = showHeader;
    }
      private _tableId:string;
     get tableId(): string {
        return this._tableId;
    }
    
    @Input()
    set tableId(tableId: string) {
        this._tableId = tableId;
        this.setValue();
    }
    setValue(){
        if(this.data && this.widget && this.mode && this.tableId ){
          this.setBaseRef(this);
          this.updateLovValues();
             super.setWidget(this.widget);
            super.setMode(this.mode);
            super.updateValues();
          if(this.widget.defaultValue){
            this.defaultValue=this.widget.defaultValue;
          }
          if(this.data && this.data[this.tableId] && this.data[this.tableId][this.rowId]){
            this.textInput=this.data[this.tableId][this.rowId][this.widget.widgetId];
            this.formStateService.updateTableState(this.tableId,this.rowId,this.widget.widgetId,this.textInput);
          }
          this.formStateService.addToArray(this);
        }
      
    }

    updateLovValues(){
      if(this.widget && this.widget.listApiName){
        this.fetchLovValues(this.widget.listApiName);
      }
    }

    fetchLovValues(lovName){
      this.httpService.postData("/customform/data/name/lov/",{"lovName":lovName}).subscribe(event=>{
        if(event.type==HttpEventType.Response){
      let result:any=event.body;
        if(result && result.length>0){
          this.widget.options=[];
          result.forEach(element => {
            this.widget.options.push(element);
          });
        }
        }
      });
    }

  
     validate(){
      this.errors=[];
      if(this.widget.required){
        if(!this.textInput ||this.textInput.trim()==""){
          this.errors.push(this.widget.widgetDisplayName +" is a required field.");
        }
      }
      if(this.errors.length>0){
        return "failed";
      }
      return "success";
    }
  ngOnInit() {
  }

  textBoxChanged(event){
     if(this.rowId){
      //its data table
      this.formStateService.updateTableState(this.tableId,this.rowId,this.widget.widgetId,event.target.value);

    }else{
      this.formStateService.updateRecordState(this.widget.widgetId,event.target.value);
      this.formStateService.valueChanged(this.widget.widgetId,event.target.value,this.formStateService.recordState[this.widget.widgetId],this.widget.options)
      this.textInput=event.target.value;
    }
    this.validate();
  }
}
