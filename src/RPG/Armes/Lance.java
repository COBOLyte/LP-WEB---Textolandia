package RPG.Armes;

import RPG.Destructibles.Destructible;

public class Lance extends Arme {
    public Lance() {
        super.nom = "Lance";
        super.DOMMAGE_ENNEMI_RATIO = 1.2;
        super.DOMMAGE_OBSTACLE_RATIO = 0.5;
    }

    public Lance(int prix) {
        super(prix);
        super.nom = "Lance";
        super.DOMMAGE_ENNEMI_RATIO = 1.2;
        super.DOMMAGE_OBSTACLE_RATIO = 0.5;
    }

    @Override
    public void attaquer(Destructible d, double dommage) {
        if (d.getClass().getSimpleName().equals("Ennemi")) {
            d.hit_me(dommage * DOMMAGE_ENNEMI_RATIO);
        } else {
            d.hit_me(dommage * DOMMAGE_OBSTACLE_RATIO);
        }
    }

    @Override
    public String ascii_art() {
        return "---------------------------->";
    }

    @Override
    public String toString() {
        return this.nom + " (" + "Dommage Ennemi: " + this.DOMMAGE_ENNEMI_RATIO + " Dommage Obstacle: " + this.DOMMAGE_OBSTACLE_RATIO + ")";
    }
}
