package TwoDimensionalCA;

/*
 * Cell
 *
 * Represents a CA Cell
 *
 * Has a State boolean and a set of rules to follow
 *
 * By Ryan Owens
 */
public class Cell {
    boolean state;


    String ifTrue = "#";
    String ifFalse = ".";

    public Cell(boolean state) {
        this.state = state;
    }

    public boolean getState()
    {
        return this.state;
    }


    public void update(Cell[] neighbors) {
        int liveNeighborCount = 0;
        for (Cell neighbor: neighbors) {
            if(neighbor.getState()) ++liveNeighborCount;
        }
        // Check for Dead Cell first
        if(!this.state) {
            if(liveNeighborCount == 3) {
                this.state = true;
            }
        }else
        {
            if(liveNeighborCount < 2) {
                this.state = false;
            }else if(liveNeighborCount == 2 || liveNeighborCount == 3) {
                this.state = true;
            }else if(liveNeighborCount > 3) {
                this.state = true;
            }
        }
    }

    @Override
    public String toString() {
        if(state) {
            return this.ifTrue;
        }
        return this.ifFalse;
    }
}
