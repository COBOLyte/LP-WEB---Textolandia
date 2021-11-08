package RPG.Potions;

public abstract class Potion {
    protected String nom;
    protected int valeur;
    protected int prix;

    public Potion(int valeur) {
        this.valeur = valeur;
    }

    public Potion(int valeur, int prix) {
        this.valeur = valeur;
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    /**
     * @return Retourne la quantité de points à régénérer (HP ou MP, en fonction du type de potion)
     */
    public int getValeur() {
        return valeur;
    }

    public int getPrix() {
        return prix;
    }

    public abstract String toString();
}
