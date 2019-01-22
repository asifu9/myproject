import { Component, OnInit, Input } from '@angular/core';
import { WidgetMetaData } from "../../../shared/shared/models";
import { FormStateService } from "../form-state.service";
import { Util } from "../../../shared/util";
import { WidgetBase } from "../widget-base";

@Component({
  selector: 'app-widget-date',
  templateUrl: './widget-date.component.html',
  styleUrls: ['./widget-date.component.scss']
})
export class WidgetDateComponent extends WidgetBase implements OnInit {

  constructor(private formStateService:FormStateService) { super(); }
  dateField: any;
  placeholderValue: string = '';
  dateFormat:string='';
  displayValue:string='';
  private _widget: WidgetMetaData;
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
  @Input()
  set widget(widget: WidgetMetaData) {
    this._widget = widget;
    this.setValue();

  }
  setValue(){
    console.log("ok cool here i am")
    if(this.widget && this.data && this.mode){
      this.setBaseRef(this);
      super.setWidget(this.widget);
            super.setMode(this.mode);
            super.updateValues();
      if (this.widget.placeholderValue) {
        this.placeholderValue = this.widget.placeholderValue;
      } else {
        this.placeholderValue = this.widget.widgetDisplayName;
      }
      if(this.widget.dateFormat){
        this.dateFormat=this.widget.dateFormat;
      }
      if(this.data[this.widget.widgetId]){
        console.log("this.data[this.widget.widgetId] "+this.data[this.widget.widgetId])
        this.dateField=Util.convertDate(this.data[this.widget.widgetId]);
        this.formStateService.updateRecordState(this.widget.widgetId,this.data[this.widget.widgetId]);
      }
      this.formStateService.addToArray(this);
    }
  
  }
      validate(){
      this.errors=[];
      if(this.widget.required){
        if(!this.dateField ){
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

  updateValue(event){
    console.log(event);
     if(this.rowId){
      //its data table
      this.formStateService.updateTableState(this.tableId,this.rowId,this.widget.widgetId,Util.convertDateToMili(event.value));

    }else{
    this.formStateService.updateRecordState(this.widget.widgetId,Util.convertDateToMili(event.value));
    
    this.formStateService.valueChanged(this.widget.widgetId,Util.convertDateToMili(event.value),this.formStateService.recordState[this.widget.widgetId],this.widget.options)
    }
    this.validate();
  }

}
