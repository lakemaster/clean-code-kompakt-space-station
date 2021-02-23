package de.viadee.cleancode.spacestation.modules;

public interface Module {
    Double getWeight();
    int getPowerConsumption();
    Double getAirConsumption();
    boolean isConnected();
    DailyWorkResult doDailyWork();
    void tvController(boolean onOrOff);
    void adjustRoomTemperature(int temperature);
}
