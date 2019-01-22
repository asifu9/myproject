import { Component, OnInit ,Input,ViewChild,ElementRef} from '@angular/core';
import { WidgetBase } from "../widget-base";
import { FormStateService } from "../form-state.service";
import { WidgetMetaData, FormAttachment } from "../../../shared/shared/models";
import { SessionService } from "../../../shared/shared/SessionService";
import { HttpService } from "../../../shared/shared/httpService";
import { FileUploadModule } from "primeng/components/fileupload/fileupload";
@Component({
  selector: 'app-widget-attach',
  templateUrl: './widget-attach.component.html',
  styleUrls: ['./widget-attach.component.scss']
})
export class WidgetAttachComponent extends WidgetBase implements OnInit {

  constructor(private formStateService:FormStateService,
  private session:SessionService,private apiService:HttpService)
   { super();}
@ViewChild('fileInput') inputEl: ElementRef;
loading:boolean=false;
url:string='';
  textInput:string='';
  attachment:FormAttachment;
  options:any[];
  defaultValue:string='';
  multiple:boolean=false;
  selectedPhotos:any[]=[];
  savingMessage:string='Upload in progress';
  private _widget:WidgetMetaData;
  result:string[]=[];
  private _data:any;
  private _form:string;
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
     get form(): string {
        return this._form;
    }
    @Input()
    set form(form: string) {
        this._form = form;
    }
   get data(): any {
        return this._data;
    }
    @Input()
    set data(data: any) {
      console.log("ok got dta " + this.data);
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
   private  _formName:string;
      get formName(): string {
        return this._formName;
    }
    @Input()
    set formName(formName: string) {
        this._formName = formName;
    }

setValue(){
    if(this.data && this.widget && this.mode){
      this.setBaseRef(this);
      super.setWidget(this.widget);
            super.setMode(this.mode);
            super.updateValues();
            this.multiple=this.widget.multiple;
        if(this.data[this.widget.widgetId]){
          
            this.textInput=this.data[this.widget.widgetId];
            this.fetchAttachments(this.data[this.widget.widgetId]);
            this.formStateService.updateRecordState(this.widget.widgetId,this.textInput);
        }
      console.log(this.widget);
        if(this.widget.defaultValue){
          this.defaultValue=this.widget.defaultValue;
        }
        this.options=this.widget.options;
      this.formStateService.addToArray(this);
    }
     
}
onBasicUpload(ee){
  debugger;
  console.log(ee);
}

fetchAttachments(id){
  this.apiService.getData("/FormUpload/"+id).subscribe(
    result=>{
      this.attachment=result;
    }
  )
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
  console.log("iiiii " + ii);
  if(this.textInput==ii){
    return true;
  }
  return false;
}
  
  ngOnInit() {
     let id=null;
    if(this.attachment){
      id=this.attachment.id;
    }
    this.url =  this.session.shareObj['url']+'/FormUpload/items/' + this.session.shareObj['user'] + '/' + this.formName+"/"+this.session.shareObj['wall']+"/"+id;
  }
  updaetList(){
     console.log();
    //   let inputEl: HTMLInputElement = this.inputEl.nativeElement;
    // let fileCount: number = inputEl.files.length;
    // this.selectedPhotos = new Array();
    // for (let i = 0; i < fileCount; i++) {
    //   this.selectedPhotos[i] = inputEl.files.item(i).name;
    // }
    // console.log(this.selectedPhotos);
    //this.uploadImages();
  }
   uploadImages() {
    this.loading = true;
    let inputEls: HTMLInputElement = this.inputEl.nativeElement;
    let fileCount: number = inputEls.files.length;
    let formData = new FormData();
    if (fileCount > 0) { 
      for (let i = 0; i < fileCount; i++) {
        formData.append('file[]', inputEls.files.item(i), inputEls.files.item(i).name);
      }
    }
     let userId = this.session.shareObj['user'];
    let id=null;
    if(this.attachment){
      id=this.attachment.id;
    }
     let url =  '/FormUpload/items/' + userId + '/' + this.formName+"/"+this.session.shareObj['wall']+"/"+id;
     let headers = new Headers();
     headers.append('Content-Type', 'multipart/form-data');
     console.log(formData);
    // this.fetchAttachments("1fd6e956-8d8a-4059-abf0-02b46151ea6e");
    this.apiService.postDataWithOptions(url,formData,headers).subscribe(
      data => {//this.attachment=data;
        console.log("resut ok =========================");
        console.log(data);
        //formData.delete('file');
        console.log(formData);
        formData=undefined;
        debugger;
      },
      error=>{
        console.log("error s");
        debugger;
      }
      
    );
    // this.apiService.makeFileRequest(url,[],event.target.files).subscribe(res=>{
    //   alert("ok done cool");
    // });
    return;
  }
  // textBoxChanged(event){
  //   console.log(event.target.value);
  //   this.formStateService.updateRecordState(this.widget.widgetId,event.target.value);
  //   this.formStateService.valueChanged(this.widget.widgetId,event.target.value,this.formStateService.recordState[this.widget.widgetId],this.widget.options)
  //   this.validate();
  // }

}

