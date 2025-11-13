    package neighborhood;

    import java.util.*;
    import gameoflife.*;

    public class MargolusNeighborhood implements Neighborhood {

        @Override
        public List<Cellule> getNeighbors(Grid grid, Position position) {
            List<Cellule> neighbors = new ArrayList<>();

            // Déterminer si on est sur une génération paire ou impaire
            boolean evenGeneration = (grid.getGenerator().getGeneratorCount() % 2 == 0);

            // Obtenir les coordonnées de la cellule
            int row = position.getRow();
            int col = position.getCol();

            // Trouver le coin supérieur gauche du bloc 2x2
            int baseRow = evenGeneration ? (row / 2) * 2 : (row / 2) * 2 + 1;
            int baseCol = evenGeneration ? (col / 2) * 2 : (col / 2) * 2 + 1;

            // Ajouter toutes les cellules du bloc 2x2
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    int newRow = baseRow + i;
                    int newCol = baseCol + j;

                    Position neighborsPos = new Position(newRow, newCol);
                    if (grid.isValidPosition(neighborsPos)) {
                        Cellule neighbor = grid.getCellules().get(neighborsPos);
                        if(neighbor != null) {
                            neighbors.add(neighbor);
                        }
                    }
                }
            }
            return neighbors;
        }

    }
