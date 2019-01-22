import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InlinePogressComponent } from './inline-pogress.component';

describe('InlinePogressComponent', () => {
  let component: InlinePogressComponent;
  let fixture: ComponentFixture<InlinePogressComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InlinePogressComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InlinePogressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
