import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WidgetDropdownComponent } from './widget-dropdown.component';

describe('WidgetDropdownComponent', () => {
  let component: WidgetDropdownComponent;
  let fixture: ComponentFixture<WidgetDropdownComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WidgetDropdownComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WidgetDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
