package CellularAutomata;
import OneDimensionalCA.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // ask user for rule to use
        String rule = "";
        while(rule == "") {
            System.out.print("Please enter a rule to use: ");
            try {
                rule = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!rule.matches("^-?\\d+$") || Integer.parseInt(rule) < 0) {
                rule = "";
            }
        }
        String cellCount = "";
        while(cellCount == "") {
            System.out.print("Please enter a cell count: ");
            try {
                cellCount = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!cellCount.matches("^-?\\d+$") || Integer.parseInt(cellCount) < 0) {
                cellCount = "";
            }
        }
        // ask user for generations count
        String count = "";
        while(count == "") {
            System.out.print("Please enter a generation count: ");
            try {
                count = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!count.matches("^-?\\d+$") || Integer.parseInt(count) < 0) {
                count = "";
            }
        }
        // ask user for list of indices to be 1
        String indices_line = "";
        String[] indices = new String[1];
        while(indices_line == "") {
            System.out.print("Please enter a comma separated list of indices to be one: ");
            try {
                indices_line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            indices = indices_line.split(",");
            for(String index: indices) {
                if(!index.matches("^-?\\d+$") || Integer.parseInt(index) < 0 || Integer.parseInt(index) >= Integer.parseInt(cellCount)) {
                    indices_line = "";
                    break;
                }
            }
        }
        System.out.println("Rule #: " + rule);
        System.out.println("Generation Count: " + count);
        System.out.println("Indices to be one: ");
        for (String index: indices) {
            System.out.print(index + " ");
        }
        System.out.println("\n");

        Network net = new Network(Integer.parseInt(rule), Integer.parseInt(count), Integer.parseInt(cellCount), indices);
        net.simulate();
        System.out.println(net.toString());
    }
}
