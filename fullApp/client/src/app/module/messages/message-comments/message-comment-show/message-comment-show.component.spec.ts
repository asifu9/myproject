import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MessageCommentShowComponent } from './message-comment-show.component';

describe('MessageCommentShowComponent', () => {
  let component: MessageCommentShowComponent;
  let fixture: ComponentFixture<MessageCommentShowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MessageCommentShowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MessageCommentShowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
