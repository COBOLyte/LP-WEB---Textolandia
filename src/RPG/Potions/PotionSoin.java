package RPG.Potions;

public class PotionSoin extends Potion {
    public PotionSoin(int valeur) {
        super(valeur);
        super.nom = "Regain";
    }

    public PotionSoin(int valeur, int prix) {
        super(valeur, prix);
        super.nom = "Regain";
    }

    public String toString() {
        return "\u001B[32m" + this.nom + " +" + this.valeur + "\u001B[0m";
    }
}
