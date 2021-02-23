package de.viadee.cleancode.spacestation.service;

public class SpaceStationModuleStatus {
    private String moduleName;
    private Double airConsumption;
    private Double weight;
    private int power;
    private String statusMessage;

    public SpaceStationModuleStatus(String moduleName, Double airConsumption, Double weight, int power, String statusMessage) {
        this.moduleName = moduleName;
        this.airConsumption = airConsumption;
        this.weight = weight;
        this.power = power;
        this.statusMessage = statusMessage;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
