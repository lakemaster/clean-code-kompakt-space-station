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

    @Autowired
    private SupplyModule supplyModule;

    @Autowired
    private HabitationModule habitationModule;

    @Autowired
    private PowerModuleWithEngine powerEngineModule;

    @Autowired
    private Shuttle shuttleModule;

    public HashMap<String, DailyWorkResult> doDailyWork() {
        HashMap<String, DailyWorkResult> dayWorRes = new HashMap<String, DailyWorkResult>();
        dayWorRes.put("supply", supplyModule.doDailyWork());
        dayWorRes.put("power", powerEngineModule.doDailyWork());
        dayWorRes.put("habitation", habitationModule.doDailyWork());
        shuttleModule.setType(ShuttleType.ORION);
        dayWorRes.put("shuttle", shuttleModule.doDailyWork());
        dayWorRes.put("overall", getOverallDailyWorkResult(dayWorRes));
        return dayWorRes;
    }

    private DailyWorkResult getOverallDailyWorkResult(HashMap<String, DailyWorkResult> dayWorRes) {
        DailyWorkResult overallResult = new DailyWorkResult();
        for (Map.Entry<String, DailyWorkResult> rSet : dayWorRes.entrySet()) {
            // evil
            DailyWorkResult r = rSet.getValue();
            if (r != null) {
                if (r.getReturnCode().equals("-1")) // TODO use Enum instead
                {
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

    public SpaceStationStatus calculateStationStatus() throws Exception {
        HashMap<String, DailyWorkResult> workResults = doDailyWork();
        return getSpaceStationStatus(workResults, workResults.get("overall"));
    }

    private SpaceStationStatus getSpaceStationStatus(HashMap<String, DailyWorkResult> workResults, DailyWorkResult overallResult) throws Exception {
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
        if(spaceStationStatus.getStatus().equals(StationStatus.ERROR) && spaceStationStatus.getStatusMessage().equals("All good!!")){
            throw new Exception(spaceStationStatus.getStatusMessage());
        }

        spaceStationStatus.setAirConsumption(overallResult.getAirconsumption());
        spaceStationStatus.setWeight(overallResult.getWeight());
        spaceStationStatus.setPower(overallResult.getPower());

        for ( Map.Entry<String, DailyWorkResult> entry : workResults.entrySet() ) {
            if (entry.getKey() != "overall") {
                SpaceStationModuleStatus status = getModuleStatus(workResults, entry.getKey());
                spaceStationStatus.addSpaceStationModule(status);
            }
        }
        return spaceStationStatus;
    }

    private SpaceStationModuleStatus getModuleStatus(HashMap<String, DailyWorkResult> workResults, String shuttle2) {
        DailyWorkResult shuttle = workResults.get(shuttle2);
        return new SpaceStationModuleStatus(
                upper(shuttle2),
                shuttle.getAirconsumption(),
                shuttle.getWeight(),
                shuttle.getPower(),
                shuttle.getErrorMessage()
        );
    }

    private String upper(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
