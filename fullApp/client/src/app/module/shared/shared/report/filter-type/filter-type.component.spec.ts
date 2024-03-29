import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FilterTypeComponent } from './filter-type.component';

describe('FilterTypeComponent', () => {
  let component: FilterTypeComponent;
  let fixture: ComponentFixture<FilterTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FilterTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FilterTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
