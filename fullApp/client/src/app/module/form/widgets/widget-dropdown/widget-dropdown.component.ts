import { Component, OnInit,Input } from '@angular/core';
import { WidgetMetaData } from "../../../shared/shared/models";
import { FormStateService } from "../form-state.service";
import { WidgetBase } from "../widget-base";
import { HttpService } from "../../../shared/shared/httpService";
import { BehaviorSubject } from "rxjs";
import { HttpEventType } from "@angular/common/http";

@Component({
  selector: 'app-widget-dropdown',
  templateUrl: './widget-dropdown.component.html',
  styleUrls: ['./widget-dropdown.component.scss']
})
export class WidgetDropdownComponent extends WidgetBase implements OnInit {
  
  constructor(private formStateService:FormStateService,private httpService:HttpService) { super()}

  displayValue:string;
  textInput:string='';
  defaultValue:string='';
  isOpen:boolean;
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
    }
    setValue(){
        if(this.data && this.widget && this.mode){
          this.updateLovValues();
          
        }
      
    }

    updateLovValues(){
      if(this.widget && this.widget.listApiName){
        this.fetchLovValues(this.widget.listApiName);
      }
    }

    fetchLovValues(lovName){
      this.httpService.postData("/customform/data/name/lov/",{"lovName":lovName}).subscribe(event=>{
        if(event){
          let result:any=event;
          if(result && result.length>0){
            this.widget.options=[];
            result.forEach(element => {
              this.widget.options.push(element);
            });
            this.setBaseRef(this);
            
              super.setWidget(this.widget);
              super.setMode(this.mode);
              super.updateValues();
            if(this.widget.defaultValue){
              this.defaultValue=this.widget.defaultValue;
            }
            if(this.data[this.widget.widgetId]){
              this.textInput=this.data[this.widget.widgetId];
              this.formStateService.updateRecordState(this.widget.widgetId,this.textInput);
            }
            this.formStateService.addToArray(this);
            this.updateDisplayValue();
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

   toggleCategory(){
    this.isOpen=!this.isOpen;
  }

  textBoxChanged(event){
    this.isOpen=false;
    console.log("------------")
    console.log(event);
    console.log("------------22")

     if(this.rowId){
      //its data table
      this.formStateService.updateTableState(this.tableId,this.rowId,this.widget.widgetId,event);

    }else{
      this.formStateService.updateRecordState(this.widget.widgetId,event);
      this.formStateService.valueChanged(this.widget.widgetId,event,this.formStateService.recordState[this.widget.widgetId],this.widget.options)
      this.textInput=event
    }
    console.log(this.widget.options);
    this.updateDisplayValue();
    this.validate();

  }

  updateDisplayValue(){
      if(this.widget.options){
      this.widget.options.forEach(element => {
        if(this.textInput==element.storedValue){
          this.displayValue=element.displayValue;
        }
      });
    }
    if(this.textInput===""){

      this.displayValue="Select One";
    }
  }
}











