package TwoDimensionalCA;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
/*
 * Moor Network
 *
 * Represents a 2D network of CA cells in a Moore Network
 *
 * By Ryan Owens
 */
public class MooreNetwork {
    Cell[][] grid;
    ArrayList<Cell[][]> generations;
    int gridWidth = 50;
    int gridHeight = 50;
    int generationCount;
    Random rand;

    public MooreNetwork(int generationCount) {
        this.generationCount = generationCount;
        this.generations = new ArrayList<>(generationCount);
        this.rand = new Random();
        // Initialize the grid
        this.grid = new Cell[gridWidth][gridHeight];
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                double chance = rand.nextDouble();
                if(chance <= 0.5) {
                    this.grid[i][j] = new Cell(true);
                }else {
                    this.grid[i][j] = new Cell(false);
                }
            }
        }
        this.saveGeneration(this.grid);
    }

    public void simulate() {
        Cell[][] lastGeneration = this.copyGeneration(this.grid);
        Cell[] neighbors;
        for (int i = 0; i < this.generationCount; i++) {

            for(int x = 0; x < this.gridWidth; x++) {
                for(int y = 0; y < this.gridHeight; y++) {
                    // Get the neighbors of the Cell
                    neighbors = this.getNeighborsOfCell(lastGeneration, x, y);
                    // update the cell
                    this.grid[x][y].update(neighbors);
                }
            }
            // make the last generation this generation
            lastGeneration = this.copyGeneration(this.grid);
            // save the last generation
            this.saveGeneration(this.grid);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int generationStep = 0;
        if (this.generationCount >= 100) {
            generationStep = 20;
        }else {
            generationStep = (int) Math.floor((double)this.generationCount / 4.0);
        }
        for (int i = 0; i < this.gridWidth; i++) {
            builder.append(String.format("%2s", Integer.toString(i)));
            builder.append(" |");
        }
        builder.append("\n");
        for (int i = 0; i < this.generationCount; i += generationStep) {
            builder.append(this.getStringOfGeneration(this.generations.get(i)));
            builder.append("\n");
        }
        return builder.toString();
    }

    private String getStringOfGeneration(Cell[][] generation) {
        StringBuilder builder = new StringBuilder();
        Cell cell;
        for (int i = 0; i < this.gridWidth; i++) {
            for (int j = 0; j < this.gridHeight; j++) {
                cell = generation[i][j];
                builder.append(String.format("%2s", cell.toString()));
                builder.append(" |");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private Cell[] getNeighborsOfCell(Cell[][] lastGeneration, int x, int y) {
        Cell[] neighbors = new Cell[8];
        // West Cell
        if(x == 0) {
            neighbors[0] = lastGeneration[this.gridWidth - 1][y];
        }else {
            neighbors[0] = lastGeneration[x-1][y];
        }

        // East Cell
        if(x == this.gridWidth - 1) {
            neighbors[1] = lastGeneration[0][y];
        }else {
            neighbors[1] = lastGeneration[x+1][y];
        }

        // North Cell
        if(y == 0) {
            neighbors[2] = lastGeneration[x][this.gridHeight - 1];
        }else {
            neighbors[2] = lastGeneration[x][y - 1];
        }

        // South Cell
        if(y == this.gridHeight - 1) {
            neighbors[3] = lastGeneration[x][0];
        } else {
            neighbors[3] = lastGeneration[x][y + 1];
        }

        // North West Cell
        if(x == 0 && y == 0) {
            // Top Left Corner
            neighbors[4] = lastGeneration[this.gridWidth - 1][this.gridHeight -1 ];
        }else if(x == 0 && y == this.gridHeight -1 ) {
            // Bottom Left Corner
            neighbors[4] = lastGeneration[this.gridWidth - 1][y - 1];
        }else if(x == this.gridWidth - 1 && y == 0) {
            // Top Right Cell
            neighbors[4] = lastGeneration[x -1][this.gridHeight - 1];
        }else if(x == this.gridWidth -1 && y == this.gridHeight - 1) {
            // Bottom Right Cell
            neighbors[4] = lastGeneration[x - 1][y - 1];
        }else if(x == 0) {
            neighbors[4] = lastGeneration[this.gridWidth - 1][y - 1];
        } else if(y == 0) {
            neighbors[4] = lastGeneration[x-1][this.gridHeight - 1];
        } else {
            neighbors[4] = lastGeneration[x-1][y-1];
        }

        // North East Cell
        if(x == 0 && y == 0) {
            // Top Left Corner
            neighbors[5] = lastGeneration[x + 1][this.gridHeight -1 ];
        }else if(x == 0 && y == this.gridHeight -1 ) {
            // Bottom Left Corner
            neighbors[5] = lastGeneration[x + 1][y - 1];
        }else if(x == this.gridWidth - 1 && y == 0) {
            // Top Right Cell
            neighbors[5] = lastGeneration[0][this.gridHeight - 1];
        }else if(x == this.gridWidth -1 && y == this.gridHeight - 1) {
            // Bottom Right Cell
            neighbors[5] = lastGeneration[0][y - 1];
        }else if(y == 0) {
            neighbors[5] = lastGeneration[x + 1][this.gridHeight - 1];
        } else if(x == this.gridWidth - 1) {
            neighbors[5] = lastGeneration[0][y - 1];
        } else {
            neighbors[5] = lastGeneration[x + 1][y - 1];
        }

        // South West Cell
        if(x == 0 && y == 0) {
            // Top Left Corner
            neighbors[6] = lastGeneration[this.gridWidth - 1][y + 1];
        }else if(x == 0 && y == this.gridHeight -1 ) {
            // Bottom Left Corner
            neighbors[6] = lastGeneration[this.gridWidth - 1][0];
        }else if(x == this.gridWidth - 1 && y == 0) {
            // Top Right Cell
            neighbors[6] = lastGeneration[x -1][y + 1];
        }else if(x == this.gridWidth -1 && y == this.gridHeight - 1) {
            // Bottom Right Cell
            neighbors[6] = lastGeneration[x - 1][0];
        }else if(x == 0) {
            neighbors[6] = lastGeneration[this.gridWidth - 1][y + 1];
        } else if(y == this.gridHeight - 1) {
            neighbors[6] = lastGeneration[x - 1][0];
        } else {
            neighbors[6] = lastGeneration[x-1][y+1];
        }

        // South East Cell
        if(x == 0 && y == 0) {
            // Top Left Corner
            neighbors[7] = lastGeneration[x + 1][y + 1];
        }else if(x == 0 && y == this.gridHeight -1 ) {
            // Bottom Left Corner
            neighbors[7] = lastGeneration[x + 1][0];
        }else if(x == this.gridWidth - 1 && y == 0) {
            // Top Right Cell
            neighbors[7] = lastGeneration[0][y + 1];
        }else if(x == this.gridWidth -1 && y == this.gridHeight - 1) {
            // Bottom Right Cell
            neighbors[7] = lastGeneration[0][0];
        }else if(y == this.gridHeight - 1) {
            neighbors[7] = lastGeneration[x + 1][0];
        } else if(x == this.gridWidth - 1) {
            neighbors[7] = lastGeneration[0][y + 1];
        } else {
            neighbors[7] = lastGeneration[x + 1][y + 1];
        }

        return neighbors;
    }

    private void saveGeneration(Cell[][] generation) {
        this.generations.add(this.copyGeneration(generation));
    }

    private Cell[][] copyGeneration(Cell[][] generation) {
        Cell[][] newGeneration = new Cell[this.gridWidth][this.gridHeight];
        for (int i = 0; i < this.gridWidth; i++) {
            for (int j = 0; j < this.gridHeight; j++) {
                newGeneration[i][j] = new Cell(generation[i][j].getState());
            }
        }
        return newGeneration;
    }
}
