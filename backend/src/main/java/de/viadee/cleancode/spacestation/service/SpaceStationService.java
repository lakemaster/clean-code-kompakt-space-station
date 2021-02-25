package de.viadee.cleancode.spacestation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.viadee.cleancode.spacestation.controller.SpaceStationController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class SpaceStationService {
	
	@Autowired
	private SpaceStationController controller;

    @RequestMapping("/spacestation/status")
    @CrossOrigin
    public SpaceStationStatus getSpaceStationStatus() {
			return controller.calculateStationStatus();
    }

}
