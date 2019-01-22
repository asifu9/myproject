import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LikeNotifyComponent } from './like-notify.component';

describe('LikeNotifyComponent', () => {
  let component: LikeNotifyComponent;
  let fixture: ComponentFixture<LikeNotifyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LikeNotifyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LikeNotifyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
