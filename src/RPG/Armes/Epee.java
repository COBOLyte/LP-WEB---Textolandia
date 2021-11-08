package RPG.Armes;

import RPG.Destructibles.Destructible;

public class Epee extends Arme {
    public Epee() {
        super.nom = "Épée";
        super.DOMMAGE_ENNEMI_RATIO = 1.3;
        super.DOMMAGE_OBSTACLE_RATIO = 0.8;
    }

    public Epee(int prix) {
        super(prix);
        super.nom = "Épée";
        super.DOMMAGE_ENNEMI_RATIO = 1.3;
        super.DOMMAGE_OBSTACLE_RATIO = 0.8;
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
        return """
      /| ________________
O|===|* >________________>
      \\|
      """;
    }

    @Override
    public String toString() {
        return this.nom + " (" + "Dommage Ennemi: " + this.DOMMAGE_ENNEMI_RATIO + " Dommage Obstacle: " + this.DOMMAGE_OBSTACLE_RATIO + ")";
    }
}
