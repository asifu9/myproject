import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReqNotifyComponent } from './req-notify.component';

describe('ReqNotifyComponent', () => {
  let component: ReqNotifyComponent;
  let fixture: ComponentFixture<ReqNotifyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReqNotifyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReqNotifyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
