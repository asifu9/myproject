import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupfeedListComponent } from './groupfeedList.component';

describe('FeedsComponent', () => {
  let component: GroupfeedListComponent;
  let fixture: ComponentFixture<GroupfeedListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupfeedListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupfeedListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
