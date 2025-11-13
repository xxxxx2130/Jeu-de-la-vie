package hashlife;

import gameoflife.*;

public class Hashlife {
    private Generator generator;
    private Map<QuadTreeNode, QuadTreeNode> cache = new HashMap<>();

    public Hashlife(Generator generator) {
        this.generator = generator;
    }

    /**
     * Convertit une grille en un arbre QuadTreeNode.
     * 
     * @param grid la grille à convertir en arbre QuadTreeNode.
     * @require la grille doit être valide et non vide.
     * @return un arbre QuadTreeNode construit à partir des données de la grille.
    */
    public QuadTreeNode convertToQuadTreeNode(Grid grid){
        int row = grid.getRows();
        int col = grid.getCols();

        if(grid == null || row == 0 || col == 0){
            return QuadTreeNode.emptyNode(0);
        }

        int size = Math.max(row, col);
        int power = 1;
        while (power < size) { power *= 2; }
        size = power;

        int[][] buildGrid = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(i < row && j < col) {
                    buildGrid[i][j] = grid.getCellules().get(new Position(i, j)).isAlive() ? 1 : 0;
                } else {
                    buildGrid[i][j] = -1;
                }
            }
        }
        return this.buildQuadTree(buildGrid, size);
    }


    /**
     * Construit un arbre QuadTreeNode à partir d'une matrice d'entiers et d'une taille donnée.
     *
     * @param buildGrid   la matrice d'entiers à partir de laquelle construire l'arbre QuadTreeNode
     * @param size la taille de la matrice d'entiers
     * @return le nœud racine de l'arbre QuadTreeNode construit
     *
     * @require buildGrid != null
     * @require buildGrid.length == size
     * @require buildGrid[i].length == size, pour tout i allant de 0 à size-1
     * @require size > 0
     *
     * @ensure le nœud racine de l'arbre QuadTreeNode construit est retourné
     * @return un arbre construit a partir d'une matrice
    */
    public QuadTreeNode buildQuadTree(int[][] buildGrid, int startRow, int startCol, int size) {
        QuadTreeNode node;
        if(size == 1) { 
            // Créer un nœud de niveau 0 pour une cellule vivante (1) ou morte (0)
            node = new QuadTreeNode(0, buildGrid[startRow][startCol] == 1);  // true si vivante, false si morte
        } else {
            int halfSize = size / 2;

            QuadTreeNode nwNode = buildQuadTree(buildGrid, startRow, startCol, halfSize);
            QuadTreeNode neNode = buildQuadTree(buildGrid, startRow, startCol + halfSize, halfSize);
            QuadTreeNode swNode = buildQuadTree(buildGrid, startRow + halfSize, startCol, halfSize);
            QuadTreeNode seNode = buildQuadTree(buildGrid, startRow + halfSize, startCol + halfSize, halfSize);

            // Créer un nœud interne qui contient les 4 sous-arbres
            node = new QuadTreeNode(nwNode, neNode, swNode, seNode);
        }
        return node;
    }

    // Méthode de départ pour l'appel initial
    public QuadTreeNode buildQuadTree(int[][] buildGrid, int size) {
        return buildQuadTree(buildGrid, 0, 0, size);
    }

    /**
     * Sauter le nombre de générations spécifié dans le jeu de la vie.
     *
     * @param grid Une grille carrée représentant l'état initial du jeu.
     * @param generations Le nombre de générations à sauter.
     *
     * @require La grille d'entrée doit être carrée et avoir une taille supérieure ou égale à 2.
     * @require La valeur de generations doit être un entier positif ou nul.
     * @ensure La grille renvoyée est de la même taille que la grille d'entrée.
     * @ensure La grille renvoyée représente l'état du jeu après avoir sauté le nombre de générations spécifié.
     * @ensure Les cellules de la grille renvoyée sont mises à jour selon les règles du jeu de la vie.
     * @ensure Les cellules de la grille renvoyée sont soit vivantes, soit mortes.
     * 
     * @return Une grille représentant l'état du jeu après avoir sauté le nombre de générations spécifié.
    */
    public Grid jumpGeneration(Grid grid, int generations) {
        if(generations == 0) { return grid; }

        QuadTreeNode node = this.convertToQuadTreeNode(grid);
        int jump = (int) Math.pow(2, node.getLevel() - 2);
        int addedBorder = 0;

        while(generations >= jump * 2) {
            node = node.addBorder();
            jump = (int) Math.pow(2, node.getLevel() - 2);
            addedBorder++;
        }

        if(addedBorder > 0) {
            node = this.computeNextGeneration(node, true);
            for(int i = 0; i < addedBorder -1; i++) {
                node = new QuadTreeNode(
                    node.getNW().getSE(), node.getNE().getSW(),
                    node.getSW().getNE(), node.getSE().getNW()
                );
            }
            generations -= jump;
        }

        for(int i = 0; i < generations; i++) {
            node = node.addBorder();
            node = this.computeNextGeneration(node, false);
        }

        return this.convertToGrid(node);
    }

    /**
     * Calcule et renvoie le prochain niveau de la hiérarchie du QuadTreeNode à partir du nœud donné,
     * représentant l'évolution de la grille dans le temps.
     * 
     * @param node Le nœud du QuadTreeNode dont la prochaine génération doit être calculée.
     * @param fast Si `true`, optimise le calcul au détriment d'une précision maximale.
     * 
     * @require node est un nœud valide du QuadTreeNode et non `null`.
     * @require fast est un booléen (`true` ou `false`).
     * 
     * @ensure Retourne un nœud valide du QuadTreeNode représentant l'évolution du nœud donné 
     *         après une itération de l'automate cellulaire. Si le nœud est vide, retourne un 
     *         nœud vide (EMPTY). Si le mode `fast` est activé et que le nœud est au niveau supérieur,
     *         retourne un nœud optimisé sans recalculer tous les sous-nœuds.
     * 
     * @return un nouveau nœud QuadTreeNode qui combine les résultats des sous-nœuds mis à jour.
    */
    private QuadTreeNode computeNextGeneration(QuadTreeNode node, boolean fast) {
        // Mémoïsation
        if (cache.containsKey(node)) {
            return cache.get(node);
        }

        // si les 4 sous-nœuds sont vides (pas de population), pas besoin de calculer
        if (node.getPopulation() == 0 ||
            (node.getNW().getPopulation() == 0 &&
             node.getNE().getPopulation() == 0 &&
             node.getSW().getPopulation() == 0 &&
             node.getSE().getPopulation() == 0)) {
            QuadTreeNode nextNode = new QuadTreeNode(
                    node.getNW().getSE(), node.getNE().getSW(), node.getSW().getNE(), node.getSE().getNW()
            );
            cache.put(node, nextNode); // Mise en cache du résultat
            return nextNode;
        }

        // Si on est au niveau 2 (ou plus élevé), on peut appliquer l'optimisation
        if (fast && node.getLevel() > 2) {
            if (node.getPopulation() > 0) {
                QuadTreeNode nextNode = new QuadTreeNode(node.getNW(), node.getNE(), node.getSW(), node.getSE()); // Une partie du noeud est vivant
            } else {
                QuadTreeNode nextNode = QuadTreeNode.EMPTY; // Si aucune cellule n'est vivante, le nœud devient vide
            }
            cache.put(node, nextNode);
            return nextNode;
        }

        
         // Génération normale
        if (node.getLevel() == 2) {
            Grid grid = this.convertToGrid(node);
            Grid nextGrid = this.generator.nextGeneration(grid);
            QuadTreeNode nextNode = this.convertToQuadTreeNode(nextGrid);
            cache.put(node, nextNode); 
            return nextNode;
        }

         // Calcul des sous-nœuds (récursif)
        QuadTreeNode nw = node.getNW();
        QuadTreeNode ne = node.getNE();
        QuadTreeNode sw = node.getSW();
        QuadTreeNode se = node.getSE();

        QuadTreeNode nwNext = this.computeNextGeneration(nw, fast);
        QuadTreeNode neNext = this.computeNextGeneration(ne, fast);
        QuadTreeNode swNext = this.computeNextGeneration(sw, fast);
        QuadTreeNode seNext = this.computeNextGeneration(se, fast);

        QuadTreeNode nextNode = new QuadTreeNode(nwNext, neNext, swNext, seNext);
        cache.put(node, nextNode); 
        return nextNode;
    }


    /**
     * La fonction convertit un arbre QuadTreeNode en une grille (Grid) de cellules.
     * 
     * @param racine Le noeud racine de l'arbre QuadTreeNode.
     * @return Une grille (Grid) de cellules représentant l'arbre QuadTreeNode.
     * @requires racine != null
     * @ensures La grille retournée a les mêmes dimensions que l'arbre QuadTreeNode représenté par racine.
    */
    public Grid convertToGrid(QuadTreeNode racine) {
        if (racine == null) {
            throw new IllegalArgumentException("Le noeud racine ne peut pas être null.");
        }

        int size = racine.getLevel() > 0 ? (int) Math.pow(2, racine.getLevel()) : 1;
        Grid grid = new Grid(size, size);
        Cellule[][] cellules = grid.getCellules();
        this.convertToGridRecursive(racine, cellules, 0, 0, size);
        return grid;
    }

    /**
     * Convertit un QuadTreeNode en une grille de cellules.
     * 
     * @param node   Le QuadTreeNode à convertir.
     * @param cellules La grille de cellules à remplir.
     * @param x      La position en x où commencer à remplir la grille.
     * @param y      La position en y où commencer à remplir la grille.
     * @param size   La taille de la zone à remplir dans la grille.
     * @throws IllegalArgumentException Si node ou cellules est null, ou si size est négatif ou dépasse la taille de cellules.
     * @ensures La grille cellules est remplie avec les états du QuadTreeNode node, en commençant à la position (x, y) et avec une taille de size.
     */
    private void convertToGridRecursive(QuadTreeNode node, Cellule[][] cellules, int x, int y, int size) {
        if (node == null || cellules == null || size <= 0) {
            throw new IllegalArgumentException("Paramètres invalides pour convertToGridRecursive.");
        }

        if (node.getLevel() == 0) {
            cellules[x][y] = new Cellule(new Position(x, y), node.getEtat());
        } else {
            int halfSize = size / 2;
            convertToGridRecursive(node.getNW(), cellules, x, y, halfSize);
            convertToGridRecursive(node.getNE(), cellules, x, y + halfSize, halfSize);
            convertToGridRecursive(node.getSW(), cellules, x + halfSize, y, halfSize);
            convertToGridRecursive(node.getSE(), cellules, x + halfSize, y + halfSize, halfSize);
        }
    }


}