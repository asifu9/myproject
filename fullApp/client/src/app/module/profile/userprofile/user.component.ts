import { Component, OnInit } from '@angular/core';

import { Router,ActivatedRoute } from '@angular/router';
import { User } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { HttpService } from "../../shared/shared/httpService";


declare var jQuery:any;

@Component({
  selector: 'user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss'],
  providers: []
})
export class UserComponent implements OnInit {
  private id:string;
  public user:  User;
  constructor(private apiService:HttpService, private router: Router ,private rp:ActivatedRoute,private session:SessionService) { 
           this.id = this.rp.snapshot.params['id'];
//+) converts string 'id' to a number
this.user=new User();
       if(this.id!=null && this.id!=''){
          this.getUserById(this.id);
         //its an edit
       }else{
         //create new
          this.user=new User();
      }

  }

  ngOnInit() {

       this.id = this.rp.snapshot.params['id'];
//+) converts string 'id' to a number
       if(this.id!=null && this.id!=''){
          this.getUserById(this.id);
         //its an edit
       }else{
         //create new
          this.user=new User();
      }
       // In a real app: dispatch action to load the details here.
       jQuery('#example1').calendar({type:'date',
          onChange: function (date, text, mode) {
          
            
          }

      });
  }
cancelUser(){
this.router.navigate(['/users']);
}

getUserById(id){
  console.log("?jjjjjjjj");
   this.apiService.getData("/User/"+id).subscribe((data) => { 
      
      this.user= data;
      
      
     this.user.dob=data.dateStr;
     // this.user.dob =this.convertTodisplay(this.user.dob);
   });
}
 createUser(){
  //this.user.dob=this.parseDate(""+this.user.dob);
  this.user.dob=null;
  this.apiService.postData("/User/",this.user).subscribe((data) => { 
      
        this.router.navigate(['/users']);
  });;
        
 }
  updateUser(){

  //this.user.dob=this.parseDate(""+this.user.dob);
  this.user.dob=null;
  this.apiService.putData("/User/",this.user).subscribe((data) => { 
      
        this.router.navigate(['/users']);
  });;
        
 }

 parseDate(dateString: string): Date {
    if (dateString) {
        return new Date(dateString);
    } else {
        return null;
    }
}

convertTodisplay(timestamp):Date{
         var date = new Date(timestamp);
        var dateObject = date.getFullYear() +'/'+ ('0' + (date.getMonth() + 1)).slice(-2) +'/'+ ('0' + date.getDate()).slice(-2);
        return date;
  
  }
}
