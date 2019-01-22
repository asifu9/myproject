import { MapUserAction } from "../../../../shared/shared/models";
import { Component, OnInit } from '@angular/core';
import { AdminService } from "../../../admin.service";
import { Department, User, UserLinkInfo, Team, Designation, ConfigValues, ReportMeta } from "../../../../shared/shared/models";
import { HttpService } from "../../../../shared/shared/httpService";
import { SessionService } from "../../../../shared/shared/SessionService";
import { Router, RouteReuseStrategy } from "@angular/router";
import { Observable } from 'rxjs';


@Component({
  selector: 'app-user-map-action',
  templateUrl: './user-map-action.component.html',
  styleUrls: ['./user-map-action.component.scss']
})
export class UserMapActionComponent implements OnInit {
  selectedTicketCategory: string;
  meta: ReportMeta[] = [];
  constructor(private httpService: HttpService, private sessionService: SessionService, private router: Router) {
    this.meta = [];
    this.meta.push(new ReportMeta("type", "type"));
    this.meta.push(new ReportMeta("value", "typeValue", this.configValues));
    this.meta.push(new ReportMeta("actions", undefined));
  }
  mapUserActions: MapUserAction[] = [];
  userId: string = "";
  user: User;
  configValues: ConfigValues[] = [];
  filteredValues: ConfigValues[] = [];
  ngOnInit() {
    let data = this.router.url.split("/");
    this.userId = data[data.length - 1];
    this.httpService.getData("/User/" + data[data.length - 1]).subscribe(res => {
      this.user = res;
    });
    this.httpService.getData("/ConfigValues/" + this.sessionService.shareObj['wall'] + "/TicketCategory/true/en_us").subscribe(
      result => {
        if (result) {
          this.configValues = result;
          this.meta = [];
          this.meta.push(new ReportMeta("type", "type"));
          this.meta.push(new ReportMeta("value", "typeValue", this.configValues));
          this.meta.push(new ReportMeta("actions", undefined));
          this.fetchData();
        }
      }
    )

  }

  fetchData() {
    this.httpService.getData("/mapuseraction/" + this.sessionService.shareObj['wall'] + "/" + this.userId).subscribe(
      result => {
        if (result && result.length > 0) {
          this.mapUserActions = result;
          this.filteredValues = [];
          for (let t of this.configValues) {
            let found = false;
            if (this.mapUserActions && this.mapUserActions.length > 0) {
              for (let c of this.mapUserActions) {
                if (t.storedValue == c.typeValue) {
                  found = true;
                  break;
                }
              }
            }
            if (!found) {
              this.filteredValues.push(t);
            }
          }
        } else {
          this.filteredValues = JSON.parse(JSON.stringify(this.configValues));
        }
      })
  }

  addTicketCategory() {
    console.log(this.selectedTicketCategory);
    let mapUserAction = new MapUserAction();
    mapUserAction.createdBy = this.sessionService.shareObj['user'];
    mapUserAction.isActive = true;
    mapUserAction.tenantId = this.sessionService.shareObj['wall'];
    mapUserAction.type = "TicketCategory";
    mapUserAction.userId = this.userId;
    mapUserAction.typeValue = this.selectedTicketCategory;
    this.httpService.postData("/mapuseraction/", mapUserAction).subscribe(
      result => {
        this.fetchData();
      }
    )
  }
  getTypeValue(i) {
    for (let o of this.configValues) {
      if (i == o.storedValue) {
        return o.displayValue;
      }
    }

  }
  deletedRow(ii) {
    this.httpService.delete("/mapuseraction/" + ii.id).subscribe(result => {
      if (result) {
        this.fetchData();
      }
    })
  }

}
