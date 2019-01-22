import { Component, OnInit, Input } from '@angular/core';
import { FormStateService } from "../../form-state.service";
import { WidgetMetaData } from "../../../../shared/shared/models";
import { WidgetBase } from "../../widget-base";

@Component({
  selector: 'app-widget-table-textarea',
  templateUrl: './widget-table-textarea.component.html',
  styleUrls: ['./widget-table-textarea.component.scss']
})
export class WidgetTableTextareaComponent extends WidgetBase implements OnInit {

  constructor(private formStateService: FormStateService) { super(); }
  textInput: string = '';
  placeholderValue: string = '';
  private _widget: WidgetMetaData;
  private _data: any;
  private _mode: string;
  get mode(): string {
    return this._mode;
  }
  @Input()
  set mode(mode: string) {
    this._mode = mode;
    this.setValues();
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
  private _rowId: string;
  get rowId(): string {
    return this._rowId;
  }
  @Input()
  set rowId(rowId: string) {
    this._rowId = rowId;
  }

  private _showHeader: boolean;
  get showHeader(): boolean {
    return this._showHeader;
  }

  @Input()
  set showHeader(showHeader: boolean) {
    this._showHeader = showHeader;
  }
  private _tableId: string;
  get tableId(): string {
    return this._tableId;
  }

  @Input()
  set tableId(tableId: string) {
    this._tableId = tableId;
    this.setValues();
  }


  getRequiredCss() {
    if (this.widget.required) {
      if (this.textInput && this.textInput.trim() == '') {
        return "requiredErrorCss";
      } else {
        return "requiredNormalCss"
      }
    }
  }
  validate() {
    this.errors = [];
    if (this.widget.required) {
      if (!this.textInput || this.textInput.trim() == "") {
        this.errors.push(this.widget.widgetDisplayName + " is a required field.");
      }
    }
    if (this.errors.length > 0) {
      return "failed";
    }
    return "success";
  }
  setValues() {
    if (this.data && this.widget && this.mode && this.tableId) {
      this.setBaseRef(this);
      super.setWidget(this.widget);
      super.setMode(this.mode);
      super.updateValues();
      if (this.widget.defaultValue) {
        this.textInput = this.widget.defaultValue;
      }
      if (this.widget.placeholderValue) {
        this.placeholderValue = this.widget.placeholderValue;
      } else {
        this.placeholderValue = this.widget.widgetDisplayName;
      }
          if(this.data && this.data[this.tableId] && this.data[this.tableId][this.rowId]){
             this.textInput = this.data[this.tableId][this.rowId][this.widget.widgetId]
            this.formStateService.updateRecordState(this.widget.widgetId, this.textInput);
          }
      
     
      this.formStateService.addToArray(this);
    }

  }


  ngOnInit() {
  }

  textBoxChanged(event) {
    if (this.rowId) {
      //its data table
      this.formStateService.updateTableState(this.tableId, this.rowId, this.widget.widgetId, event.target.value);

    } else {
      this.formStateService.updateRecordState(this.widget.widgetId, event.target.value);
      this.formStateService.valueChanged(this.widget.widgetId, event.target.value, this.formStateService.recordState[this.widget.widgetId], this.widget.options)
    }
    this.validate();
  }
}
