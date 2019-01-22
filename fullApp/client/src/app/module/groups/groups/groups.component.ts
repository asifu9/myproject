import { Component, OnInit } from '@angular/core';
import { NewgroupComponent } from './newgroup.component';
import { GroupComponent } from './group.component';
import { Http, RequestOptions } from '@angular/http';
import { SessionService } from "../../shared/shared/SessionService";
import { Group } from "../../shared/shared/models";
import { HttpService } from "../../shared/shared/httpService";
@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.scss'],
  providers: [NewgroupComponent, GroupComponent]
})
export class GroupsComponent implements OnInit {

  constructor(private apiService: HttpService, private session: SessionService) { }
  private groups: Group[];
  ngOnInit() {

    this.fetchData();
  }

  fetchData() {
    this.apiService.getData("/Group/byuser/" + this.session.shareObj['wall']).subscribe((data) => {
      this.groups = data
    });
  }


}
