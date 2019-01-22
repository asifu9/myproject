import { Component, OnInit } from '@angular/core';
import { AdminService } from "../admin.service";
import { Router } from "@angular/router";
import { SessionService } from "../../shared/shared/SessionService";
import { ReportMeta } from "../../shared/shared/models";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";
import { HttpService } from "../../shared/shared/httpService";

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.scss']
})
export class TeamComponent implements OnInit {
  meta: ReportMeta[] = [];
  constructor(private httpService: HttpService, private notificationService: NotificationServiceService, private adminService: AdminService, private router: Router, public session: SessionService) {
    this.adminService.adminSelectedItem = 'team';
    if (this.router.url.split("/")[this.router.url.split("/").length - 1] === 'team') {
      this.adminService.showTeamButton = true;
    } else {
      this.adminService.showTeamButton = false;
    }


  }
  hideButtons() {
    this.adminService.showTeamButton = false;
  }
  ngOnInit() {
    this.meta = [];
    this.meta.push(new ReportMeta("Name", "name"));
    this.meta.push(new ReportMeta("Action", undefined));
    this.adminService.adminSelectedItem = 'team';
    this.adminService.fetchTeam();
  }

  hideMe() {
    this.adminService.showTeamButton = false;
  }

  hideMenu() {
    this.adminService.showTeamButton = false;
  }
  showMenu() {
    this.adminService.showTeamButton = true;
  }

  deletedRow(id) {
    this.notificationService.showProgressBar("", "");
    this.httpService.delete("/team/" + id.id).subscribe(result => {
      this.notificationService.showSaveDailog("delete", "delete", false);
      this.adminService.fetchTeam();

    },
      error => {
        this.notificationService.hideProgressBar();
        this.notificationService.showErrorDialog(error);
      }
    )
  }
}
