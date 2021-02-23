package de.viadee.cleancode.spacestation.service;

import de.viadee.cleancode.spacestation.StationStatus;
import java.util.ArrayList;
import java.util.List;

public class SpaceStationStatus {

    private String statusMessage;
    private StationStatus status;
    private Double airConsumption;
    private Double weight;
    private int power;
    private List<SpaceStationModuleStatus> spaceStationModuleStatuses = new ArrayList<>();

    public SpaceStationStatus(StationStatus status, String statusMessage) {
        this.setStatus(status);
        this.setStatusMessage(statusMessage);
    }

    public SpaceStationStatus(StationStatus status, String statusMessage, List<SpaceStationModuleStatus> spaceStationModuleStatuses) {
        this.setStatus(status);
        this.setStatusMessage(statusMessage);
        this.setSpaceStationModuleStatuses(spaceStationModuleStatuses);
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public StationStatus getStatus() {
        return status;
    }

    public void setStatus(StationStatus status) {
        this.status = status;
    }

    public List<SpaceStationModuleStatus> getSpaceStationModuleStatuses() {
        return spaceStationModuleStatuses;
    }

    public Double getAirConsumption() {
        return airConsumption;
    }

    public void setAirConsumption(Double airConsumption) {
        this.airConsumption = airConsumption;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setSpaceStationModuleStatuses(List<SpaceStationModuleStatus> spaceStationModuleStatuses) {
        this.spaceStationModuleStatuses = spaceStationModuleStatuses;
    }

    public void addSpaceStationModule(SpaceStationModuleStatus spaceStationModuleStatus) {
        this.spaceStationModuleStatuses.add(spaceStationModuleStatus);
    }
}
