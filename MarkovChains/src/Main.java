import MarkovChain.MarkovChain;
import Matrix.Matrix;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        final String FILENAME = "output.txt";
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



        try {
            fw = new FileWriter(FILENAME);
            bw = new BufferedWriter(fw);

            for (int i = 0; i < states.length; i++)
            {
                bw.write("Initial State: " + states[i] + "\n");
                bw.write(timeMatrices[i].toString());
                bw.write("\n\n");
            }
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
