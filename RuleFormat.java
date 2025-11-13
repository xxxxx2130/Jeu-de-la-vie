package rule;
import gameoflife.*;

/**
 * Interface pour les règles du Jeu de la Vie.
 */
public interface RuleFormat {

    /* Applique les règles pour une cellule 
    */
    boolean apply(Cellule cell, int aliveNeighbors);
    
    /**
      Configure les règles à partir d'une chaine saisie par l'utilisateur 
     */
    void configure(String ruleString);
}
