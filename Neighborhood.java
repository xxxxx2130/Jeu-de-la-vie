package neighborhood;

import gameoflife.*;
import java.util.*;

@FunctionalInterface
public interface Neighborhood {
    List<Cellule> getNeighbors(Grid grid, Position position);

    // Méthode par défaut avec une implémentation
    default int countAliveNeighbors(Grid grid, Position position) {
        int count = 0;
        for (Cellule cell : getNeighbors(grid, position)) {
            if (cell.isAlive()) {
                count++;
            }
        }
        return count;
    }
}

