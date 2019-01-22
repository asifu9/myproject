import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectedCompanyComponent } from './selectedcompany.component';

describe('SelectedCompanyComponent', () => {
  let component: SelectedCompanyComponent;
  let fixture: ComponentFixture<SelectedCompanyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SelectedCompanyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectedCompanyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
