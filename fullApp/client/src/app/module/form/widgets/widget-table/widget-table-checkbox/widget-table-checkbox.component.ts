import { Component, OnInit,Input } from '@angular/core';
import { WidgetBase } from "../../widget-base";
import { FormStateService } from "../../form-state.service";
import { WidgetMetaData } from "../../../../shared/shared/models";


@Component({
  selector: 'app-widget-table-checkbox',
  templateUrl: './widget-table-checkbox.component.html',
  styleUrls: ['./widget-table-checkbox.component.scss']
})
export class WidgetTableCheckboxComponent extends WidgetBase  implements OnInit {

  constructor(private formStateService:FormStateService) {
    super();
   }
  private _data:any;
  private _mode:string;
   get mode(): string {
        return this._mode;
    }
    @Input()
    set mode(mode: string) {
        this._mode = mode;
         this.setValues();
    }
   get data(): any {
        return this._data;
    }
    @Input()
    set data(data: any) {
        this._data = data;
        this.setValues();
    }

  textInput:boolean=false;
  defaultValue:boolean=false;
  private _widget:WidgetMetaData;
  result:string[]=[];
  options:any[];
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
    get widget(): WidgetMetaData {
        return this._widget;
    }
    @Input()
    set widget(widget: WidgetMetaData) {
        this._widget = widget;
        this.setValues();
    }
      validate(){
      this.errors=[];
      if(this.widget.required){
        if(!this.result ||this.result.length==0){
          this.errors.push(this.widget.widgetDisplayName +" is a required field.");
        }
      }
      if(this.errors.length>0){
        return "failed";
      }
      return "success";
    }
    setValues(){
        if(this.data && this.widget && this.mode){
          this.setBaseRef(this);
            super.setWidget(this.widget);
            super.setMode(this.mode);
            super.updateValues();
            if(this.widget.defaultValue){
              this.defaultValue=(this.widget.defaultValue=="true"?true:false);
            }
            this.result=this.data[this.widget.widgetId];
            if(this.data[this.widget.widgetId]){
                this.formStateService.updateRecordState(this.widget.widgetId,this.result);
            }
            this.options=this.widget.options;
            this.formStateService.addToArray(this);
        }
      
    }

  getCheckedValue(item){
      if(this.result && this.result.indexOf(item)!==-1){
        return true;
      }else{
        return false;
      }
  }
  ngOnInit() {
  }

  textBoxChanged(event){
    if(!this.result){
      this.result=[];
    }
    if(this.result.indexOf(event.target.value)==-1){
      this.result.push(event.target.value);
    }
     if(this.rowId){
      //its data table
      this.formStateService.updateTableState(this.tableId,this.rowId,this.widget.widgetId,this.result);

    }else{
    this.formStateService.updateRecordState(this.widget.widgetId,this.result);
    this.formStateService.valueChanged(this.widget.widgetId,this.result,this.formStateService.recordState[this.widget.widgetId],this.widget.options)
    }
    this.validate();
  }

}
