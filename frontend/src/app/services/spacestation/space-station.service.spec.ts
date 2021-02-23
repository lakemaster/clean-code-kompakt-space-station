import { TestBed, inject } from '@angular/core/testing';

import { SpaceStationService } from './space-station.service';

describe('SpaceStationService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SpaceStationService]
    });
  });

  it('should be created', inject([SpaceStationService], (service: SpaceStationService) => {
    expect(service).toBeTruthy();
  }));
});
