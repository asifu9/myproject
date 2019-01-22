import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MesdetailComponent } from './mesdetail.component';

describe('MesdetailComponent', () => {
  let component: MesdetailComponent;
  let fixture: ComponentFixture<MesdetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MesdetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MesdetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
