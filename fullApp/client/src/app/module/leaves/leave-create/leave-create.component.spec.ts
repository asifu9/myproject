import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaveCreateComponent } from './leave-create.component';

describe('LeaveCreateComponent', () => {
  let component: LeaveCreateComponent;
  let fixture: ComponentFixture<LeaveCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeaveCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeaveCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
