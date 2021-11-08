package RPG.Potions;

public class PotionMagie extends Potion {
    public PotionMagie(int valeur) {
        super(valeur);
        super.nom = "Vigueur";
    }

    public PotionMagie(int valeur, int prix) {
        super(valeur, prix);
        super.nom = "Vigueur";
    }

    public String toString() {
        return "\u001B[36m" + this.nom + " +" + this.valeur + "\u001B[0m";
    }
}
