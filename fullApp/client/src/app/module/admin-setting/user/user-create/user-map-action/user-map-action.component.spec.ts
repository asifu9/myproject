import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserMapActionComponent } from './user-map-action.component';

describe('UserMapActionComponent', () => {
  let component: UserMapActionComponent;
  let fixture: ComponentFixture<UserMapActionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserMapActionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserMapActionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
