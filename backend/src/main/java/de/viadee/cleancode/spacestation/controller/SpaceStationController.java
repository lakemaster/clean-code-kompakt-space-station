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
        // DailyWorkResult overallResult2 = new DailyWorkResult() ;
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
        dayWorRes.put("overall", overallResult);
        return dayWorRes;
    }

    public SpaceStationStatus calculateStationStatus() throws Exception {
        HashMap<String, DailyWorkResult> workResults = doDailyWork();
        DailyWorkResult overallResult = workResults.get("overall");
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

        DailyWorkResult shuttle = workResults.get("shuttle");
        SpaceStationModuleStatus shuttleModuleStatus = new SpaceStationModuleStatus(
                "Shuttle",
                shuttle.getAirconsumption(),
                shuttle.getWeight(),
                shuttle.getPower(),
                shuttle.getErrorMessage()
        );

        DailyWorkResult power = workResults.get("power");
        SpaceStationModuleStatus powerModuleStatus = new SpaceStationModuleStatus(
                "Power",
                power.getAirconsumption(),
                power.getWeight(),
                power.getPower(),
                power.getErrorMessage()
        );

        /*spaceStationStatus.setPowerPackCapacity(power.getPowerPackCapacity());
        spaceStationStatus.setPowerPowerPackActive(power.isPowerPackActive());
        spaceStationStatus.setPowerSolarSailAct(power.isSolarSailActive());*/


        DailyWorkResult supply = workResults.get("supply");
        SpaceStationModuleStatus supplyModuleStatus = new SpaceStationModuleStatus(
                "Supply",
                supply.getAirconsumption(),
                supply.getWeight(),
                supply.getPower(),
                supply.getErrorMessage()
        );


        DailyWorkResult habitation = workResults.get("habitation");
        SpaceStationModuleStatus habitationModuleStatus = new SpaceStationModuleStatus(
                "Habitation",
                habitation.getAirconsumption(),
                habitation.getWeight(),
                habitation.getPower(),
                habitation.getErrorMessage()
        );

        spaceStationStatus.addSpaceStationModule(shuttleModuleStatus);
        spaceStationStatus.addSpaceStationModule(powerModuleStatus);
        spaceStationStatus.addSpaceStationModule(supplyModuleStatus);
        spaceStationStatus.addSpaceStationModule(habitationModuleStatus);

        return spaceStationStatus;


    }
}
