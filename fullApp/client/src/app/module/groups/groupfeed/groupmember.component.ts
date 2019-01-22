import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { SessionService } from "../../shared/shared/SessionService";

@Component({
  selector: 'groupmember',
  templateUrl: './groupmember.component.html',
  styleUrls: ['./groupmember.component.scss'],
  providers: []
})
export class GroupMemberComponent implements OnInit {

  groupName: string;
  constructor( private router: Router, private rp: ActivatedRoute, private session: SessionService) { }

  ngOnInit() {
    let data = this.router.url.split("/");
    if (data.length == 3) {
      this.groupName = data[data.length - 1];
    }
    console.log('fdfdfdf ' + this.groupName);
  }

  

}
