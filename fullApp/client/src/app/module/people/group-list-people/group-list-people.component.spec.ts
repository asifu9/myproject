import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupListPeopleComponent } from './group-list-people.component';

describe('GroupListPeopleComponent', () => {
  let component: GroupListPeopleComponent;
  let fixture: ComponentFixture<GroupListPeopleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupListPeopleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupListPeopleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
