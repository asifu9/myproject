import { Injectable } from '@angular/core';
import { HttpService } from "../../shared/shared/httpService";
import { SessionService } from "../../shared/shared/SessionService";
import { WidgetBase } from "./widget-base";
import { BehaviorSubject } from "rxjs";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";

@Injectable()
export class FormStateService {

  recordState:any;  
  isFormValid:boolean=true;
  componentRef:WidgetBase[]=[];
  onChangeEmitter :BehaviorSubject<any>=new BehaviorSubject<any>(null);
  constructor(private notificationServiceService:NotificationServiceService,private httpSession:HttpService,private session:SessionService) { }

  updateRecordState(key,value){
    console.log(" value in single update " + value);
    if(!this.recordState){
      this.recordState={};
    }
    this.recordState[key]=value;
  }

deleteRow(iiit,tableId){
  if(this.recordState && this.recordState[tableId]){
    delete this.recordState[tableId][iiit];
  }
}
  updateTableState(tableId,rowId,key,value){
    console.log("value to update is " + value);
    if(!this.recordState){
      this.recordState={};
    }
    if(!this.recordState[tableId]){
      this.recordState[tableId]={};
    }
     if(!this.recordState[tableId][rowId]){
      this.recordState[tableId][rowId]={};
    }
    this.recordState[tableId][rowId][key]=value;
  }

  addToArray(comp:WidgetBase){
    this.componentRef.push(comp);
  }
  updateOtherDetails(data){
    if(!this.recordState){
      this.recordState={};
    }
    this.recordState=Object.assign(this.recordState,this.recordState,...data);
  }

  saveState(name){
    let errorFound=false;
    for(let ii of this.componentRef){
      var result=ii.validate();
      if(result=="success"){
        continue;
      }else {
         errorFound=true;
      }
        
    }
    if(errorFound){
      return;
    }
     if(!this.recordState){
      this.recordState={};
    }
    this.recordState["tenantId"]=this.session.shareObj['wall'];
    this.recordState["createdBy"]=this.session.shareObj['user'];
    this.httpSession.postData("/customform/"+name,this.recordState).subscribe(result=>{
      this.notificationServiceService.showSaveDailog(name,"create",true);
      return result;
    });
  }

  updateState(name){
    if(!this.recordState){
      this.recordState={};
    }
    this.recordState["updatedBy"]=this.session.shareObj['user'];
    this.httpSession.putData("/customform/"+name,this.recordState).subscribe(result=>{
      this.notificationServiceService.showSaveDailog(name,"update",true);
      return result;
    });
  }

    valueChanged(fieldName,currentValue,previousValue,options){
    this.onChangeEmitter.next({
      "name":fieldName,
      "currentValue":currentValue,
      "previousValue":previousValue,
      "options":options
    });
  }

}
