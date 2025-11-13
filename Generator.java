package gameoflife;

import neighborhood.*;
import rule.*;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.util.*;

public class Generator {
    private Rules rules;
    private int nbrGeneration = 0;
    private Neighborhood neighborhood;
    private static final Integer MAX_GENERATION = Integer.MAX_VALUE;


    private List<Grid> history = new ArrayList<>();

    public Generator() {
        //par defaut Conway et Moore
        neighborhood = new MooreNeighborhood();
        this.rules = null; // ConwayRule.conwayRule()
    }

    public void setRules(Rules rules) {
        this.rules = rules;
    }

    public Rules getRules() {
        return this.rules;
    }

    public int getGeneratorCount() {
        return this.nbrGeneration;
    }

    public Grid nextGeneration(Grid grid) {
        System.out.println("Génération " + this.nbrGeneration + ":");
        grid.display();

        Grid nextGrid = createNextGrid(grid);
        grid.copyFrom(nextGrid);
        nbrGeneration++;
        return nextGrid;
    }

    public Grid createNextGrid(Grid grid) {
        int row = grid.getRows();
        int col = grid.getCols();

        Grid nextGrid = new Grid(row, col, neighborhood);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                Position position = new Position(i, j);
                Cellule currentCell = grid.getCellules().get(position);

                List<Cellule> neighbors = neighborhood.getNeighbors(grid, position);
                int aliveNeighbors = (int) neighbors.stream().filter(Cellule::isAlive).count();

                Rules currentRules = (rules != null) ? rules : ConwayRule.conwayRule(); // Utiliser les règles de Conway si rules est null
                boolean nextState = currentRules.apply(currentCell, aliveNeighbors);

                nextGrid.setCelluleState(position, nextState);
            }
        }
        return nextGrid;
    }

    public int getPeriod() {
        if (history.size() < 2) {
            return -1;
        }

        Grid currentState = history.get(history.size() - 1);
        for (int period = 1; period < history.size(); period++) {
            if (history.size() % period == 0) {
                boolean cycleFound = true;
                for (int i = 0; i < history.size() - period; i++) {
                    if (!history.get(i).equals(history.get(i + period))) {
                        cycleFound = false;
                        break;
                    }
                }
                if (cycleFound && history.get(history.size() - period).equals(currentState)) {
                    return period;
                }
            }
        }
        return -1;
    }

    public int runSimulation(Grid grid) {
        if (rules == null) {
            rules = ConwayRule.conwayRule();
        }

        if (nbrGeneration >= MAX_GENERATION) {
            System.out.println("Nombre maximal de générations atteint. Simulation arrêtée.");
            return 4; // Maximum generations reached
        }

        Grid nextGrid = nextGeneration(grid);

        if (nextGrid.isAllDead()) {
            return 1; // Grid is empty
        }

        if (history.contains(nextGrid)) {
            return 2; //  Oscillation detected
        }

        history.add(nextGrid); // Add nextGrid to history instead of grid

        if (nbrGeneration >= 2 && history.get(history.size() - 2).equals(nextGrid)) {
            return 3; // Grid is stable
        }

        grid.copyFrom(nextGrid);
        return 0; // Continue simulation
    }

    public void setNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

}
