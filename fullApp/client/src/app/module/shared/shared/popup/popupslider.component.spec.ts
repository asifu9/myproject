import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupSliderComponent } from './popupslider.component';

describe('PopupComponent', () => {
  let component: PopupSliderComponent;
  let fixture: ComponentFixture<PopupSliderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PopupSliderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PopupSliderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
