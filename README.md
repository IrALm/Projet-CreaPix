# Générateur d'Images avec Quadtrees

## Description : 1 ère Variante

Ce projet est une application Java permettant de générer des images en utilisant une représentation basée sur les **quadtrees**.  
Les quadtrees permettent de diviser l'image en régions distinctes pour gérer efficacement les nuances de couleurs et optimiser la gestion de la complexité visuelle.

Ce projet est conçu pour explorer les concepts algorithmiques liés aux quadtrees et fournir une solution performante pour la manipulation d'images.

---

## Description : 2 ième Variante 

La deuxième variante utilise des **Arbres Ternaires**. Ils permettent de diviser l'image en trois régions triangulairess
pour gérer les nuances de couleurs

---


## Fonctionnalités

- Génération d'images à partir d'une structure quadtree dans la 1 ère variante et à partir d'une structure 
  arbre ternaire dans la 2 ième variante.
- Gestion dynamique des régions pour ajuster la nuance des couleurs.
- Export des images générées dans des formats courants (PNG).
- Analyse visuelle des subdivisions du quadtree et de l'arbre ternaire

---

## Installation

### Prérequis

- Java 11 ou supérieur.

### Étapes d'installation

1. Clonez le dépôt :
   ```bash
   git clone https://github.com/IrALm/Projet-CreaPix.git
   cd Projet-CreaPix
2 . Pour compiler :
    ```bash
    javac -d Projet-CreaPix/bin Projet-CreaPix/src/*.java
    
3. Pour l'exécution :
4. 
   Exécution de la première variante :

        ```bash
        java -classpath bin MonBoTablo 1 LesEntrees/MesExemplesQuadtree/Exemple4.txt LesSorties/SortieQuadtree/
   
        Explication : MonBoTablo = classe contenant la fonction main
                      1 : n° de la variante à exécuter
                      chemin de fichier d'entréé où se trouve les données utiles pour la bonne éxecution du programme:
                      -LesEntrees/MesExemplesQuadtree/Exemple4.txt
                      chemin de sortie pour le resultat produit : LesSorties/SortieQuadtree/
   
    Execution pour la 2 ième variante :

        ```bash
        java -classpath bin MonBoTablo 2 LesEntrees/MesExemplesArbreTernaire/Exemple3.txt LesSorties/SortieArbreTernaire/


