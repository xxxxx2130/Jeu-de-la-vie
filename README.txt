Jeu de la Vie - Package rule

Ce package permet de définir et d'appliquer des règles au Jeu de la Vie. L'utilisateur peut spécifier des règles sous forme de chaînes de caractères et le programme les applique aux cellules de la grille.
Structure

    RuleFormat : Interface pour configurer et appliquer les règles.
    Rules : Implémentation de RuleFormat avec la logique des règles (par exemple : règles de  Conway).
    RuleParser : Permet de parser une chaine de caractères et de la convertir en une règle .

Configuration des règles

Les règles sont définies avec la syntaxe suivante :

B-<condition_naissance>/S-<condition_survie>

    B-<condition_naissance> : Conditions pour qu'une cellule morte devienne vivante (ex. B-2,3).
    S-<condition_survie> : Conditions pour qu'une cellule vivante reste vivante (ex. S-3,4).

Exemple : B-2,3/S-3,4 signifie :

    Une cellule morte devient vivante avec 2 ou 3 voisins vivants.
    Une cellule vivante survit avec 3 ou 4 voisins vivants.

