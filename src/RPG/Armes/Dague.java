package RPG.Armes;

import RPG.Destructibles.Destructible;

public class Dague extends Arme {
    public Dague() {
        super.nom = "Dague";
        super.DOMMAGE_ENNEMI_RATIO = 1.1;
        super.DOMMAGE_OBSTACLE_RATIO = 0.7;
    }

    public Dague(int prix) {
        super(prix);
        super.nom = "Dague";
        super.DOMMAGE_ENNEMI_RATIO = 1.1;
        super.DOMMAGE_OBSTACLE_RATIO = 0.7;
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
        return "[]++++||=======>";
    }

    @Override
    public String toString() {
        return this.nom + " (" + "Dommage Ennemi: " + this.DOMMAGE_ENNEMI_RATIO + " Dommage Obstacle: " + this.DOMMAGE_OBSTACLE_RATIO + ")";
    }
}
