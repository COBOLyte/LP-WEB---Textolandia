package RPG.Armes;

import RPG.Destructibles.Destructible;

public class Hache extends Arme {
    public Hache() {
        super.nom = "Hache";
        super.DOMMAGE_ENNEMI_RATIO = 0.8;
        super.DOMMAGE_OBSTACLE_RATIO = 1.2;
    }

    public Hache(int prix) {
        super(prix);
        super.nom = "Hache";
        super.DOMMAGE_ENNEMI_RATIO = 0.8;
        super.DOMMAGE_OBSTACLE_RATIO = 1.2;
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
  ,:\\      /:.
 //  \\_()_/  \\\\
||   |    |   ||
||   |    |   ||
||   |____|   ||
 \\\\  / || \\  //
  `:/  ||  \\;'
       ||
       ||
       XX
       XX
       XX
       XX
       OO
       `'
       """;
    }

    @Override
    public String toString() {
        return this.nom + " (" + "Dommage Ennemi: " + this.DOMMAGE_ENNEMI_RATIO + " Dommage Obstacle: " + this.DOMMAGE_OBSTACLE_RATIO + ")";
    }
}
