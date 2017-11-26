/*
 * Copyright (c) 2017, Gary R. Mayer
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package simcomponents;

//import com.sun.corba.se.spi.transport.TransportDefault;

import java.util.Collections;
import java.util.LinkedList;

/**
 * A simulation system providing an experimental frame for a simulation engine and
 * simulatable components. The domain of this experimental frame is a queueing network
 * of servers that processes a generic job.
 * 
 * @author Gary R. Mayer
 */
public class BasicSimSystem {

    /**
     * Takes up to two (optional) arguments. The first must be a double value that
     * represents the end time of the simulation. If a second argument is given,
     * it must be a long value representing the random number generator seed.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Long testSeed;                // default random seed
        //testSeed = 543210L;
        testSeed = Long.MIN_VALUE;
        

        // number of 'days' to work
        int days = 1;
        double endSimTime = 8 * days;       // max hours to simulate
        // the defined unit time here is an hour
        double jobArrivalRate = 1.25;    // average jobs per hour
        jobArrivalRate = jobArrivalRate + (jobArrivalRate * 8);
        jobArrivalRate = jobArrivalRate + (jobArrivalRate * days); // how many work days?
        jobArrivalRate = jobArrivalRate + (jobArrivalRate * 0.05); // include percentage of 'return calls'
        System.out.println("Simulation Work Days: " + days);
        System.out.println("Job Arrival Rate for Simulation: " + jobArrivalRate);

        int callCenterServers = 3;
        int softwareTechnicianServers = 2;
        int softwareManagerServers = 1;
        int hardwareTechnicianServers = 2;
        int hardwareManagerServers = 1;
        int repairTechnicianServers = 2;

        double callCenterServiceRate = 3.0 / 60.0;
        double softwareTechnicianServiceRate = 20.0 / 60.0 ;
        double softwareManagerServiceRate = 45.0 / 60.0 ;
        double hardwareTechnicianServiceRate = 40.0 / 60.0;
        double hardwareManagerServiceRate = 60.0 / 60.0;
        double repairTechnicianServiceRate = 0.8;

        
        if (args.length > 2) {
            throw new IllegalArgumentException("A maximum of two arguments is allowed.");
        }
        
        if (args.length == 2) {
            try {
                endSimTime = Double.valueOf(args[0]);
                testSeed = Long.valueOf(args[1]);
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid arguments. When providing two"
                        + " arguments, the first must be a double value and the"
                        + " second must be a long value.");
            }            
        }
        
        else if (args.length == 1) {
            try {
                endSimTime = Double.valueOf(args[0]);
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid argument. When providing only one"
                        + " argument, the value must be a double value.");
            }            
        }
        
        // instantiate simulation components
        SimEngine engine = SimEngine.getInstance();
        engine.setEndTime(endSimTime);
        // Get Generators
        Generator genr = new Generator("Gen_1", jobArrivalRate);
        // Get Queue Stations
        QueueStation callCenter = new QueueStation("Call_Center", callCenterServers, callCenterServiceRate);
        QueueStation softwareTechnicians = new QueueStation("Software_Technicians", softwareTechnicianServers, softwareTechnicianServiceRate);
        QueueStation softwareManagers = new QueueStation("Software_Managers", softwareManagerServers, softwareManagerServiceRate);
        QueueStation hardwareTechnicians = new QueueStation("Hardware_Technicians", hardwareTechnicianServers, hardwareTechnicianServiceRate);
        QueueStation hardwareManagers = new QueueStation("Hardware_Managers", hardwareManagerServers, hardwareManagerServiceRate);
        QueueStation hardwareRepair = new QueueStation("Hardware_Repair", repairTechnicianServers, repairTechnicianServiceRate, true);
        // Get Transducers
        Transducer transd = new Transducer();
        Transducer repairTransd = new Transducer("Hardware_Repair_Transducer");
        Transducer failRepairTransd = new Transducer("Failed_Hardware_Repair_Transducer");
        
        if (testSeed != Long.MIN_VALUE) {
            genr.setRandomSeed(testSeed);
            callCenter.setRandomSeed(testSeed);
            softwareTechnicians.setRandomSeed(testSeed);
            softwareManagers.setRandomSeed(testSeed);
            hardwareTechnicians.setRandomSeed(testSeed);
            hardwareManagers.setRandomSeed(testSeed);
            hardwareRepair.setRandomSeed(testSeed);
        }
        
        // register the simulation engine to monitor component events and connect the components
        genr.register(engine);
        callCenter.register(engine);
        softwareTechnicians.register(engine);
        softwareManagers.register(engine);
        hardwareTechnicians.register(engine);
        hardwareManagers.register(engine);
        hardwareRepair.register(engine);

        // Set output stations for the queues
        genr.setQueueStation(callCenter);

        callCenter.addOutputStation(softwareTechnicians, 0.58);
        callCenter.addOutputStation(hardwareTechnicians, 0.27);
        callCenter.addOutputStation(transd, 0.15);

        softwareTechnicians.addOutputStation(softwareManagers, 0.30);
        softwareTechnicians.addOutputStation(hardwareTechnicians, 0.20);
        softwareTechnicians.addOutputStation(transd, 0.50);

        hardwareTechnicians.addOutputStation(softwareTechnicians, 0.05);
        hardwareTechnicians.addOutputStation(hardwareManagers, 0.18);
        hardwareTechnicians.addOutputStation(transd, 0.41);
        hardwareTechnicians.addOutputStation(hardwareRepair, 0.36);

        softwareManagers.addOutputStation(hardwareTechnicians, 0.20);
        softwareManagers.addOutputStation(transd, 0.80);

        hardwareManagers.addOutputStation(transd, 0.64);
        hardwareManagers.addOutputStation(hardwareRepair, 0.36);

        hardwareRepair.addOutputStation(repairTransd, 0.75);
        hardwareRepair.addOutputStation(failRepairTransd, 0.25);
        
        // run the simulation
        System.out.println("Beginning simulation...\n");
        genr.initialize();
        engine.simulate();
        System.out.println("\nSIMULATION COMPLETE");
        engine.printResults();

        callCenter.printJobResults();
        softwareTechnicians.printJobResults();
        softwareManagers.printJobResults();
        hardwareTechnicians.printJobResults();
        hardwareManagers.printJobResults();
        hardwareRepair.printJobResults();

        transd.printJobResults();
        repairTransd.printJobResults();
        failRepairTransd.printJobResults();
        
    }
    
}
