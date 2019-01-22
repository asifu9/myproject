import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WidgetAttachComponent } from './widget-attach.component';

describe('WidgetAttachComponent', () => {
  let component: WidgetAttachComponent;
  let fixture: ComponentFixture<WidgetAttachComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WidgetAttachComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WidgetAttachComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
