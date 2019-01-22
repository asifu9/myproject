import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LovCreateComponent } from './lov-create.component';

describe('LovCreateComponent', () => {
  let component: LovCreateComponent;
  let fixture: ComponentFixture<LovCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LovCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LovCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
