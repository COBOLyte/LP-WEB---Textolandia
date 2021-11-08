package RPG.Carte;

import java.util.ArrayList;

public class Carte {
    private final ArrayList<ArrayList<String>> carteLayout;
    private final Point currentJoueurPosition;
    private String currentjoueurElement; // Variable servant à garder en mémoire, l'élément graphique sur lequel se trouve le Joueur
    private int nbJour;

    /*
     * Construction d'une carte sur un système de Matrice 2D (ArrayList (y) dans des ArrayList (x)).
     */
    public Carte() {
        this.carteLayout = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            this.carteLayout.add(new ArrayList<>());
            for (int j = 0; j < 11; j++) {
                if (i == 5 && j == 5) this.carteLayout.get(i).add("M"); // Ajout de l'élément graphique représentant le Magasin
                else if (i == 0 && j == 10) this.carteLayout.get(i).add("\u001B[33mX\u001B[0m"); // Ajout de l'élément graphique représentant l'Objectif final
                else if ((i >= 7 && i <= 9) && (j >= 6 && j <= 9)) this.carteLayout.get(i).add("\u001B[32m^\u001B[0m"); // Ajout des éléments graphiques représentant des arbres
                else if (i <= 5 && j <= 2) this.carteLayout.get(i).add("\u001B[36m~\u001B[0m"); // Ajout des éléments graphiques représentant de l'eau
                else this.carteLayout.get(i).add("*");
            }
        }
        this.currentjoueurElement = "*"; // Élément du point de départ
        this.currentJoueurPosition = new Point(10, 0);
        this.updateNewJoueurPosition();
        this.nbJour = 1;
    }

    public int getNbJour() {
        return nbJour;
    }

    public String getCurrentjoueurElement() {
        return currentjoueurElement;
    }

    public void displayCarte() {
        System.out.println("[ CARTE ] [ Jour " + nbJour + " ]");
        for (ArrayList<String> array : this.carteLayout) {
            for (String s : array) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }

    /**
     * Déplace le joueur sur la carte.
     * @param direction Lettre saisie indiquant la direction prise par le joueur
     */
    public void deplacerJoueur(String direction) {
        int x = this.currentJoueurPosition.getX();
        int y = this.currentJoueurPosition.getY();
        this.carteLayout.get(x).set(y, currentjoueurElement);

         // Z: Haut, S: Bas, Q: Gauche, D: Droite
        switch (direction.toUpperCase()) {
            case "Z" -> this.currentJoueurPosition.setX(x - 1);
            case "S" -> this.currentJoueurPosition.setX(x + 1);
            case "Q" -> this.currentJoueurPosition.setY(y - 1);
            case "D" -> this.currentJoueurPosition.setY(y + 1);
        }
        this.updateNewJoueurPosition();
        nbJour++;
    }

    /*
     * MAJ de la carte prenant en compte la nouvelle position du Joueur.
     */
    private void updateNewJoueurPosition() {
        int x = this.currentJoueurPosition.getX();
        int y = this.currentJoueurPosition.getY();

        currentjoueurElement = this.carteLayout.get(x).get(y);
        this.carteLayout.get(x).set(y, "\u001B[31mP\u001B[0m");
    }

    /**
     * Valide la direction empruntée par le joueur (permet d'éviter la sortie d'un ArrayList).
     * @param direction Direction empruntée par le Joueur
     * @return Vrai, si le Joueur peut aller dans la direction ou Faux, sinon
     */
    public boolean isInputInvalid(String direction) {
        if (direction.equalsIgnoreCase("Z") || direction.equalsIgnoreCase("S") || direction.equalsIgnoreCase("Q") || direction.equalsIgnoreCase("D")) {
            return switch (direction.toUpperCase()) {
                case "Z" -> this.currentJoueurPosition.getX() - 1 < 0;
                case "S" -> this.currentJoueurPosition.getX() + 1 >= this.carteLayout.size();
                case "Q" -> this.currentJoueurPosition.getY() - 1 < 0;
                default -> this.currentJoueurPosition.getY() + 1 >= this.carteLayout.get(0).size();
            };
        } else return false;
    }

    public boolean hasJoueurArrivedAtGoal() {
        return this.currentJoueurPosition.getX() == 0 && this.currentJoueurPosition.getY() == 10;
    }
}
