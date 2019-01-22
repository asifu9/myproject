import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupLeftComponent } from './groupleft.component';

describe('GroupfeedComponent', () => {
  let component: GroupLeftComponent;
  let fixture: ComponentFixture<GroupLeftComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupLeftComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupLeftComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
