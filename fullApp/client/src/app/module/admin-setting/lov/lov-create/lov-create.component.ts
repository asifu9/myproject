import { Component, OnInit } from '@angular/core';
import { TranslationService } from "../../../shared/shared/translate.service";
import { NotificationServiceService } from "../../../shared/shared/notification-service.service";
import { SessionService } from "../../../shared/shared/SessionService";
import { HttpService } from "../../../shared/shared/httpService";
import { ActivatedRoute } from "@angular/router";
import { Router } from "@angular/router";

@Component({
  selector: 'app-lov-create',
  templateUrl: './lov-create.component.html',
  styleUrls: ['./lov-create.component.scss']
})
export class LovCreateComponent implements OnInit {

  mode: string = "1";
  inputId: string = "";
  constructor(private translation: TranslationService, private notificationService: NotificationServiceService,
    private rp: ActivatedRoute, private route: Router,
    private sessionService: SessionService, private httpService: HttpService) {
    rp.params.subscribe(p => {
      if (p && p.id) {
        this.mode = "2";
        this.inputId = p.id;
      }

    });
  }

  ngOnInit() {
  }


}
