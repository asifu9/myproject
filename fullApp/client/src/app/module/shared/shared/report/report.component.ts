import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit {

  constructor() { }

  private _data:any[];
        get data(): any {
    // transform value for display
    return this._data;
  }
  
  @Input()
  set data(data: any) {
    this._data = data;
  }

 private  _reportConfig:any;
      get reportConfig(): any {
    // transform value for display
    return this._reportConfig;
  }
  
  @Input()
  set reportConfig(reportConfig: any) {
    this._reportConfig = reportConfig;
  }

  
  ngOnInit() {
  }


  

}
