package OneDimensionalCA;

import java.util.Vector;

/*
 * Network
 *
 * Represents a 1D network of CA cells
 *
 * By Ryan Owens
 */
public class Network {
    int initialCellCount = 100;
    Vector<Cell> cells;
    Vector<Vector<Cell>> generations;
    int rule;
    int generationCount;

    public Network(int rule, int generationCount, int cellCount, String[] indices)
    {
        this.rule = rule;
        this.generationCount = generationCount;
        this.cells = new Vector<>(initialCellCount);
        this.generations = new Vector<>(generationCount);
        this.initialCellCount = cellCount;
        for(int i = 0; i < initialCellCount; i++)
        {
            this.cells.add(new Cell(rule, false));
        }
        int index;
        for(String ind: indices) {
            index = Integer.parseInt(ind);
            this.cells.set(index, new Cell(rule, true));
        }
//        System.out.println(this.getStringFromGeneration(this.cells));
        this.generations.add(this.copyGeneration(this.cells));
    }

    public void simulate() {
        Vector<Cell> lastGeneration = this.copyGeneration(this.generations.lastElement());
        for(int run = 0; run < this.generationCount; run++) {
            // update states in cells
            this.updateCells(lastGeneration);
            // update last generation to current generation
            lastGeneration = this.copyGeneration(this.cells);
            // push last generation onto the vector
            this.generations.add(this.copyGeneration(this.cells));
        }
    }

    private Vector<Cell> copyGeneration(Vector<Cell> generation)
    {
        Vector<Cell> newGeneration = new Vector<>(generation.size());
        for (Cell cell: generation) {
            newGeneration.add(new Cell(this.rule, cell.getState()));
        }
        return newGeneration;
    }

    private void updateCells(Vector<Cell> lastGeneration) {
        Cell left;
        Cell right;
        Cell current;
        for (int i = 0; i < lastGeneration.size(); i++) {
            if(i == 0)
            {
                left = lastGeneration.lastElement();
                right = lastGeneration.elementAt(i + 1);
//                System.out.print("Left: 99, Current: " + i + ", Right: " + (i + 1));
            }else if(i == lastGeneration.size() - 1) {
                left = lastGeneration.elementAt(i - 1);
                right = lastGeneration.firstElement();
//                System.out.print("Left: " + (i - 1) + ", Current: " + i + ", Right: 0");
            }else {
                left = lastGeneration.elementAt(i - 1);
                right = lastGeneration.elementAt(i + 1);
//                System.out.print("Left: " + (i-1) + ", Current: " + i + ", Right: " + (i + 1));
            }
            current = lastGeneration.elementAt(i);
            this.cells.elementAt(i).update(left, current, right);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.initialCellCount; i++) {
//            builder.append("|");
            builder.append(String.format("%2s", Integer.toString(i)));
            builder.append(" |");
        }
        builder.append("\n");
        for(Vector<Cell> generation: generations) {
            builder.append(this.getStringFromGeneration(generation));
            builder.append("\n");
        }
        return builder.toString();
    }

    private String getStringFromGeneration(Vector<Cell> generation) {
        StringBuilder builder = new StringBuilder();
        for(Cell cell: generation) {
            builder.append(String.format("%2s", cell.toString()));
            builder.append(" |");
        }
        return builder.toString();
    }
}
