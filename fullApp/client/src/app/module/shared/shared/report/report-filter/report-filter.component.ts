import { Component, OnInit,Input,Output,EventEmitter } from '@angular/core';

@Component({
  selector: 'app-report-filter',
  templateUrl: './report-filter.component.html',
  styleUrls: ['./report-filter.component.scss']
})
export class ReportFilterComponent implements OnInit {

  @Input()
  data:any[];
  _reportConfig:any;
  
  filters:any[]=[];
  @Output()
  change: EventEmitter<any> = new EventEmitter<any>();
  constructor() { }


    get reportConfig(): any {
    // transform value for display
    return this._reportConfig;
  }
  
  @Input()
  set reportConfig(reportConfig: any) {
    this._reportConfig = reportConfig;
    this.updateDataForReport();
  }
  ngOnInit() {
  }

  updateDataForReport(){

  }
  showFilter(ii){
    if(this.filters.indexOf(ii)==-1){
      this.filters.push(ii);
    }
  }
  filterValueChanged(event){
    this.change.emit(event);
  }
}
