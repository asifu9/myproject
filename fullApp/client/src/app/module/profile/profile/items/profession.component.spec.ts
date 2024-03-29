import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfessionComponent } from './profession.component';

describe('AddressComponent', () => {
  let component: ProfessionComponent;
  let fixture: ComponentFixture<ProfessionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProfessionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfessionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
