import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PhotoItemComponent } from './photoitem.component';

describe('PhotosComponent', () => {
  let component: PhotoItemComponent;
  let fixture: ComponentFixture<PhotoItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PhotoItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PhotoItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
