package gameoflife;

import neighborhood.*;
import rule.*;
import java.util.*;


public class Game {
    private Grid grid;
    private Generator generator;
    private Neighborhood neighborhood;
    private Rules rules;

    public Game(int rows, int cols) {

            this.neighborhood = new MooreNeighborhood(); // Default neighborhood
            this.grid = new Grid(rows, cols, this.neighborhood);
            this.generator = new Generator();
            this.rules = ConwayRule.conwayRule(); // Default rules

    }

  //  public Game() {}

    public void setNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
        this.grid.setNeighborhood(neighborhood);
        this.generator.setNeighborhood(neighborhood);
    }

    public Neighborhood getNeighborhood() {
        return this.neighborhood;
    }

    public Rules getRule() {
        return rules;
    }

    public void setRules(Rules rules) {
        this.rules = rules;
        generator = new Generator();
        generator.setNeighborhood(this.neighborhood);
    }

    /**
     * @return
     */
    public Generator getGenerator() {
        return generator;
    }

    public void initializeGridManually() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Initialisation manuelle de la grille :");
        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getCols(); j++) {
                System.out.print("Cellule [" + i + "," + j + "] vivante ? (y/n): ");
                String input = scanner.nextLine();
                grid.setCelluleState(new Position(i, j), input.equalsIgnoreCase("y"));
            }
        }
    }

    public void start() {
       generator.nextGeneration(grid);

    }


    /**
     * Compte le nombre de cellules vivantes dans la grille.
     * @param grid La grille à analyser
     * @return Le nombre de cellules vivantes
     */
    public int countAliveCells(Grid grid) {
        int count = 0;
        for(int i = 0; i < grid.getRows(); i++) {
            for(int j = 0; j < grid.getCols(); j++) {
                Position position = new Position(i, j);
                if(grid.getCellules().get(position).isAlive()) {
                    count++;
                }
            }
        }
        return count;
    }

   // -----------------------------------------------
   public boolean[][] getnextGridState() {
       Grid nextgrid =generator.createNextGrid(grid);
       boolean[][] gridState = new boolean[nextgrid.getRows()][nextgrid.getCols()];
       for (int i = 0; i < nextgrid.getRows(); i++) {
           for (int j = 0; j < nextgrid.getCols(); j++) {
               Position pos = new Position(i, j);
               Cellule cell = nextgrid.getCellules().get(pos);
               gridState[i][j] = cell != null && cell.isAlive();
           }
       }
       return gridState;
   }

    /**
     * Converts the grid's cell states into a 2D boolean array.
     * @return A 2D boolean array representing the grid's cell states.
     */
    public boolean[][] getGridState() {
        boolean[][] gridState = new boolean[grid.getRows()][grid.getCols()];
        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getCols(); j++) {
                Position pos = new Position(i, j);
                Cellule cell = grid.getCellules().get(pos);
                gridState[i][j] = cell != null && cell.isAlive();
            }
        }
        boolean cell = true;
        gridState[1][1] = cell ;
        return gridState;
    }
    public void initializeGrid(boolean[][] gridState) {
        for (int i = 0; i < gridState.length; i++) {
            for (int j = 0; j < gridState[i].length; j++) {
                Position pos = new Position(i, j);
                grid.setCelluleState(pos, gridState[i][j]);
            }
        }
    }

    public void setGridSize(int rows, int cols) {
        this.grid = new Grid(rows, cols, this.neighborhood);
    }

    public Grid getGrid() {
        return grid;
    }
}
