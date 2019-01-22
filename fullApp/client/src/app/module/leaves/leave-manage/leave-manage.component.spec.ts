import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaveManageComponent } from './leave-manage.component';

describe('LeaveManageComponent', () => {
  let component: LeaveManageComponent;
  let fixture: ComponentFixture<LeaveManageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeaveManageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeaveManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
