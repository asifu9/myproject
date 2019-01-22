import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaveTypeCreateComponent } from './leave-type-create.component';

describe('LeaveTypeCreateComponent', () => {
  let component: LeaveTypeCreateComponent;
  let fixture: ComponentFixture<LeaveTypeCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeaveTypeCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeaveTypeCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
