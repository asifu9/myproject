import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WidgetInputfieldComponent } from './widget-inputfield.component';

describe('WidgetInputfieldComponent', () => {
  let component: WidgetInputfieldComponent;
  let fixture: ComponentFixture<WidgetInputfieldComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WidgetInputfieldComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WidgetInputfieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
