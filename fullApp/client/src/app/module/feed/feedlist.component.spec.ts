import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedListComponent } from './feedlist.component';

describe('FeedsComponent', () => {
  let component: FeedListComponent;
  let fixture: ComponentFixture<FeedListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FeedListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
