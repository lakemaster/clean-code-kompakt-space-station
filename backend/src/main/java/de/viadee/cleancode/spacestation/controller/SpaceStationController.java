package de.viadee.cleancode.spacestation.controller;

import java.util.HashMap;
import java.util.Map;

import de.viadee.cleancode.spacestation.modules.shuttle.Shuttle;
import de.viadee.cleancode.spacestation.modules.shuttle.ShuttleType;
import de.viadee.cleancode.spacestation.service.SpaceStationModuleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.viadee.cleancode.spacestation.StationStatus;
import de.viadee.cleancode.spacestation.modules.DailyWorkResult;
import de.viadee.cleancode.spacestation.modules.habitation.HabitationModule;
import de.viadee.cleancode.spacestation.modules.power.PowerModuleWithEngine;
import de.viadee.cleancode.spacestation.modules.supply.SupplyModule;
import de.viadee.cleancode.spacestation.service.SpaceStationStatus;

@Controller
public class SpaceStationController {

    public static final String SUPPLY = "supply";
    public static final String POWER = "power";
    public static final String HABITATION = "habitation";
    public static final String SHUTTLE = "shuttle";
    public static final String OVERALL = "overall";
    @Autowired
    private SupplyModule supplyModule;

    @Autowired
    private HabitationModule habitationModule;

    @Autowired
    private PowerModuleWithEngine powerEngineModule;

    @Autowired
    private Shuttle shuttleModule;

    public HashMap<String, DailyWorkResult> doDailyWork() {
        HashMap<String, DailyWorkResult> dayWorRes = new HashMap<>();
        dayWorRes.put(SUPPLY, supplyModule.doDailyWork());
        dayWorRes.put(POWER, powerEngineModule.doDailyWork());
        dayWorRes.put(HABITATION, habitationModule.doDailyWork());
        shuttleModule.setType(ShuttleType.ORION);
        dayWorRes.put(SHUTTLE, shuttleModule.doDailyWork());
        dayWorRes.put(OVERALL, getOverallDailyWorkResult(dayWorRes));
        return dayWorRes;
    }

    private DailyWorkResult getOverallDailyWorkResult(HashMap<String, DailyWorkResult> dayWorRes) {
        DailyWorkResult overallResult = new DailyWorkResult();
        for (Map.Entry<String, DailyWorkResult> rSet : dayWorRes.entrySet()) {
            DailyWorkResult r = rSet.getValue();
            if (r != null) {
                if (r.getReturnCode().equals("-1")) {
                    overallResult.setReturnCode("-1");
                    overallResult.setErrorMessage(overallResult.getErrorMessage() + " -> " + r.getErrorMessage());
                }
                overallResult.setPower(overallResult.getPower() + r.getPower());
                if (r.getAirconsumption() != null) {
                    overallResult.setAirconsumption(overallResult.getAirconsumption() + r.getAirconsumption());
                }
                overallResult.setWeight(overallResult.getWeight() + r.getWeight());
            }
        }
        return overallResult;
    }

    public SpaceStationStatus calculateStationStatus() {
        HashMap<String, DailyWorkResult> workResults = doDailyWork();
        return getSpaceStationStatus(workResults, workResults.get("overall"));
    }

    private SpaceStationStatus getSpaceStationStatus(HashMap<String, DailyWorkResult> workResults, DailyWorkResult overallResult) {
        SpaceStationStatus spaceStationStatus;
        if (overallResult != null && overallResult.getReturnCode() != null) {
            if (overallResult.getReturnCode().equals("-1")) {
                spaceStationStatus = new SpaceStationStatus(StationStatus.ERROR, overallResult.getErrorMessage());
            } else if (overallResult.getReturnCode().equals("0") && overallResult.getPower() <= 42) {
                spaceStationStatus = new SpaceStationStatus(StationStatus.SLEEP, "Station is in sleep mode.");
            } else if (overallResult.getReturnCode().equals("0") && overallResult.getPower() > 43) {
                spaceStationStatus = new SpaceStationStatus(StationStatus.ALL_SYSTEMS_GREEN, "It's alive! It's alive!");
            } else {
                spaceStationStatus = new SpaceStationStatus(StationStatus.UNKNOWN, "Status undefined.");
            }
        } else {
            spaceStationStatus = new SpaceStationStatus(StationStatus.UNKNOWN, "Status undefined.");
        }
        if(spaceStationStatus.getStatus().equals(StationStatus.ERROR) && spaceStationStatus.getStatusMessage().equals("All good!!")) {
            throw new RuntimeException(spaceStationStatus.getStatusMessage());
        }

        spaceStationStatus.setAirConsumption(overallResult.getAirconsumption());
        spaceStationStatus.setWeight(overallResult.getWeight());
        spaceStationStatus.setPower(overallResult.getPower());

        for (Map.Entry<String, DailyWorkResult> entry : workResults.entrySet()) {
            if (!entry.getKey().equals(OVERALL)) {
                SpaceStationModuleStatus status = getModuleStatus(workResults, entry.getKey());
                spaceStationStatus.addSpaceStationModule(status);
            }
        }
        return spaceStationStatus;
    }

    private SpaceStationModuleStatus getModuleStatus(HashMap<String, DailyWorkResult> workResults, String moduleName) {
        DailyWorkResult shuttle = workResults.get(moduleName);
        return new SpaceStationModuleStatus(
                firstLetterUpperCase(moduleName),
                shuttle.getAirconsumption(),
                shuttle.getWeight(),
                shuttle.getPower(),
                shuttle.getErrorMessage()
        );
    }

    private String firstLetterUpperCase(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
