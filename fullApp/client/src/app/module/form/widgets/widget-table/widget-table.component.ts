import { Component, OnInit, Input } from '@angular/core';
import { WidgetBase } from "../widget-base";
import { WidgetMetaData } from "../../../shared/shared/models";
import { FormStateService } from "../form-state.service";
import { Util } from "../../../shared/util";

@Component({
  selector: 'app-widget-table',
  templateUrl: './widget-table.component.html',
  styleUrls: ['./widget-table.component.scss']
})
export class WidgetTableComponent extends WidgetBase implements OnInit {

  constructor(private formStateService: FormStateService) {
    super();
  }

  ngOnInit() {
  }

  validate() {

    return "success";
  }


  textInput: string[] = [];
  selectedValue: string = '';
  defaultValue: string[] = [];
  private _widget: WidgetMetaData;
  rows: any[] = [];
  result: string[] = [];
  private _data: any;
  private _mode: string;
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
    this.setValue();
  }

  setValue() {
    if (this.data && this.widget && this.mode) {
      this.setBaseRef(this);
      super.setWidget(this.widget);
      super.setMode(this.mode);
      console.log("data in table");
      console.log(this.data);
      console.log(this.data[this.widget.widgetId]);

      if (this.data && this.data[this.widget.widgetId] ) {
        this.rows=[];
        for(let pp in this.data[this.widget.widgetId]){
          console.log(pp);
           let obj: any = {};
            obj = this.widget.items.map(a => ({ ...a }));

            obj.rowId=pp;
            this.rows.push(obj);
        }
       
        
      } else {
        let obj: any = {};
        obj = this.widget.items.map(a => ({ ...a }));
        obj.rowId = Util.getUUID();
        this.rows.push(obj);
      }


      this.formStateService.addToArray(this);
    }

  }
  AddMore() {
    let obj: any = {};
    obj = this.widget.items.map(a => ({ ...a }));
    obj.rowId = Util.getUUID();
    this.rows.push(obj);

  }

  deleteRow(ittt, widgetId) {
    for (let i = 0; i < this.rows.length; i++) {
      if (this.rows[i].rowId === ittt) {
        this.rows.splice(i, 1);
        this.formStateService.deleteRow(ittt, widgetId);
        return;
      }
    }
  }
  getUpdatedRecords(ddd){
    let isFound:boolean=false;
    for(var i=0;i<ddd.length;i++){
      if(ddd[i] && ddd[i].hidden){
        ddd.splice(i,1);
        this.getUpdatedRecords(ddd);
      }
    }
   return ddd;
  }
}
