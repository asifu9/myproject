import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMessageGroupComponent } from './create-message-group.component';

describe('CreateMessageGroupComponent', () => {
  let component: CreateMessageGroupComponent;
  let fixture: ComponentFixture<CreateMessageGroupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateMessageGroupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateMessageGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
