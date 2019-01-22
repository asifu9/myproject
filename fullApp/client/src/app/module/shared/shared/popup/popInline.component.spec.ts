import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PopInlineComponent } from './popInline.component';

describe('PopInlineComponent', () => {
  let component: PopInlineComponent;
  let fixture: ComponentFixture<PopInlineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PopInlineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PopInlineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
