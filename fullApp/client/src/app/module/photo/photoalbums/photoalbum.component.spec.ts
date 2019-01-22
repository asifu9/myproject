import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PhotoalbumsComponent } from './photoalbums.component';

describe('PhotoalbumsComponent', () => {
  let component: PhotoalbumsComponent;
  let fixture: ComponentFixture<PhotoalbumsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PhotoalbumsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PhotoalbumsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
