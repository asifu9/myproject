import { Component, OnInit } from '@angular/core';
import { User } from '../shared/models';
import { SessionService } from '../shared/SessionService';
import { SearchtopComponent } from '../searchtop/searchtop.component';
import { HttpService } from "../shared/httpService";
import { Router } from "@angular/router";
declare var jQuery: any;
@Component({
  selector: 'topPanel',
  templateUrl: './top-panel.component.html',
  styleUrls: ['./top-panel.component.scss'],
  providers: [SearchtopComponent]
})
export class TopPanelComponent implements OnInit {

  constructor(private router:Router,private apiService: HttpService, private session: SessionService) { }
  userName: string = '';
  showSearchBoxCon: boolean = false;
  user: User;
  notification:Notification[];

  captureData(e) {
    // e.target.prevntDefault();
    if (e.target.value + "" + e.key != '') {
      this.showSearchBoxCon = true;
      var containerPos = jQuery('#searchItem').offset();
      jQuery("#showSearchBox").css("marginTop", containerPos.top + 32);
      console.log(jQuery('#searchItem').outerWidth());
      jQuery("#showSearchBox").css("width", jQuery('#searchItem').outerWidth() - 1);
      jQuery("#showSearchBox").css("marginLeft", containerPos.left - 119);
      jQuery("#showSearchBox").css("position", "absolute");
    }
    console.log(e);

  }
  ngOnInit() {
    
    jQuery('#searchItem')
      .dropdown();

    jQuery('#searchItem').dropdown({
    });
    //this.router.navigate(['home']);
    this.fetchData();
  }
  res:any;
  fetchData(){
    this.apiService.getData("/notificationapi/user/"+this.session.shareObj['user']+"/1").subscribe(
      result=>{
        if(result){
          this.notification =result.notification;
          this.res=result;
        }
      }
    )
  }



}
