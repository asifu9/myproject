import { Component, OnInit, Input, Output, EventEmitter, OnDestroy } from '@angular/core';

@Component({
  selector: 'app-filter-type',
  templateUrl: './filter-type.component.html',
  styleUrls: ['./filter-type.component.scss']
})
export class FilterTypeComponent implements OnInit ,OnDestroy{
  ngOnDestroy(): void {
  }

  constructor() { }
  private _filter:any[];
    @Output()
  change: EventEmitter<any> = new EventEmitter<any>();
  filterModel:any={};

      get filter(): any[] {
    // transform value for display
    return this._filter;
  }
  
  @Input('filter')
  set filter(filter: any[]) {
    this._filter = filter;
    if(this.filter){
       for(let i of this._filter){
        if(!this.filterModel[i.columnName])
        this.filterModel[i.columnName]="";
      }
    }
   
  }
   changeDate(ee){
    console.log(this.filterModel);
    this.change.emit(this.filterModel);
  }
  ngOnInit() {
  }
  updateParent(){
    
  }
}
