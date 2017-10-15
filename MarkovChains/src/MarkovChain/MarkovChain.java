/*
 * MarkovChain.java
 *
 * Markov chain for simulation.
 *
 * By Ryan Owens
 *
 * Date: 10/14/2017
 */
package MarkovChain;

import java.util.Random;
import java.util.Vector;

import Matrix.Matrix;

import static java.lang.Math.abs;

public class MarkovChain {
    private char[] states;
    private Matrix probabilityMatrix;
    private Random rand;
    private char initialState;
    private Vector visitedStates;

    public MarkovChain(char[] states,Matrix probabilityMatrix)
    {
        this.states = states;
        this.probabilityMatrix = probabilityMatrix;
        this.rand = new Random(System.currentTimeMillis());
    }

    public char getInitialState()
    {
        return this.initialState;
    }

    public Vector getVisitedStates() {
        return this.visitedStates;
    }

    public Matrix walk(int initialState, int walkSteps)
    {
        Matrix timeMatrix = new Matrix(this.probabilityMatrix.getRows(), walkSteps + 1, 0.0);
        Vector visitedStates = new Vector();
        if(initialState < 0)
        {
            initialState = this.rand.nextInt(this.states.length - 1);
        }
        timeMatrix.setAt(initialState, 0, 1.0);
        visitedStates.add(initialState);
        int currentState = initialState;
        double[] currentRow;
        System.out.println("Initial State: " + this.states[currentState]);
        this.initialState = this.states[currentState];
        for(int i = 0; i < walkSteps; i++)
        {
            // get the row for the current state
            currentRow = this.probabilityMatrix.getRow(currentState);
            // get the next state
            currentState = this.calculateNextState(currentRow);
            // append the current state to the visited states
            visitedStates.add(currentState);
            // calculate new timeMatrix values
            this.updateTimeMatrix(timeMatrix, visitedStates);
        }
        this.visitedStates = visitedStates;
        return timeMatrix;
    }

    private int calculateNextState(double[] row)
    {
        // Calculate the CDF
        double[] cdf = new double[row.length];
        int cnt = 0;
        for (int i = 0; i < row.length; i++)
        {
            for (int j = 0; j < row.length; j++)
            {
                if(row[j] <= row[i])
                {
                    cnt++;
                }
            }
            cdf[i] = (double)cnt / (double)row.length;
            cnt = 0;
        }
        // Get the Uniform Random Number
        double randNumber = this.rand.nextDouble(); // Uniform Random [0,1)
        // Select the value from the CDF with the closest value
        int selected = 0;
        double smallestDiff = 1.0;
        for (int i = 0; i < cdf.length; i++)
        {
            if(abs(cdf[i] - randNumber) < smallestDiff)
            {
                smallestDiff = abs(cdf[i] - randNumber);
                selected = i;
            }
        }
        return selected;
    }

    private void updateTimeMatrix(Matrix timeMatrix, Vector visitedStates)
    {
        int length = visitedStates.toArray().length;
        int round = length - 1;
        int[] counts = new int[this.states.length];
        for (Object state: visitedStates)
        {
            int s = (int)state;
            counts[s] = counts[s] + 1;
        }

        for (int i = 0; i < counts.length; i++)
        {
            double cnt = counts[i] / (double)length;
            timeMatrix.setAt(i, round, cnt);
        }
    }
}
