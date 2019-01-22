import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupfeedComponent } from './groupfeed.component';

describe('GroupfeedComponent', () => {
  let component: GroupfeedComponent;
  let fixture: ComponentFixture<GroupfeedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupfeedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupfeedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
