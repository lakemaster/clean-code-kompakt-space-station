export interface SpaceStationStatus {
    status: StationStatus;
    statusMessage: String;
    airConsumption: number;
    weight: number;
    power: number;
    spaceStationModuleStatuses: SpaceStationModuleStatus[];
}

export interface SpaceStationModuleStatus {
  moduleName: String;
  statusMessage: String;
  airConsumption: number;
  weight: number;
  power: number;
}

export enum StationStatus {
  SLEEP,
  ERROR,
  ALL_SYSTEMS_GREEN,
  UNKNOWN
}
