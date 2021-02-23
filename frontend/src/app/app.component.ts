import {Component, OnDestroy, OnInit, Renderer2} from '@angular/core';
import {SpaceStationService} from './services/spacestation/space-station.service';
import {SpaceStationModuleStatus, StationStatus} from './models/space-station-status.model';

@Component({
  selector: 'ccss-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {

  interval;
  currentTime: number;
  rightSideModules: SpaceStationModuleStatus[] = [];
  leftSideModules: SpaceStationModuleStatus[] = [];

  constructor(protected spaceStationService: SpaceStationService,
              private renderer: Renderer2) {
    this.spaceStationService.loadSpaceStationStatus();
    this.updateCurrentTime();
    this.interval = setInterval(() => {
      this.spaceStationService.loadSpaceStationStatus();
      this.spaceStationService.healthCheck();
      this.updateCurrentTime();
    }, 1000);
  }

  ngOnInit(): void {
      this.updateSpaceStationModulesStatus();
      this.monitorConnectionToBackend();
  }

  private updateCurrentTime() {
    this.currentTime = Date.now();
  }

  ngOnDestroy(): void {
    if (this.interval) {
      clearInterval(this.interval);
    }
  }

  private updateSpaceStationModulesStatus() {
    this.spaceStationService.spaceStationStatus$.subscribe((spaceStationStatus) => {
      const spaceStationModulesStatus = spaceStationStatus.spaceStationModuleStatuses;
      this.rightSideModules = [];
      this.leftSideModules = [];
      spaceStationModulesStatus.forEach((moduleStatus, index) => {
        if (index % 2 === 0) {
          this.leftSideModules.push(moduleStatus);
        } else {
          this.rightSideModules.push(moduleStatus);
        }
      });
    });
  }

  private monitorConnectionToBackend() {
    this.spaceStationService.isBackendUp.subscribe(isBackendUp => {
      if (!isBackendUp) {
        this.showNoConnectionErrorMessage();
      } else {
        this.hideNoConnectionErrorMessage();
      }
    });
  }

  private hideNoConnectionErrorMessage() {
    this.renderer.addClass(document.body, 'background-image-space-station');
    this.renderer.removeClass(document.body, 'background-image-no-connection');
  }

  private showNoConnectionErrorMessage() {
    this.renderer.removeClass(document.body, 'background-image-space-station');
      this.renderer.addClass(document.body, 'background-image-no-connection');
  }

  spaceStationStatusAvailable() {
    return this.spaceStationService.spaceStationStatus.getValue().statusMessage
      && !(this.spaceStationService.spaceStationStatus.getValue().status === StationStatus.UNKNOWN);
  }
}
