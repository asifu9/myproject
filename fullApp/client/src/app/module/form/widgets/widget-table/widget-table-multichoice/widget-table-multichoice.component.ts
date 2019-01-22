import { Component, OnInit,Input } from '@angular/core';
import { WidgetBase } from "../../widget-base";
import { FormStateService } from "../../form-state.service";
import { WidgetMetaData } from "../../../../shared/shared/models";


@Component({
  selector: 'app-widget-table-multichoice',
  templateUrl: './widget-table-multichoice.component.html',
  styleUrls: ['./widget-table-multichoice.component.scss']
})
export class WidgetTableMultichoiceComponent extends WidgetBase implements OnInit {

  constructor(private formStateService:FormStateService) { super();}


  textInput:string='';
  options:any[];
  defaultValue:string='';
  private _widget:WidgetMetaData;
  result:string[]=[];
  private _data:any;
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

setValue(){
    if(this.data && this.widget && this.mode){
      this.setBaseRef(this);
      super.setWidget(this.widget);
            super.setMode(this.mode);
            super.updateValues();
        if(this.data[this.widget.widgetId]){
            this.textInput=this.data[this.widget.widgetId];
            this.formStateService.updateRecordState(this.widget.widgetId,this.textInput);
        }
        if(this.widget.defaultValue){
          this.defaultValue=this.widget.defaultValue;
        }
        this.options=this.widget.options;
      this.formStateService.addToArray(this);
    }
     
}

   validate(){
      this.errors=[];
      if(this.widget.required){
        if(!this.formStateService.recordState || 
        !this.formStateService.recordState[this.widget.widgetId]
        ||this.formStateService.recordState[this.widget.widgetId]==""){
          this.errors.push(this.widget.widgetDisplayName +" is a required field.");
        }
      }
      if(this.errors.length>0){
        return "failed";
      }
      return "success";
    }
updateValue(ii){
  if(this.textInput==ii){
    return true;
  }
  return false;
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
    }
    this.validate();
  }

}
