/*
 * Main.java
 *
 * Uses MonteCarloPiCalculator to calculate PI using acceptance rejection.
 *
 * By Ryan Owens
 *
 * Date: 10/14/2017
 */
import MonteCarlo.MonteCarloPiCalculator;

public class Main {

    public static void main(String[] args) {
        MonteCarloPiCalculator calculator = new MonteCarloPiCalculator();

        double[] pis = new double[5];
        int[] acceptCounts = new int[5];

        pis[0] = calculator.calculate(100);
        acceptCounts[0] = calculator.acceptCount();
        System.out.println("Pi for " + 100 + " points is " + pis[0] + " with " + acceptCounts[0] + " accepted points");
        pis[1] = calculator.calculate(1000);
        acceptCounts[1] = calculator.acceptCount();
        System.out.println("Pi for " + 1000 + " points is " + pis[1]+ " with " + acceptCounts[1] + " accepted points");
        pis[2] = calculator.calculate(10000);
        acceptCounts[2] = calculator.acceptCount();
        System.out.println("Pi for " + 10000 + " points is " + pis[2]+ " with " + acceptCounts[2] + " accepted points");
        pis[3] = calculator.calculate(100000);
        acceptCounts[3] = calculator.acceptCount();
        System.out.println("Pi for " + 100000 + " points is " + pis[3]+ " with " + acceptCounts[3] + " accepted points");
        pis[4] = calculator.calculate(100000000);
        acceptCounts[4] = calculator.acceptCount();
        System.out.println("Pi for " + 100000000 + " points is " + pis[4]+ " with " + acceptCounts[4] + " accepted points");
    }
}
