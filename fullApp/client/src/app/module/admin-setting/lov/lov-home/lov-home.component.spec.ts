import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LovHomeComponent } from './lov-home.component';

describe('LovHomeComponent', () => {
  let component: LovHomeComponent;
  let fixture: ComponentFixture<LovHomeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LovHomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LovHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
