package de.viadee.cleancode.spacestation.modules;

public class DailyWorkResult {
    String returnCode;

    boolean solarSailActive = false;

    boolean powerPackActive = false;

    int powerPackCapacity;

    public boolean isSolarSailActive() {
        return solarSailActive;
    }

    public void setSolarSailActive(boolean solarSailActive) {
        this.solarSailActive = solarSailActive;
    }

    public boolean isPowerPackActive() {
        return powerPackActive;
    }

    public void setPowerPackActive(boolean powerPackActive) {
        this.powerPackActive = powerPackActive;
    }

    public int getPowerPackCapacity() {
        return powerPackCapacity;
    }

    public void setPowerPackCapacity(int powerPackCapacity) {
        this.powerPackCapacity = powerPackCapacity;
    }

    public Double getAirconsumption() {
        return airconsumption;
    }

    public void setAirconsumption(Double airconsumption) {
        this.airconsumption = airconsumption;
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

    Double airconsumption = 2.3; // Default value

    Double weight = 3000.0; // Default value

    int power = 8; // Default value

    public DailyWorkResult() {
        //Default;
    }

    public DailyWorkResult(String returnCode, String errorMessage) {
        this.returnCode = returnCode;
        this.errorMessage = errorMessage;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    String errorMessage;
}
