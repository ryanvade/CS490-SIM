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
        double endSimTime = 10.0;       // max hours to simulate
        
        Long testSeed;                // default random seed
        testSeed = 543210L;      
        //testSeed = Long.MIN_VALUE;
        
        
        // the defined unit time here is an hour
        double jobArrivalRate = 4.0;    // average jobs per hour
        int stationServers1 = 3;
        int stationServers2 = 1;
        double serviceRate1 = 0.5;      // average jobs per hour
        double serviceRate2 = 1.0;      // average jobs per hour
        
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
        QueueStation station1 = new QueueStation("Station_1", stationServers1, serviceRate1);
        QueueStation station2 = new QueueStation("Station_2", stationServers2, serviceRate2);
        // Get Transducer
        Transducer transd = new Transducer();
        
        if (testSeed != Long.MIN_VALUE) {
            genr.setRandomSeed(testSeed);
            station1.setRandomSeed(testSeed);
            station2.setRandomSeed(testSeed);
        }
        
        // register the simulation engine to monitor component events and connect the components
        genr.register(engine);
        station1.register(engine);
        station2.register(engine);
        
        // send generator output to station 1
        // 100% of station 1 output goes to station 2
        // 20% of station 2 output goes back to station 1; the rest goes to the transducer
        genr.setQueueStation(station1);
        station1.addOutputStation(station2, 1.0);
        station2.addOutputStation(station1, 0.2);
        station2.addOutputStation(transd, 0.8);
        
        // run the simulation
        System.out.println("Beginning simulation...\n");
        genr.initialize();
        engine.simulate();
        System.out.println("\nSIMULATION COMPLETE");
        
    }
    
}
