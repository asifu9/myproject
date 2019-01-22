import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PhotoCommentComponent } from './photocomment.component';

describe('CommentComponent', () => {
  let component: PhotoCommentComponent;
  let fixture: ComponentFixture<PhotoCommentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PhotoCommentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PhotoCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
