package neighborhood;

import java.util.*;

import gameoflife.*;

public class VonNeumannNeighborhood implements Neighborhood {
    
    /**
     * tableau 2D qui stocke les directions des voisins. Avec VonNeumann il n'y a que 4 voisins
    */
    private static final int[][] DIRECTIONS = {
        {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    /**
     * Retourne la liste des voisins d'une cellule selon la méthode de Von Neumann
     * @param grid La grille de cellules
     * @param position la postione de la cellule dans la grille
     * @return Liste des cellules voisines valides
     */
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