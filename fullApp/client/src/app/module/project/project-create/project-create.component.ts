import { Component, OnInit, OnDestroy } from '@angular/core';
import { WidgetBase } from "../../form/widgets/widget-base";
import { FormStateService } from "../../form/widgets/form-state.service";
import { ActivatedRoute } from "@angular/router";
import { Location } from '@angular/common';
@Component({
  selector: 'app-project-create',
  templateUrl: './project-create.component.html',
  styleUrls: ['./project-create.component.scss']
})
export class ProjectCreateComponent implements OnInit,OnDestroy {
   subs=null;
   id:string;
   mode:string="1";
  ngOnDestroy(): void {
    console.log("destroued");
    if(this.subs){
      this.subs.unsubscribe();
    }
  }

  constructor(private location: Location, public formStateService:FormStateService,private activatedRoute:ActivatedRoute) { 
    this.id=this.activatedRoute.snapshot.params['id'];
    if(this.id){
      this.mode="2";
    }
  }
  
  ngOnInit() {
      this.subs=this.formStateService.onChangeEmitter.subscribe(onChange=>{
        this.onChangeEvent(onChange);
      })
  }

  onChangeEvent(event){
    console.log("onchagne event");
    console.log(event);
  }

}
