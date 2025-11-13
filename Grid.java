package gameoflife;

import neighborhood.*;
import java.util.*;


public class Grid {
    private Map<Position, Cellule> cellules;
    private int rows;
    private int cols;
    private Neighborhood neighborhood;
    private Generator generator;

    public Grid(int rows, int cols, Neighborhood neighborhood) {
        this.rows = rows;
        this.cols = cols;
        this.cellules = new HashMap<>();
        this.neighborhood = neighborhood;
        this.generator = new Generator();
        initializeGrid();
    }

    /**
     * Initialise la grille avec toutes les cellules mortes par défaut.
     */
    public final void initializeGrid() {
        cellules.clear();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Position pos = new Position(i, j);
                cellules.put(pos, new Cellule(pos, false)); // Initialisation des cellules mortes par défaut
            }
        }
    }

    /**
     * Initialise la grille avec des cellules vivantes à des positions spécifiées.
     */
    public final void initializeGrid(List<Position> initialAliveCells) {
        initializeGrid();
        for (Position pos : initialAliveCells) {
            cellules.put(pos, new Cellule(pos, true)); // Mets certaines cellules vivantes
        }
    }

    /**
     * Retourne la Map des cellules vivantes.
     */
    public Map<Position, Cellule> getCellules() {
        return cellules;
    }

    public Neighborhood getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Generator getGenerator() {
        return this.generator;
    }

    public void nextGeneration() {
        generator.nextGeneration(this);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public boolean isValidPosition(Position pos) {
        return pos.getRow() >= 0 && pos.getRow() < rows && pos.getCol() >= 0 && pos.getCol() < cols;
    }

    public void setCelluleState(Position pos, boolean alive) {
        if (!isValidPosition(pos)) return;  // position invalide

        Cellule cell = cellules.get(pos);

        if (cell != null) {
            cell.setAlive(alive);  // Si la cellule existe, on met simplement à jour son état
        } else if (alive) {
            cellules.put(pos, new Cellule(pos, true));  // Si la cellule n'existe pas et qu'elle est vivante, on l'ajoute
        }
    }


    public List<Cellule> getValidNeighbors(int[][] directions, Position pos) {
        List<Cellule> validNeighbors = new ArrayList<>();
        for (int[] dir : directions) {
            Position neighborPos = new Position(pos.getRow() + dir[0], pos.getCol() + dir[1]);
            if (isValidPosition(neighborPos) && cellules.containsKey(neighborPos)) {
                validNeighbors.add(cellules.get(neighborPos));
            }
        }
        return validNeighbors;
    }

    public boolean isAllDead() {
        for (Cellule cell : cellules.values()) {
            if (cell.isAlive()) {
                return false;
            }
        }
        return true;  // Aucune cellule vivante, la grille est vide
    }


    public void display() {
        System.out.println("Grille de Jeu de la Vie :");
        System.out.println(toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Position position = new Position(i, j);
                Cellule cell = cellules.get(position);
                sb.append(cell.isAlive() ? "x" : ".");
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Grid otherGrid = (Grid) obj;
        return cellules.equals(otherGrid.cellules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Position(rows, cols), cellules);
    }

    public void copyFrom(Grid other) {
        if (this.getRows() != other.getRows() || this.getCols() != other.getCols()) {
            throw new IllegalArgumentException("Grid dimensions must match");
        }

        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                Position pos = new Position(i, j);
                Cellule otherCell = other.getCellules().get(pos);
                this.setCelluleState(pos, otherCell != null && otherCell.isAlive());
            }
        }
    }

}
