/*
 * MonteCarloPiCalculator.java
 *
 * Calculates PI by drawing random values, [0,1), and plugging them into the equation
 * x^2 + y^2 = 1 to see if they can be accepted.
 *
 * By Ryan Owens
 *
 * Date: 10/14/2017
 */
package MonteCarlo;

import java.util.Random;
import java.util.Vector;

public class MonteCarloPiCalculator {
    private Vector<double[]> accepted;
    private Vector<double[]> rejected;

    public MonteCarloPiCalculator()
    {
        this.accepted = new Vector<double[]>();
        this.rejected = new Vector<double[]>();
    }


    public double calculate(int points)
    {
        Random rand = new Random(System.currentTimeMillis());
        double bound = 1.0;
        double pi = 0.0;
        double randX = 0.0, randY = 0.0;
        double[] point;
        for (int i = 0; i < points; i++)
        {
            // calculate a random number
            randX = rand.nextDouble();
            randY = rand.nextDouble();
            point = new double[]{randX, randY};
            // check if it is valid
            if(accepted(randX, randY))
            {
                accepted.add(point);
            }else
            {
                rejected.add(point);
            }
        }
        double num = this.accepted.toArray().length;
        pi = 4.0 * (num / (double)points);
        return pi;
    }

    public boolean accepted(double x, double y)
    {
//        System.out.println(String.valueOf(x * x) + " + " + String.valueOf(y * y) + " <= 1");
        if((x * x) + (y*y) <= 1.0)
        {
            return true;
        }
        return false;
    }

    public int acceptCount()
    {
        return this.accepted.toArray().length;
    }
}
