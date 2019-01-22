import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupMiddleComponent } from './groupmiddle.component';

describe('GroupfeedComponent', () => {
  let component: GroupMiddleComponent;
  let fixture: ComponentFixture<GroupMiddleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupMiddleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupMiddleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
