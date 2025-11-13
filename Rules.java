package rule;


import gameoflife.*;;

public class Rules implements RuleFormat {
    private  int[] bornCondition;
    private  int[] surviveCondition;
    

    public Rules(int[] bornCondition, int[] surviveCondition){
        this.bornCondition = bornCondition;
        this.surviveCondition = surviveCondition;
    }

    public int[] getBornCondition() {
        return this.bornCondition;
    }

    public int[] getSurviveCondition() {
        return this.surviveCondition;
    }

    @Override
    public boolean apply(Cellule cell, int aliveNeighbors) {
        if(!cell.isAlive()) {
            return contains(bornCondition,aliveNeighbors);
        }else{
            return contains(surviveCondition,aliveNeighbors);
        }

    }
    
    //la valeur est déja dans le tableau 
    public boolean contains(int[] array , int value){
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return true; // valeur trouvé
            }
        }
        return false; // valeur non trouvé
    }


    @Override
    public void configure(String ruleString) {
        // Default Conway's Game of Life rules (B3/S23)
        int[] defaultBornCondition = {3};
        int[] defaultSurviveCondition = {2, 3};

        try {
            // If the ruleString is empty or null, use the default Conway rules
            if (ruleString == null || ruleString.trim().isEmpty()) {
                this.bornCondition = defaultBornCondition;
                this.surviveCondition = defaultSurviveCondition;
                return;
            }

            String[] parts = ruleString.split("/");

            if (parts.length != 2) {
                throw new IllegalArgumentException("Une règle mal formatée. Manque de /");
            }

            // Extraire les conditions de naissance
            String bornPart = parts[0].replace("B-", "").trim();
            String survivePart = parts[1].replace("S-", "").trim();

            if (bornPart.isEmpty() || survivePart.isEmpty()) {
                throw new IllegalArgumentException("Les conditions de naissance ou de survie sont manquantes.");
            }

            bornCondition = parseCondition(bornPart);
            surviveCondition = parseCondition(survivePart);

        } catch (Exception e) {
            System.err.println("Erreur lors de la configuration des règles: " + e.getMessage());
            throw e;  // Propager l'exception après l'avoir loggée
        }
    }

    private int[] parseCondition(String conditionString) {
        String[] values = conditionString.split(",");
        int[] condition = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            condition[i] = parseInteger(values[i].trim());
        }
        return condition;
    }


    // Méthode utilitaire pour analyser un entier, en lançant une exception si la conversion échoue
    private int parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Les valeurs doivent être des entiers valides. Erreur sur : " + value);
        }
    }
    
}

