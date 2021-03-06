package OneDimensionalCA;
/*
 * Cell
 *
 * Represents a CA Cell
 *
 * Has a State boolean and a Rule array to follow
 *
 * By Ryan Owens
 */
public class Cell {
    boolean state;
    int rule;
    boolean[] ruleArr;
    String ifTrue = "#";
    String ifFalse = ".";

    public Cell(int rule, boolean state) {
        this.state = state;
        this.rule = rule;
        String ruleString = Integer.toString(rule, 2);
        while(ruleString.length() < 8) {
            ruleString = "0" + ruleString;
        }
        this.ruleArr = new boolean[ruleString.length()];
        for(int i = 0; i < ruleString.length(); i++) {
            ruleArr[ruleString.length() - i - 1] = ruleString.charAt(i) == '1';
        }
    }

    public boolean getState() {
        return this.state;
    }

    public int getStateAsInt() {
        if(this.state) {
            return 1;
        }
        return 0;
    }

    public void update(Cell left, Cell current, Cell right) {
        String index = "" + Integer.toString(left.getStateAsInt()) + Integer.toString(current.getStateAsInt()) + Integer.toString(right.getStateAsInt());
        int id = Integer.parseInt(index, 2);
        this.state = this.ruleArr[id];
    }

    @Override
    public String toString() {
        if(state) {
            return this.ifTrue;
        }
        return this.ifFalse;
    }
}
