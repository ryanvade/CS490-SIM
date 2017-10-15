/*
 * Main.java
 *
 * Main method for Markov chain homework.
 *
 * By Ryan Owens
 *
 * Date: 10/14/2017
 */
import MarkovChain.MarkovChain;
import Matrix.Matrix;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.StandardOpenOption;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        final String FILENAME = "output.txt",
                FILENAME2 = "output2.txt",
                FILENAME3 = "output3.txt",
                FILENAME4 = "output4.txt";
        double[][] prob = {
                {248, 62, 0, 0},
                {29, 232, 29, 0},
                {20, 0, 140, 40},
                {29, 0, 0, 261}
        };
        Matrix probMatrix = new Matrix(4, 4, prob);
        final char[] states = {'G', 'Y', 'O', 'R'};
        Matrix[] timeMatrices = new Matrix[states.length];
        MarkovChain chain = new MarkovChain(states, probMatrix);
        for (int i = 0; i < states.length; i++)
        {
            timeMatrices[i] = chain.walk(i, 30);
        }

        for (int s = 0; s < states.length; s++)
        {
            printResults(FILENAME, timeMatrices[s], states[s], null, null);
        }

        Matrix timeMatrixRandom100 = chain.walk(-1, 100);
        printResults(FILENAME2, timeMatrixRandom100, chain.getInitialState(), null, null);
        printResultsCount(FILENAME2, chain.getInitialState(), chain.getVisitedStates(), states);
        Matrix timeMatrixRandom1000 = chain.walk(-1, 1000);
        printResults(FILENAME3, timeMatrixRandom1000, chain.getInitialState(), null, null);
        printResultsCount(FILENAME3, chain.getInitialState(), chain.getVisitedStates(), states);

        for (int i = 0; i < 10; i++)
        {
            printResults(FILENAME4, chain.walk(-1, 10), chain.getInitialState(), chain.getVisitedStates(), states);
        }
    }

    private static void printResultsCount(final String FILENAME, char initialState, Vector visitedStates, char[] states)
    {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(FILENAME, true);
            bw = new BufferedWriter(fw);
            bw.write("Initial State: " + initialState + "\n");
            int[] counts = new int[states.length];
            Object[] visits = visitedStates.toArray();
            int state;
            for(int i = 0; i < visits.length; i++)
            {
                state = (int)visits[i];
                counts[state] = counts[state] + 1;
            }
            for (int i = 0; i < states.length; i++)
            {
                bw.write(states[i] + ": " + counts[i] + "\n");
            }
            bw.write("\n\n");
        }catch (IOException e)
        {
            e.printStackTrace();
        }finally {
            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }
    }

    private static void printResults(final String FILENAME, Matrix mat, char initialState, Vector visitedStates, char[] states)
    {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(FILENAME, true);
            bw = new BufferedWriter(fw);
            bw.write("Initial State: " + initialState + "\n");
            bw.write(mat.toString());
            if(visitedStates != null)
            {
                bw.write("Visited States: \n");
                for (Object s: visitedStates) {
                    bw.write(states[(int)s]);
                }
            }
            bw.write("\n\n");
        }catch (IOException e)
        {
            e.printStackTrace();
        }finally {
            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }
    }
}
