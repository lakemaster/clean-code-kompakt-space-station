import {BehaviorSubject} from 'rxjs';
import {SpaceStationStatus, StationStatus} from '../../models/space-station-status.model';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class SpaceStationService {

  private BACKEND_URL_SPACESTATION_STATUS = 'api/spacestation/status';
  private BACKEND_URL_HEALTH = '/actuator/health';

  private DEFAULT_SPACE_STATION_STATUS = {
    status: StationStatus.UNKNOWN,
    statusMessage: null,
    airConsumption: null,
    weight: null,
    power: null,
    spaceStationModuleStatuses: []
  };

  public spaceStationStatus = new BehaviorSubject<SpaceStationStatus>(this.DEFAULT_SPACE_STATION_STATUS);
  public spaceStationStatus$ = this.spaceStationStatus.asObservable();

  public isBackendUp  = new BehaviorSubject<boolean>(true);

  constructor(private http: HttpClient) {}



  loadSpaceStationStatus() {
    this.http.get<SpaceStationStatus>(this.BACKEND_URL_SPACESTATION_STATUS).subscribe(status => {
        this.spaceStationStatus.next(status);
      }, () => {
      this.spaceStationStatus.next(this.DEFAULT_SPACE_STATION_STATUS);
      this.isBackendUp.next(false);
    });
  }

  healthCheck() {
    this.http.get<HealthStatus>(this.BACKEND_URL_HEALTH).subscribe(applicationStatus => {
        this.isBackendUp.next(applicationStatus.status === 'UP');
    }, () => {
        this.isBackendUp.next(false);
    });
  }
}

interface HealthStatus {
  status: string;
}

