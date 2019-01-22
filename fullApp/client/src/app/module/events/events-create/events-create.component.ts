import { Component, OnInit } from '@angular/core';
import { Events, Reminders } from "../../shared/shared/models";
import { SessionService } from "../../shared/shared/SessionService";
import { FormGroup, FormControl } from "@angular/forms";
import { Router, ActivatedRoute } from "@angular/router";
import { HttpService } from "../../shared/shared/httpService";
import { Util } from "../../shared/util";
import { HttpEventType } from "@angular/common/http";
import { NotificationServiceService } from "../../shared/shared/notification-service.service";
import { TranslationService } from "../../shared/shared/translate.service";
declare var moment: any;
@Component({
  selector: 'app-events-create',
  templateUrl: './events-create.component.html',
  styleUrls: ['./events-create.component.scss']
})
export class EventsCreateComponent implements OnInit {
  readOnly: boolean = false;
  buttonValue: string = "Save";
  events: Events = new Events();
  reminders: Reminders;
  id: string;
  header: string = 'Create New Event';
  date: number;
  constructor(private translation: TranslationService, private notificationServiceService: NotificationServiceService, private httpService: HttpService, private activatedRoute: ActivatedRoute,
    private sessionService: SessionService, private router: Router) {
    this.id = this.activatedRoute.snapshot.params['id'];
  }
  eventsForm: FormGroup;
  ngOnInit() {

    this.translation.getAsyn("save").subscribe(result => {
      this.buttonValue = result;
    })
    this.createEvent();
    if (this.id) {
      this.notificationServiceService.showProgressBar("", "");
      this.httpService.getData("/events/" + this.id).subscribe(
        result => {
          if (result) {
            this.events = result;
            this.translation.getAsyn("update").subscribe(result => {
              this.buttonValue = result;
            })
            this.httpService.getData("/reminders/tenant/" + this.sessionService.shareObj['wall'] + "/event/" + this.id).subscribe(
              result => {
                this.notificationServiceService.hideProgressBar();
                if (result) {
                  this.reminders = result;
                }


              }, error => {
                this.notificationServiceService.hideProgressBar();
                this.notificationServiceService.showErrorDialog(error);
              });

          }
          this.createEvent();
        }, error => {
          this.notificationServiceService.hideProgressBar();
          this.notificationServiceService.showErrorDialog(error);
        }
      )
    }

  }

  createEvent() {
    //disabled: true

    this.eventsForm = new FormGroup({
      description: new FormControl(this.events.description),
      name: new FormControl(this.events.name),
      status: new FormControl(this.events.status),
      eventStartDate: new FormControl(Util.convertDate(this.events.eventStartDate)),
      eventEndDate: new FormControl(Util.convertDate(this.events.eventEndDate))
    });
    if (!this.events.id) {
      this.reminders = new Reminders();
    }
  }
  saveEvent() {
    console.log(this.eventsForm);
    this.notificationServiceService.showProgressBar("", "");
    if (!this.id) {
      this.events = Object.assign(this.events, ...this.eventsForm.value);
      this.events.eventEndDate = Util.convertDateToMili(this.events.eventEndDate);
      this.events.eventStartDate = Util.convertDateToMili(this.events.eventStartDate);
      this.events.status = 1;
      this.events.createdBy = this.sessionService.shareObj['user'];
      this.events.tenantId = this.sessionService.shareObj['wall'];
      this.httpService.postData("/events/", this.events).subscribe(event => {
        if (event) {
          let result: any = event;
          if (this.reminders.dates) {
            this.reminders.createdBy = this.sessionService.shareObj['user'];
            this.reminders.notificationTypes = ['email'];
            this.reminders.tenantId = this.sessionService.shareObj['wall'];
            this.reminders.type = 'event';
            this.reminders.typeValue = result.id;

            this.reminders.dates.forEach(e => {
              e = Util.convertDateToMili(e);
            });
            this.httpService.postData("/reminders/", this.reminders).subscribe(res => {
              this.notificationServiceService.hideProgressBar();
              this.notificationServiceService.showSaveDailog("event", "create", true);
              //alert(":success in create");
            })
          } else {
            this.notificationServiceService.hideProgressBar();
          }


        }else{
           this.notificationServiceService.hideProgressBar();
        }
      })
    } else {
      this.events = Object.assign(this.events, ...this.eventsForm.value);
      this.events.updatedBy = this.sessionService.shareObj['user'];
      this.events.eventEndDate = Util.convertDateToMili(this.events.eventEndDate);
      this.events.eventStartDate = Util.convertDateToMili(this.events.eventStartDate);
      this.httpService.postData("/events/", this.events).subscribe(event => {
        if (event) {
          let result: any = event;
          if (this.reminders.tenantId) {
            //already exist
            if (this.reminders.dates) {
              this.reminders.dates.forEach(e => {
                e = Util.convertDateToMili(e);
              })
              this.httpService.putData("/reminders/", this.reminders).subscribe(res => {
                 this.notificationServiceService.hideProgressBar();
                this.notificationServiceService.showSaveDailog("event", "update", true);
              },error=>{

                 this.notificationServiceService.hideProgressBar();
                 this.notificationServiceService.showErrorDialog(error);
              })
            } else {
               this.notificationServiceService.hideProgressBar();
              this.notificationServiceService.showSaveDailog("event", "update", true);
            }


          } else {
            if (this.reminders.dates) {
              this.reminders.createdBy = this.sessionService.shareObj['user'];
              this.reminders.notificationTypes = ['email'];
              this.reminders.tenantId = this.sessionService.shareObj['wall'];
              this.reminders.type = 'event';
              this.reminders.typeValue = result.id;

              this.reminders.dates.forEach(e => {
                e = Util.convertDateToMili(e);
              })
              this.httpService.postData("/reminders/", this.reminders).subscribe(res => {
                this.notificationServiceService.showSaveDailog("event", "update", true);
              })
            } else {
              this.notificationServiceService.showSaveDailog("event", "update", true);
            }
          }
        }else{
           this.notificationServiceService.hideProgressBar();
        }
      })

    }

  }

  addDate() {
    if (this.date) {
      if (this.reminders.dates) {

        this.reminders.dates.push(Util.convertDateToMili(this.date));
      } else {
        this.reminders.dates = [];
        this.reminders.dates.push(Util.convertDateToMili(this.date));
      }
    }
  }

  getDate(d) {

    return Util.convertDate(d);
  }
}
