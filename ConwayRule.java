package rule;

public class ConwayRule {
    
    public static Rules conwayRule() {
        return new Rules(new int[] {3}, new int[] {2, 3});
    }    
    
    /*la méthode ConwayRule() définit ces règles comme suit :

    La cellule morte devient vivante si elle a exactement 3 voisins vivants (new int[] {3}).
    La cellule vivante reste vivante si elle a exactement 2 ou 3 voisins vivants (new int[] {2, 3}). */
}
