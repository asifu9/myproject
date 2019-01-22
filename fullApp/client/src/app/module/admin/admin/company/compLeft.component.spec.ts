import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyLeftComponent } from './compLeft.component';

describe('CompanyComponent', () => {
  let component: CompanyLeftComponent;
  let fixture: ComponentFixture<CompanyLeftComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompanyLeftComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompanyLeftComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
