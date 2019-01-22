import { Component, OnInit,Input } from '@angular/core';
import { ReportFilterPipe } from "../ReportFilterPipe";
import { Util } from "../../../util";

@Component({
  selector: 'app-report-view',
  templateUrl: './report-view.component.html',
  styleUrls: ['./report-view.component.scss']
})
export class ReportViewComponent implements OnInit {

  private _data:any[];
  private _reportConfig:any;
 private _filter: any;
 sortType:boolean=false;
 sortField:string;
  updatedData:any[]=[];
  tempMap=new Array<any>();
  colWidth:string[]=[];
 filterModel:any;
 filteredConfigMap:string[]=[];
  limit: number;
  page: number = 1;
  filteredConfig:any[]=[];
  tempMapConfig:any={};
  constructor() { }

  ngOnInit() {
    //this.updateDataForReport();
    
  }

    get reportConfig(): any {
    // transform value for display
    return this._reportConfig;
  }
  sortAsyDysn(columnName){
    if(this.sortField!=columnName){
      this.sortField=columnName;
    }else{
      this.sortType=!this.sortType;
    }
    //isClicked
    for(let tt of this.filteredConfig){
      if(tt.columnName!=columnName){
        tt.isClicked=undefined;
      }else{
        tt.isClicked=true;
      }
    }
    console.log("field name to sort "+this.sortField + " : " + this.sortType);
  }
  @Input()
  set reportConfig(reportConfig: any) {
    this._reportConfig = reportConfig;
     if(this.reportConfig && this.reportConfig.columns){
         for(let pp of this.reportConfig.columns){
          this.tempMapConfig[pp.columnName]=pp;
          if(!pp.hidden){
            this.filteredConfig.push(pp);
            this.filteredConfigMap.push(pp.columnName);
          }
        }

        this.updateDataForReport();
     }
  
  }

    get data(): any {
    // transform value for display
    return this._data;
  }
  
  @Input()
  set data(data: any) {
    this._data = data;
    this.updateDataForReport();
  }


    get filter(): any {
    // transform value for display
    return this._filter;
  }
  
  @Input()
  set filter(filter: any) {
    this._filter = filter;
    //this.updateDataForReport();
  }

updateDataForReport(){
  if(this.reportConfig && this.data){
    for(let c of this.reportConfig.columns){
      this.colWidth[c.index]=c.width;
    }
    for(let d of this.data){
      
      let temp:any={};
        for(let c of this.reportConfig.columns){
          temp[c.columnName]=d[c.columnName];
        }
        this.updatedData.push(temp);
    }
    let mainRow={};
    let fff=0;
    for(let c of this.reportConfig.columns){
    //  console.log(c.columnName);
      mainRow[fff]=c;
      fff+=1;
    }
    //this.updatedData.unshift(mainRow);
   // console.log("upated report data " )
  //  console.log(this.reportConfig.columns);
  }
    
}
  getProperty(it,list){
     let obj=Object.keys(it);
     let temp:string[]=[];
     for(let iii of obj){
       if(this.filteredConfigMap.indexOf(iii)!=-1){
          temp.push(iii);
       }
     }
     return temp;
  }

  getType(name){
    return this.tempMapConfig[name].columnType;
  }
  getFormattedDate(name,iiitem){
      return Util.convertUTFDate(iiitem[name]);//+" | amDateFormat: DD/MM/YYYY";// +this.tempMapConfig[name].dateFormat;
    
  }
  getFormattedDateFormat(name){
    return this.tempMapConfig[name].dateFormat;
  }
  isVisible(name){
    return this.tempMapConfig[name].hidden;
  }
  filterHiddenColumns(colss){
      let temp:any[]=[]
      for(let obj of colss){
        if(!obj.hidden){
          temp.push(obj);
        }
      }
      return temp;
  }

  filterValueChangedParent(event){
    let type= typeof event;
    if(!(event instanceof Event)){
      this.filterModel=event;
    }else  if(!(event instanceof Event)){
      this.filterModel=event;
    }else{
    }

    }

}
