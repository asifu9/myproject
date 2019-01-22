import { Component, OnInit,Input } from '@angular/core';
import {  WidgetMetaData } from "../../../shared/shared/models";
import { FormStateService } from "../form-state.service";
import { WidgetBase } from "../widget-base";
import { TranslationService } from "../../../shared/shared/translate.service";

@Component({
  selector: 'app-widget-inputfield',
  templateUrl: './widget-inputfield.component.html',
  styleUrls: ['./widget-inputfield.component.scss']
})
export class WidgetInputfieldComponent extends WidgetBase implements OnInit {

  constructor(private translation:TranslationService,private formStateService:FormStateService) { super() }
  textInput:string='';
  placeholderValue:string='';
  errorMessage:string='';
  private _widget:WidgetMetaData;
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
   get data(): any {
        return this._data;
    }
    @Input()
    set data(data: any) {
        this._data = data;
        this.setValues();
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
        if(!this.textInput ||this.textInput.trim()==""){
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
        if(this.mode=="1"){

          this.showHeader=true;
        }
        super.setBaseRef(this);
        super.setWidget(this.widget);
            super.setMode(this.mode);
            super.updateValues();
        if(this.widget.defaultValue){
          this.textInput=this.widget.defaultValue;
        }
         if(this.widget.placeholderValue){
           this.translation.getAsyn(this.widget.placeholderValue).subscribe(result=>{
             this.placeholderValue=result;
           })
        }else{
           this.translation.getAsyn(this.widget.widgetDisplayName).subscribe(result=>{
             this.placeholderValue=result;
           })
        }
        if(this.tableId){
          //its data table row
          if(this.data && this.data[this.tableId] && this.data[this.tableId][this.rowId]){
            console.log("text box in data row" +this.data[this.tableId][this.rowId][this.widget.widgetId]);
            console.log(this.rowId);
            this.textInput=this.data[this.tableId][this.rowId][this.widget.widgetId];
            this.formStateService.updateTableState(this.tableId,this.rowId,this.widget.widgetId,this.textInput);
          }
 
        }else{
           if(this.data[this.widget.widgetId]){
            this.textInput=this.data[this.widget.widgetId];
            this.formStateService.updateRecordState(this.widget.widgetId,this.textInput);
          }
        }
       
        this.formStateService.addToArray(this);
      }
       
    }

  
  ngOnInit() {
  }
  keyPress(event){
      if(this.widget.widgetDataType=='Number'){
         const pattern = /[0-9\.]/;

        let inputChar = String.fromCharCode(event.charCode);
        if (event.keyCode != 8 && !pattern.test(inputChar)) {
          event.preventDefault();
        }
      }
     
  }
  onBlurMethod(event){
    const emailPattern = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
    if(this.widget.widgetDataType=="Email"){
      if(event.target.value){
          if(emailPattern.test(event.target.value)){
            this.isWidgetValid=true;
            this.errorMessage="";
          }else{
            this.errorMessage="Email is not valid";
            this.isWidgetValid=false;
          }
      }else{
          this.isWidgetValid=true;
          this.errorMessage="";
      }
    
    }else if(this.widget.widgetDataType=='URL'){
      if(event.target.value){
        if(this.checkURL(event.target.value)){
          this.isWidgetValid=true;
          this.errorMessage="";
        }else{
          
          this.errorMessage="URL is not valid";
          this.isWidgetValid=false;
        }
      }else{
          this.errorMessage="";
          this.isWidgetValid=true;
      }
    }else {
       this.isWidgetValid=true;
    }
  }
 checkURL(url) {
    if(url)
    {
        if (url.match(/(http(s)?:\/\/.)?(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)/g) !== null)
            return true;
        else 
            return false;
        
    }
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

  getCss(){
    // if(this.showHeader){
    //   return "inline field addTopSpace"
    // }else{
    //   return "inline field"
    // }
  }

}
