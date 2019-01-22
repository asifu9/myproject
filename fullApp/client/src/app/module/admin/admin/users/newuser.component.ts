import { Component, OnInit } from '@angular/core';
import { Route, ActivatedRoute, Router, Params } from "@angular/router";
import { User } from "../../../shared/shared/models";
import { SessionService } from "../../../shared/shared/SessionService";
import { HttpService } from "../../../shared/shared/httpService";
declare var jQuery: any;


@Component({
  selector: 'newuser', 
  templateUrl: './newuser.component.html',
  styleUrls: ['./newuser.component.scss'],
  providers: []
})
export class NewUserComponent implements OnInit {
  name:string='';
  mode:string='';
  inputId:string;
  ngOnInit() {

  }
   constructor(private apiService:HttpService,private router: Router,private activatedRoute: ActivatedRoute,private session:SessionService) { 
      console.log(this.activatedRoute.snapshot.queryParams["name"]);
      this.name=this.activatedRoute.snapshot.queryParams["name"];
      this.mode=this.activatedRoute.snapshot.queryParams["mode"];
      this.inputId=this.activatedRoute.snapshot.queryParams["inputId"];
     
   }
//   isCreateNew:boolean=true;
//   id:string=null;
//  public user:  User=null;
//   constructor(private apiService:HttpService,private router: Router,private activatedRoute: ActivatedRoute,private session:SessionService) { 
  
//     this.id = this.activatedRoute.snapshot.params['id'];
//   }

//   ngOnInit() {
//     if(this.id==null){

//       this.user=new User();
//     }else{
//       this.apiService.getData("/User/simple/"+this.id).subscribe((data) => { 
//           this.isCreateNew=false;
//           this.user= data

//       });
//   }
//  jQuery( "#example1" ).datepicker();
//     // jQuery('#example1').calendar({type:'date',format: 'YYYY-MM-DD',
//     //       onChange: function (date, text, mode) {
//     //         console.log("=========");
//     //           console.log(date);
//     //           console.log(text);
//     //           //October 12, 2017
//     //       //  console.log(moment("Sunday, February 28, 2010").format('MM/DD/YYYY'));
//     //       }

//     //   });
//   }
//   createUser(item){
//     console.log(item);
//   }
//   updateUser(item){

//   }

}
