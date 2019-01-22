import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WidgetCheckboxComponent } from './widget-checkbox.component';

describe('WidgetCheckboxComponent', () => {
  let component: WidgetCheckboxComponent;
  let fixture: ComponentFixture<WidgetCheckboxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WidgetCheckboxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WidgetCheckboxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
