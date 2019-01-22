import { Component, OnInit,Input,AfterViewInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { SessionService } from "../../../shared/shared/SessionService";
import { Address, User } from "../../../shared/shared/models";
import { HttpService } from "../../../shared/shared/httpService";
@Component({
  selector: 'address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.scss'],
  providers:[]
})
export class AddressComponent implements OnInit,AfterViewInit {

userName:string;
  isNew: boolean = false;
  user:User;
  address: Address = new Address;
  addresses: Address[];
  isEdited:boolean=false;
  constructor(private apiService: HttpService, private session: SessionService,private router: Router,
    private rp: ActivatedRoute) { 

      let data = this.router.url.split("/");
    if (data.length == 4) {
      this.userName = data[data.length - 2];
    }
  }

  ngOnInit() {

   // this.populateAddress();
  }
  ngAfterViewInit(){
    this.populateAddress();
  }
  editAddress(item){
    this.address=item;
    this.isEdited=true;

  }
  populateAddress(){
    this.address.userId = this.session.shareObj['user'];
    if(this.userName!=null && this.userName!=''){
      this.apiService.getData("/User/userName/plain/"+this.userName).subscribe((data)=>{
        this.user=data;
           this.apiService.getData("/Address/"+this.user.id).subscribe((data)=>{
              this.address=data;
          });
      });
    
   
    }
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
      this.apiService.postData("/Address/", this.address).subscribe((data) => {
        this.isNew=false;
      });
    }
  }
}
