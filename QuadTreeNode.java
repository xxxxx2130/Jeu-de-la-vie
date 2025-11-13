package hashlife;

public class QuadTreeNode {
    private final int level; 
    private final boolean isAlive; 
    private final QuadTreeNode nw, ne, sw, se; // Sous-nœuds 
    private static final QuadTreeNode[] EMPTY_NODES = new QuadTreeNode[32]; // Cache pour les nœuds vides

    /**
     * Constructeur pour une cellule unique (niveau 0).
     * @param level Niveau de la cellule (0 pour une cellule individuelle)
     * @param alive Indique si la cellule est vivante
     */
    public QuadTreeNode(int level, boolean alive) {
        this.level = level;
        this.isAlive = alive;
        this.nw = null;
        this.ne = null;
        this.sw = null;
        this.se = null;
    }

    /**
     * Constructeur pour un bloc de cellules.
     * @param nw Sous-nœud nord-ouest
     * @param ne Sous-nœud nord-est
     * @param sw Sous-nœud sud-ouest
     * @param se Sous-nœud sud-est
     */
    public QuadTreeNode(QuadTreeNode nw, QuadTreeNode ne, QuadTreeNode sw, QuadTreeNode se) {
        this.level = nw.level + 1; 
        this.isAlive = false; // noeud interne c'est une cellule vivante 
        this.nw = nw;
        this.ne = ne;
        this.sw = sw;
        this.se = se;
    }

    // Getters
    public int getLevel() {
        return level;
    }
    public boolean isAlive() {
        return isAlive;
    }
    public QuadTreeNode getNW() {
        return nw;
    }
    public QuadTreeNode getNE() {
        return ne;
    }
    public QuadTreeNode getSW() {
        return sw;
    }
    public QuadTreeNode getSE() {
        return se;
    }

    /**
     * Crée un nœud vide d'un certain niveau dans le quadtree
     * un noeud vide représente une région composée de cellules mortes
     * @param level Niveau du nœud à générer
     * @return Un nœud vide de level
     */
    public static QuadTreeNode emptyNode(int level) {
        if (level == 0) {
            return new QuadTreeNode(0, false); // Feuille vide (cellule morte)
        }
        // Mémoïsation pour éviter de recréer des nœuds vides
        if (EMPTY_NODES[level] != null) {
            return EMPTY_NODES[level];
        }
        // Crée récursivement des sous-nœuds vides
        QuadTreeNode empty = new QuadTreeNode(
            emptyNode(level - 1),
            emptyNode(level - 1),
            emptyNode(level - 1),
            emptyNode(level - 1)
        );
        // Stocker le noeud vide créé dans le cache
        EMPTY_NODES[level] = empty; 
        return empty;
    }

    /**
     * Ajoute une bordure de cellules mortes autour du nœud.
     * @return Un nouveau nœud avec une bordure de cellules mortes ajoutée.
     */
    public QuadTreeNode addBorder() {
        if (this.level == 0) {
            //cas de bse: si c'est une feuille, on ajoute une bordure de cellule mortes,
            QuadTreeNode borderNode = emptyNode(1);
            return new QuadTreeNode(borderNode, borderNode, borderNode, this);
        } else {
            // cas récursif : on ajoute récursivement une bordure aux sous-nœuds
            QuadTreeNode nwBorder = this.nw.addBorder();
            QuadTreeNode neBorder = this.ne.addBorder();
            QuadTreeNode swBorder = this.sw.addBorder();
            QuadTreeNode seBorder = this.se.addBorder();
            return new QuadTreeNode(nwBorder, neBorder, swBorder, seBorder);
        }
    }
}