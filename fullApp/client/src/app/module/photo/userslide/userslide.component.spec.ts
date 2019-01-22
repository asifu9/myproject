import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserslideComponent } from './userslide.component';

describe('UserslideComponent', () => {
  let component: UserslideComponent;
  let fixture: ComponentFixture<UserslideComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserslideComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserslideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
