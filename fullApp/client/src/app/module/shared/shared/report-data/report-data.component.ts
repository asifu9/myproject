import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { MatTableModule, MatTableDataSource } from '@angular/material';
import { ReportMeta } from "../models";
import { MatSort, MatPaginator, MatProgressSpinnerModule, MatMenuModule } from '@angular/material';
import { Util } from "../../../shared/util";
import { Output } from "@angular/core";
import { EventEmitter } from "@angular/core";
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { DeleteConfirmationComponent } from "../../../shared/shared/delete-confirmation/delete-confirmation.component";
import { TranslateService, TranslatePipe } from '@ngx-translate/core';
import { SessionService } from "../../../shared/shared/SessionService";


@Component({
  selector: 'app-report-data',
  templateUrl: './report-data.component.html',
  styleUrls: ['./report-data.component.scss']
})
export class ReportDataComponent implements OnInit {
  constructor(public dialog: MatDialog, public translate: TranslateService,public session:SessionService) { }
  listData: MatTableDataSource<any>;
  displayedColumns: string[] = [];
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  searchKey: string;


  @Output() createEventEmitter = new EventEmitter();
  @Output() cancelEventEmitter = new EventEmitter();

  @Output() deleteEventEmitter = new EventEmitter<any>();
  @Output() editEventEmitter = new EventEmitter<any>();
  @Output() action1EventEmitter = new EventEmitter<any>();
  @Output() action2EventEmitter = new EventEmitter<any>();
  @Output() notifyToggleFeature = new EventEmitter<any>();
  @Output() addedListEmitter = new EventEmitter<any>();
  @Output() removedListEmitter = new EventEmitter<any>();
  @Output() notifiySelectEmitter=new EventEmitter<any>();

  @Input() header: string;
  @Input() direction: string;
  @Input() createNeeded: string;
  @Input() createURL: string;
  @Input() editReq: boolean = true;
  @Input() updateURL: string;
  @Input() featureToggleURL: string;
  @Input() checkedValues:string[]=[];
  private _data: any;
  private _meta: ReportMeta[];
  get data(): any {
    return this._data;
  }
  @Input()
  set data(data: any) {
    this._data = data
    this.updateData();
  }

  get meta(): ReportMeta[] {
    return this._meta;
  }
  @Input()
  set meta(meta: ReportMeta[]) {
    this._meta = meta
    this.updateData();
  }


  ngOnInit() {
  }

  addNew() {
    this.createEventEmitter.emit("add");
  }
  deleteConfirmation: boolean = false;
  featureToggleNotify(id) {
    this.notifyToggleFeature.emit(id);
  }
  updateList(id){
    if(this.checkedValues){
      let index=this.checkedValues.indexOf(id);
      console.log(index);
      if(index==-1){
        this.checkedValues.push(id);
      }else{
        this.checkedValues.splice(index,1);
      }
    }
    this.addedListEmitter.emit(this.checkedValues);
  }
  notofiySelect(obj){
    this.notifiySelectEmitter.emit(obj);
  }
  removeList(id){
    console.log("--------");
    console.log(id);
    console.log(this.checkedValues);
    if(this.checkedValues){
      let index=this.checkedValues.indexOf(id);
      if(index!==-1){
        this.checkedValues.splice(index,1);
      }
    }
    this.removedListEmitter.emit(id);
  }
  getCheckedValue(id){
    let result=false;
    if(this.checkedValues){
      for(let str of this.checkedValues){
        if(str===id){
           result=true;
           break;
        }
      }
    }
    return result;
  }
  deleteRow(obj) {

    const dialogRef = this.dialog.open(DeleteConfirmationComponent, {
      data: { deleteConfirmation: this.deleteConfirmation }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleteEventEmitter.emit(obj);
      }

    });

  }
  editRow(obj) {
    this.editEventEmitter.emit(obj);
  }
  action1Row(obj) {
    this.action1EventEmitter.emit(obj);
  }
  action2Row(obj) {
    this.action2EventEmitter.emit(obj);
  }


  updateData() {
    let dataType: any = {};
    if (this.data && this.meta) {
      if (!(this.displayedColumns && this.displayedColumns.length == this.meta.length)) {
        this.meta.forEach(i => {
          dataType[i.key] = i.type;
          this.displayedColumns.push(i.key);
        })
      }
      this.listData = new MatTableDataSource(this.data);
      this.listData.sort = this.sort;
      this.listData.paginator = this.paginator;
      this.listData.filterPredicate = (d, filter) => {
        return this.displayedColumns.some(ele => {
          if (ele) {
            if (isNaN(d[ele])) {
              return ele != 'actions' && d[ele].toLowerCase().indexOf(filter) != -1;
            } else {
              if (dataType[ele] === "date") {
                //its a date field
                return this.getDate(d[ele]).toLowerCase().indexOf(filter) != -1;
              } else {
                return ele != 'actions' && d[ele] === filter;
              }
            }
          }
        });
      };
    }
  }
  getDate(d) {
    let date = new Date(d * 1000);
    return Util.displayDateFormat(date);
  }

  getIconName(key, list) {
    let icon = "check_box_outline_blank";
    if (list) {
      for (let i of this.data) {
        if (this.isIdExistsInData(key,list)  && key ==  i.id) {
          icon = "check_box";
          break;
        }
      }
    }
    return icon;
  }

  isIdExistsInData(id,list){
    let result=false;
    for(let obj of list){
      if(id==obj){
        result=true;
        break;
      }
    }
    return result;
  }

  onSearchClear() {
    this.searchKey = "";
    this.applyFilter();
  }

  applyFilter() {
    this.listData.filter = this.searchKey.trim().toLowerCase();
  }

  getDisplayValue(list, key) {
    let result = "";
    if (list) {
      for (let temp of list) {
        if ('storedValue' in temp) {
          if (temp.storedValue == key) {

            result = temp.displayValue;
            break;
          }
        }
      }
    }
    return result;
  }
  getImageUrlToggle(list, key) {
    let icon = "green";
    if (list) {
      for (let i of list) {
        if (key == i.id) {
          icon = "red";
          break;
        }
      }
    }

    return icon;
  }
  getImageUrlToggleLabel(list, key) {
    let icon = "on";
    if (list) {
      for (let i of list) {
        if (key == i.id) {
          icon = "off";
          break;
        }
      }
    }

    return icon;
  }
  getData(ss) {
  }
}
