import { Component, OnInit, Input, EventEmitter, ElementRef } from '@angular/core';
import { LoaderComponent } from '../loader/loader.component';
import { SuccessComponent } from '../success/success.component';
import { Photo, DropdownValue } from '../../shared/models';
import { DropdownComponent } from '../../shared/dropdown/dropdown.component';
import { SessionService } from '../../shared/SessionService';
import { HttpService } from "../httpService";
declare var jQuery: any;
@Component({
  selector: 'share',
  templateUrl: './share.component.html',
  styleUrls: ['./share.component.scss'],
  providers: [DropdownComponent]
})
export class ShareComponent implements OnInit {

  private path: string = '/assets/uploads';
  @Input()
  type: string;
  @Input()
  object: any;
  items: any[]=[];
  desc: string='';
  wallData: any;
  selectedItem: DropdownValue[] = new Array<DropdownValue>();

  constructor(private apiService: HttpService, private session: SessionService, private elementRef: ElementRef) {
    this.items = [{ value: '3e12753c-d249-45ae-a1df-f42bc367f799', label: 'Public' }, { value: 'NONE', label: 'Private' }];
  }

  ngOnInit() {
    
  }



  sharePhoto() {
    let userId = this.session.shareObj['user'];
    var data = [];



    if (this.selectedItem != undefined) {
      this.selectedItem.forEach(item => {
        data.push(item.value);
      })
    }

    this.apiService.postData("/share/" + this.object.id + "/" + userId + "/photo/" + this.desc, data).subscribe((result) => {


    });
  }
}
