import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WidgetTextareaComponent } from './widget-textarea.component';

describe('WidgetTextareaComponent', () => {
  let component: WidgetTextareaComponent;
  let fixture: ComponentFixture<WidgetTextareaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WidgetTextareaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WidgetTextareaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
