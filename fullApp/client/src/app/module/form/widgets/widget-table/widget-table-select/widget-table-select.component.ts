import { Component, OnInit ,Input} from '@angular/core';
import { WidgetBase } from "../../widget-base";
import { FormStateService } from "../../form-state.service";
import { HttpService } from "../../../../shared/shared/httpService";
import { Options, WidgetMetaData } from "../../../../shared/shared/models";
import { HttpEventType } from "@angular/common/http";


@Component({
  selector: 'app-widget-table-select',
  templateUrl: './widget-table-select.component.html',
  styleUrls: ['./widget-table-select.component.scss']
})
export class WidgetTableSelectComponent extends WidgetBase  implements OnInit {


constructor(private formStateService:FormStateService,private httpService:HttpService) { super(); }

  tempArray:Options[]=[];
  originalArray:Options[]=[];
  textInput:string[]=[];
  selectedValue:string='';
  defaultValue:string[]=[];
  hideTextBox:boolean=true;
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
        this.originalArray=this.widget.options;
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
        updateLovValues(){
      if(this.widget && this.widget.listApiName){
        this.fetchLovValues(this.widget.listApiName);
      }
    }
    removeAddedValues(ii){
      this.textInput.splice(this.textInput.indexOf(ii),1);
       for(let i of this.widget.options){
        if(ii==i.storedValue){
          this.tempArray.push(i);
          break;
        }
      }
    if(this.textInput.length==1 && !this.widget.multiple){
      this.hideTextBox=false;
    }else{
       if(this.disabledValue){
      this.hideTextBox=true;
       }
    }
    this.textBoxChanged();
  }
    fetchLovValues(lovName){
      this.httpService.postData("/customform/data/name/lov/",{"lovName":lovName}).subscribe(event=>{
        if(event.type==HttpEventType.Response){
          let result:any=event.body;
        if(result && result.length>0){
          this.widget.options=[];
          result.forEach(element => {
            this.widget.options.push(element);
           this.originalArray= this.widget.options.map(a => ({...a}));
          });
        }
      }
      });
    }
    removeSelectedItems(){
      if(this.originalArray){
        for(let ff of this.textInput){
              
                for(var index=0;index<this.originalArray.length;index++){
                  let ooo=this.originalArray[index];
                  if(ff==ooo.storedValue){
                    this.originalArray.splice(index,1);
                    break;
                  }
                }
                for(var index=0;index<this.tempArray.length;index++){
                  let ooo=this.tempArray[index];
                  if(ff==ooo.storedValue){
                    this.tempArray.splice(index,1);
                    break;
                  }
                }

                
              }
      }
     
    }
  setValue(){
      if(this.data && this.widget && this.mode){
        this.updateLovValues();
        this.setBaseRef(this);
        super.setWidget(this.widget);
            super.setMode(this.mode);
            super.updateValues();
            if(this.disabledValue){
              this.hideTextBox=false;
            }
        if(this.widget.defaultValue){
          this.defaultValue.push(this.widget.defaultValue);
        }

          if(this.tableId){
          //its data table row
          if(this.data && this.data[this.tableId] && this.data[this.tableId][this.rowId]){

            this.textInput=this.data[this.tableId][this.rowId][this.widget.widgetId];
            this.formStateService.updateTableState(this.tableId,this.rowId,this.widget.widgetId,this.textInput);
            this.removeSelectedItems();
          }
 
        }else{

          if(this.data[this.widget.widgetId]){
            this.textInput=this.data[this.widget.widgetId];
            this.removeSelectedItems();
            this.formStateService.updateRecordState(this.widget.widgetId,this.textInput);
          }
        }
        this.formStateService.addToArray(this);
      }
  
  }
  fillTextbox(item){
    if(!this.textInput){
      this.textInput=[];
    }
    this.textInput.push(item);
    if(this.textInput.length==1 && !this.widget.multiple){
      this.hideTextBox=false;
    }else{
      if(!this.disabledValue ){
      this.hideTextBox=true;
      }
    }
    this.removeSelectedItems();
    this.textBoxChanged();
  }
  complete(event){
    if(this.originalArray){
      this.tempArray=[];
      if(event.target.value==""){
        this.tempArray=[];
      }else{
        for(let i of this.originalArray){
          if(i.displayValue.toLowerCase().indexOf(event.target.value.toLowerCase())>=0){
            this.tempArray.push(i);
          }
        }
      }
   
      
    }

      
  }
  ngOnInit() {
  }
  getDisplayValue(i){
    if(this.widget && this.widget.options){
      for(let obj of this.widget.options){
        if(i===obj.storedValue){
          return obj.displayValue;
        }
      }
    }
   
  }

  textBoxChanged(){
  
 if(this.rowId){
      //its data table
      
      if(this.widget.multiple){
        this.formStateService.updateTableState(this.tableId,this.rowId,this.widget.widgetId,this.textInput);
      }else{
        this.formStateService.updateTableState(this.tableId,this.rowId,this.widget.widgetId,this.textInput[0]);
      }
      //   if(this.widget.multiple){
      //   this.formStateService.valueChanged(this.widget.widgetId,this.textInput,this.formStateService.recordState[this.widget.widgetId],this.widget.options)
        
      // }else{
      //   this.formStateService.valueChanged(this.widget.widgetId,this.textInput[0],this.formStateService.recordState[this.widget.widgetId],this.widget.options)
      // }

    }else{
      if(this.widget.multiple){
        this.formStateService.updateRecordState(this.widget.widgetId,this.textInput);
      }else{
        this.formStateService.updateRecordState(this.widget.widgetId,this.textInput[0]);
      }
        if(this.widget.multiple){
        this.formStateService.valueChanged(this.widget.widgetId,this.textInput,this.formStateService.recordState[this.widget.widgetId],this.widget.options)
      }else{
        this.formStateService.valueChanged(this.widget.widgetId,this.textInput[0],this.formStateService.recordState[this.widget.widgetId],this.widget.options)
      }
    }
    
    this.validate();
  }

}
