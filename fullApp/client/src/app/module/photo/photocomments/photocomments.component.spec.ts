import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PhotoCommentsComponent } from './photocomments.component';

describe('CommentsComponent', () => {
  let component: PhotoCommentsComponent;
  let fixture: ComponentFixture<PhotoCommentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PhotoCommentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PhotoCommentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
