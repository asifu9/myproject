import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WidgetMultichoiceComponent } from './widget-multichoice.component';

describe('WidgetMultichoiceComponent', () => {
  let component: WidgetMultichoiceComponent;
  let fixture: ComponentFixture<WidgetMultichoiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WidgetMultichoiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WidgetMultichoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
