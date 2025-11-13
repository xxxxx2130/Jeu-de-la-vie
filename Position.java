package gameoflife;

import java.util.Objects;

public class Position {
    private int row;
    private int col;

    /**
     * @param row
     * @param col
     */
    public Position(int row, int col){
        this.row = row;
        this.col = col;
    }

    /**
     * @return row
     */
    public int getRow() {
        return this.row;
    }

    /**
     * @return col
     */
    public int getCol() {
        return this.col;
    }

    /**
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @param col
     */
    public void setCol(int col) {
        this.col = col;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position other = (Position) obj;
        return row == other.row && col == other.col;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }


}
