import { TestBed, inject } from '@angular/core/testing';

import { FormStateService } from './form-state.service';

describe('FormStateService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FormStateService]
    });
  });

  it('should be created', inject([FormStateService], (service: FormStateService) => {
    expect(service).toBeTruthy();
  }));
});
