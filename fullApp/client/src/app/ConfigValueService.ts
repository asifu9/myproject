
import { Injectable } from '@angular/core';
import { Subject, Observable } from 'rxjs';
import { ConfigValues, TicketCategory } from "./module/shared/shared/models";
import { HttpService } from "./module/shared/shared/httpService";
import { SessionService } from "./module/shared/shared/SessionService";


@Injectable()
export class ConfigValueService {
    ticketStatus:ConfigValues[]=[];
    ticketPriority:ConfigValues[]=[];
    ticketCategory:ConfigValues[]=[];
    tickeStatusMap:any={};
ticketPriorityMap:any={};
    ticketCategoryMap:any={};
    configFetchStatus:string='none'
    constructor(private httpService:HttpService,private sessionService:SessionService){
        this.fetchConfigValues()
    }



    fetchConfigValues(): any{
      
     return   new Observable(observer => {
    this.httpService.getData("/ConfigValues/"+this.sessionService.shareObj["wall"]+"/true").subscribe(result=>{


            if(result){
                for(let i of result){
                    if(i.type=='TicketPriority'){
                        this.ticketPriority.push(i);
                        this.ticketPriorityMap[""+i.storedValue]=i.displayValue;
                    }else if(i.type=='TicketStatus'){
                        this.ticketStatus.push(i);
                        this.tickeStatusMap[""+i.storedValue]=i.displayValue;
                    }else if(i.type=='TicketCategory'){
                         this.ticketCategory.push(i);
                        this.ticketCategoryMap[""+i.storedValue]=i.displayValue;
                    }
                    
                }
               this.configFetchStatus="done";
               observer.next("done");
                observer.complete();
            }

        });
 
})


     
    }

    
     
}