package CellularAutomata;

import TwoDimensionalCA.MooreNetwork;
/*
 * Two Dimensional CA
 *
 * Runs a Two Dimensional CA simulation of size 50x50 with 100 generations
 *
 * By Ryan Owens
 */
public class TwoDimensional {
    public static void main(String[] args) {
        MooreNetwork network = new MooreNetwork(100);
        network.simulate();
        System.out.println(network);
    }
}
