import { Component, OnInit } from '@angular/core';
import { SessionService } from "../../../shared/shared/SessionService";
import { Address } from "../../../shared/shared/models";
import { HttpService } from "../../../shared/shared/httpService";

@Component({
  selector: 'education',
  templateUrl: './education.component.html',
  styleUrls: ['./education.component.scss'],
  providers:[]
})
export class EducationComponent implements OnInit {

  isNew: boolean = false;
  address: Address = new Address;
  addresses: Address[];
  isEdited:boolean=false;
  constructor(private apiService: HttpService, private session: SessionService) { }

  ngOnInit() {

    this.populateAddress();
  }
  editAddress(item){
    this.address=item;
    this.isEdited=true;

  }
  populateAddress(){
    this.address.userId = this.session.shareObj['user'];
    this.apiService.getData("/Address/"+this.session.shareObj['user']).subscribe((data) => {
      this.addresses=data;
    });
  }
  showAddNew() {
    this.isNew = true;
    this.address = new Address();
  }
  saveAddress() {
    if(this.address.id!=null){
      //update the record
        this.apiService.putData("/Address/", this.address).subscribe((data) => {
        //this.addresses.unshift(data);
        this.isEdited=false;
        this.populateAddress();
      });

    }else{
      this.address.userId = this.session.shareObj['user'];
      this.apiService.postData( "/Address/", this.address).subscribe((data) => {
        this.isNew=false;
      });
    }
  }
}
