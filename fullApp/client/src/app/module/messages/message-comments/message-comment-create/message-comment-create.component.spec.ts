import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MessageCommentCreateComponent } from './message-comment-create.component';

describe('MessageCommentCreateComponent', () => {
  let component: MessageCommentCreateComponent;
  let fixture: ComponentFixture<MessageCommentCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MessageCommentCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MessageCommentCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
