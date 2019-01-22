import { Component, OnInit, EventEmitter, ElementRef, Input, Output } from '@angular/core';
import { Photo, DropdownValue } from '../../shared/models';
import { SessionService } from '../../shared/SessionService';
import { HttpService } from "../httpService";
declare var jQuery: any;
@Component({
  selector: 'dropdown',
  templateUrl: './dropdown.component.html',
  styleUrls: ['./dropdown.component.scss']
})
export class DropdownComponent implements OnInit {

  @Input()
  dropdownURL: string;
  @Input()
  items: DropdownValue[];
  @Input()
  selectedItem: DropdownValue[] = new Array<DropdownValue>();
  @Input()
  label: string;
  @Output()
  selectedItemChange: EventEmitter<any> = new EventEmitter();


  constructor(private elementRef: ElementRef, private apiService: HttpService, private session: SessionService) {
    this.updateFirstOne();
  }

  ngOnInit(): any {
    console.log("init");
    if(this.dropdownURL){
 this.apiService.getData(this.dropdownURL).subscribe((data) => {
      this.items = data;
      this.selectedItemChange.emit(this.selectedItem);
    });
    }
   
  }
  ngAfterViewInit() {
    jQuery(this.elementRef.nativeElement).find('select').dropdown({ allowTab: false });
    jQuery(this.elementRef.nativeElement).find('select').dropdown("set selected", this.selectedItem);
  }

  updateFirstOne() {
    if (this.items != null) {
      this.items.forEach(i => {
        if (i.label == 'Current Wall') {
          this.selectedItem = new Array<DropdownValue>();
          this.selectedItem.push(i);
          this.selectedItemChange.emit(this.selectedItem);
        }
      });
    }
  }

  onChange(newValue) {
    if (null != newValue && undefined != newValue) {
      newValue.forEach(element => {
        let selectedItem1 = this.items.find(item => item.value == element);
        if (this.selectedItem == null || this.selectedItem == undefined) {
          this.selectedItem = new Array<DropdownValue>();
        }
        this.selectedItem.push(selectedItem1);
      });

    }

    this.selectedItemChange.emit(this.selectedItem);
  }

}
