package rule;


public class RuleParser {
    
    /**
     * Parse une chaîne de règles et retourne un objet RuleFormat.
     * @param ruleString la chaîne de règles saisie par l'utilisateur
     * @return un objet implémentant RuleFormat qui contient les règles configurées
     */
    public static RuleFormat parse(String ruleString) {
        if (!ruleString.contains("/")) {
            throw new IllegalArgumentException("Une règle mal formatée. Manque de /");
        }

        RuleFormat rule = new Rules(new int[]{}, new int[]{});
        rule.configure(ruleString);  // Configurer les règles en fonction de la chaîne
        return rule;
    }
}


