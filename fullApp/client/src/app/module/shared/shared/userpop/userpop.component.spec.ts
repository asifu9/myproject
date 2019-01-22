import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserpopComponent } from './userpop.component';

describe('UserpopComponent', () => {
  let component: UserpopComponent;
  let fixture: ComponentFixture<UserpopComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserpopComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserpopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
