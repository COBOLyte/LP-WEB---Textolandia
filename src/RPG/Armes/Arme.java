package RPG.Armes;

import RPG.Destructibles.Destructible;

public abstract class Arme {
    protected String nom;
    protected double DOMMAGE_ENNEMI_RATIO, DOMMAGE_OBSTACLE_RATIO;
    protected int prixBase;
    protected int prix;

    public Arme() {}

    /**
     * @param prix Paramètre servant à l'instanciation pour la classe Magasin et montrer le prix au Joueur
     */
    public Arme(int prix) {
        this.prix = prix;
        this.prixBase = prix;
    }

    public String getNom() {
        return nom;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getPrixBase() {
        return prixBase;
    }

    public abstract void attaquer(Destructible d, double dommage);

    public abstract String ascii_art();

    public abstract String toString();
}
