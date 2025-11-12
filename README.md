JEU DE LA VIE DE CONWAY
========================

Technologies: Java, UML, Swing, Algorithmie, HashLife
Note: 18.29/20
Projet collaboratif (équipe de 3 étudiants)

DESCRIPTION
-----------
Implémentation optimisée de l'automate cellulaire de Conway avec algorithme HashLife pour les calculs à grande échelle. Développé dans le cadre de la Licence 3 Informatique à l'Université de Caen.

FONCTIONNALITÉS
---------------
- Grille interactive avec HashLife pour performances accrues
- Simulation ultra-rapide sur grandes grilles
- Patterns prédéfinis (Clignotant, Planeur, Canon)
- Interface graphique intuitive avec contrôles temps réel
- Tests unitaires complets

ARCHITECTURE
------------
Architecture MVC (Modèle-Vue-Contrôleur) avec optimisation HashLife

CONTRIBUTIONS
-------------
Équipe de 3 développeurs:

MOI (Lamia):
- Modélisation UML complète du système
- Implémentation de la logique métier et règles du jeu
- Conception des diagrammes de classes 

Personne 2:
- Développement de l'interface graphique Swing
- Gestion des événements utilisateur
- Rendu visuel de la grille

Personne 3:
- Implémentation de l'algorithme HashLife
- Optimisation du calcul de voisinage
- Développement des tests unitaires

COMPÉTENCES DÉMONTRÉES
----------------------
Techniques:
- Modélisation UML 
- Implémentation d'algorithmes complexes (HashLife)
- Architecture MVC robuste
- Interface graphique Swing
- Tests unitaires

Transversales:
- Répartition efficace des tâches
- Coordination technique d'équipe
- Intégration de modules complexes

INSTALLATION
------------
Prérequis: Java JDK 8+

Compilation:
javac -d build src/*.java

Exécution:
java -cp build Main

ALGORITHME HASHLIFE
-------------------
Notre implémentation utilise l'algorithme HashLife pour:
- Accélération exponentielle des calculs
- Gestion efficace de la mémoire
- Simulation de grandes grilles sur nombreuses générations

RÈGLES DU JEU
-------------
- Naissance: Cellule morte avec 3 voisins vivants → vivante
- Survie: Cellule vivante avec 2 ou 3 voisins → reste vivante
- Mort: Dans les autres cas → meurt ou reste morte

---
Projet académique - Université de Caen - Licence 2 Informatique
