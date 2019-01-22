import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NotifiMenuComponent } from './notifi-menu.component';

describe('NotifiMenuComponent', () => {
  let component: NotifiMenuComponent;
  let fixture: ComponentFixture<NotifiMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NotifiMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NotifiMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
