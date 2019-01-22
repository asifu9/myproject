import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchtopComponent } from './searchtop.component';

describe('SearchtopComponent', () => {
  let component: SearchtopComponent;
  let fixture: ComponentFixture<SearchtopComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchtopComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchtopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
