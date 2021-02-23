package de.viadee.cleancode.spacestation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ******************************************************
 * Diese Klasse ist public SpaceStationApplication.
 * 
 * Author: Christoph Meyer
 * Firma: viadee Unternehmensberatung AG
 * Datum: 29.11.2019
 * 
 * ******************************************************
 * zuletzt geändert am 29.11.2019 - erzeugt (MeC)
 * ******************************************************
 * 
 * @author MeC
 *
 */
@SpringBootApplication
public class SpaceStationApplication 
{

    /**
     * Die Main.
     * 
     * ******************************************************
     * zuletzt geändert am 29.11.2019 - erzeugt (MeC)
     * ******************************************************
     * 
     * @author MeC
     * @param args
     */
    public static void main(String[] args) 
    {
        //Die Spring-Boot Application runnen
        SpringApplication.run(SpaceStationApplication.class, args);
    }
}
