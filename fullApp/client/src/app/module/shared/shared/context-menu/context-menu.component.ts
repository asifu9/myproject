import { Component, OnInit } from '@angular/core';
import { Output } from "@angular/core";
import { EventEmitter } from "@angular/core";
import { Input } from "@angular/core";

@Component({
  selector: 'app-context-menu',
  templateUrl: './context-menu.component.html',
  styleUrls: ['./context-menu.component.css']
})
export class ContextMenuComponent implements OnInit {

  deleteButton:boolean=false;
  attachmentButton:boolean=false;
  editButton:boolean=false;
  popOutButton:boolean=false;

  @Input() id:string;
  @Output() deleteButtonAction = new EventEmitter<any>();
  @Output() attachmentButtonAction = new EventEmitter<any>();
  @Output() editButtonAction = new EventEmitter<any>();
  @Output() popOutButtonAction = new EventEmitter<any>();
  constructor() { }

  ngOnInit() {
  }

  attachment(){
    this.attachmentButtonAction.emit(this.id);
  }
    delete(){
    this.deleteButtonAction.emit(this.id);
  }
    edit(){
    this.editButtonAction.emit(this.id);
  }
    popout(){
    this.popOutButtonAction.emit(this.id);
  }

}
