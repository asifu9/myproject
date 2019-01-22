import { Component, OnInit } from '@angular/core';
import { HttpService } from "../../shared/shared/httpService";
import { ActivatedRoute, Router } from "@angular/router";
import { SessionService } from "../../shared/shared/SessionService";
import { LeaveApply, LeaveApplyComment } from "../../shared/shared/models";
import { Util } from "../../shared/util";

@Component({
  selector: 'app-leave-manage',
  templateUrl: './leave-manage.component.html',
  styleUrls: ['./leave-manage.component.scss']
})
export class LeaveManageComponent implements OnInit {
  id: string;
  leaveApply: LeaveApply;
  comment: string;
  errorMessage: string;
  path: string;
  constructor(private httpService: HttpService, private activatedRoute: ActivatedRoute,
    private sessionService: SessionService, private router: Router) {
    this.id = this.activatedRoute.snapshot.params['id'];
    this.path = this.sessionService.shareObj['url'] + "/uploads";
  }

  ngOnInit() {
    this.sessionService.updateMenu("leaves");
    this.fetchData();
  }

  fetchData() {
    if (this.id) {
      this.httpService.getData("/leaveapply/" + this.id).subscribe(result => {
        if (result) {
          this.leaveApply = result;
        }
      })
    }

  }

  getPath(u) {
    if (u && u.photo) {
      return this.path + "/" + u.photo.createdBy + "/" + u.photo.albumId + "/" + u.photo.details[0].name;
    } else {
      return this.sessionService.defaultPhotoAlbum;
    }
  }

  getDate(d) {
    let date = new Date(d * 1000);
    return Util.displayDateFormat(date);
  }

  rejectLeave() {
    if (!this.comment || this.comment.length == 0) {
      this.errorMessage = "Comment is mandatory while rejecting leave";
    } else {
      this.errorMessage = "";
    }
    let com = new LeaveApplyComment();
    com.commentedBy = this.sessionService.shareObj['user'];
    com.description = this.comment;
    this.httpService.postData("/leaveapply/update/" + this.leaveApply.id + "/rejected/" + this.sessionService.shareObj['user'], com).subscribe(
      result => {
        alert("Done ok cool");
      }
    )
  }

  approveLeave() {

    let com = new LeaveApplyComment();

    if (this.comment) {
      com.commentedBy = this.sessionService.shareObj['user'];
      com.description = this.comment;
    }
    this.httpService.postData("/leaveapply/update/" + this.leaveApply.id + "/approved/" + this.sessionService.shareObj['user'], com).subscribe(
      result => {
        alert("Done ok cool");
      }
    )
  }

  okCool(ee) {
    console.log(ee);
    if (ee && ee.length > 0) {
      this.errorMessage = "";
    }
  }

}
