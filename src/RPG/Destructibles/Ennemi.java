package RPG.Destructibles;

import RPG.Joueur.Joueur;

public class Ennemi extends Destructible {
    private int LV;
    public Ennemi(int HP, int jLV) {
        super(HP);
        this.LV = jLV;
    }

    /**
     * Permet à l'ennemi d'attaquer un Joueur
     * @param j Joueur qui prendra les dégâts
     * @param dommage Dégâts à infliger au Joueur
     */
    public void attack(Joueur j, double dommage) {
        j.hit_me(dommage);
    }
}
