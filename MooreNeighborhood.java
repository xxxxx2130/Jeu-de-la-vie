package neighborhood;

import java.util.*;
import gameoflife.*;

public class MooreNeighborhood implements Neighborhood {

    private static final int[][] DIRECTIONS = {
        {-1, -1}, {-1, 0}, {-1, 1},
        {0, -1},         {0, 1},
        {1, -1}, {1, 0},  {1, 1}
    };

    @Override
    public List<Cellule> getNeighbors(Grid grid, Position position) {
        List<Cellule> neighbors = new ArrayList<>();

        for (int[] dir : DIRECTIONS) {
            int newRow = position.getRow() + dir[0];
            int newCol = position.getCol() + dir[1];
            Position neighborPos = new Position(newRow, newCol);

            // Utiliser isValidPosition pour vérifier si la position est valide
            if (grid.isValidPosition(neighborPos)) {
                Cellule neighbor = grid.getCellules().get(neighborPos);
                if (neighbor != null) {
                    neighbors.add(neighbor);
                }
            }
        }
        return neighbors;
    }
}
