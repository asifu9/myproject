import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupMemberComponent } from './groupmember.component';

describe('GroupfeedComponent', () => {
  let component: GroupMemberComponent;
  let fixture: ComponentFixture<GroupMemberComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupMemberComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupMemberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
