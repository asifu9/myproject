import { Component, OnInit,Input } from '@angular/core';
import { WidgetBase } from "../../widget-base";
import { FormStateService } from "../../form-state.service";
import { WidgetMetaData } from "../../../../shared/shared/models";

@Component({
  selector: 'app-widget-table-list',
  templateUrl: './widget-table-list.component.html',
  styleUrls: ['./widget-table-list.component.scss']
})
export class WidgetTableListComponent extends WidgetBase implements OnInit {

constructor(private formStateService:FormStateService) { super(); }


  textInput:string[]=[];
  selectedValue:string='';
  defaultValue:string[]=[];
  private _widget:WidgetMetaData;
  result:string[]=[];
  private _data:any;
      private _mode:string;
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
    

   validate(){
      this.errors=[];
      if(this.widget.required){
        if(!this.textInput ||this.textInput.length==0){
          this.errors.push(this.widget.widgetDisplayName +" is a required field.");
        }
      }
      if(this.errors.length>0){
        return "failed";
      }
      return "success";
    }
  setValue(){
      if(this.data && this.widget && this.mode){
        this.setBaseRef(this);
        super.setWidget(this.widget);
            super.setMode(this.mode);
            super.updateValues();
        if(this.widget.defaultValue){
          this.defaultValue.push(this.widget.defaultValue);
        }
        if(this.data[this.widget.widgetId]){
          this.textInput=this.data[this.widget.widgetId];
          this.formStateService.updateRecordState(this.widget.widgetId,this.textInput);
        }
        this.formStateService.addToArray(this);
      }
  
  }
  ngOnInit() {
  }
  getDisplayValue(i){
    for(let obj of this.widget.options){
      if(i===obj.storedValue){
        return obj.displayValue;
      }
    }
  }

  textBoxChanged(event){
      if(this.textInput.indexOf(event.target.value)==-1)
      {
        this.textInput.push(event.target.value);
      }
     if(this.rowId){
      //its data table
      this.formStateService.updateTableState(this.tableId,this.rowId,this.widget.widgetId,this.textInput);

    }else{
    
      this.formStateService.updateRecordState(this.widget.widgetId,this.textInput);
      this.formStateService.valueChanged(this.widget.widgetId,this.textInput,this.formStateService.recordState[this.widget.widgetId],this.widget.options)
    }
    this.validate();
  }
}
