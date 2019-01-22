import { TestBed } from '@angular/core/testing';

import { TranslationPipeServiceService } from './translation-pipe-service.service';

describe('TranslationPipeServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TranslationPipeServiceService = TestBed.get(TranslationPipeServiceService);
    expect(service).toBeTruthy();
  });
});
