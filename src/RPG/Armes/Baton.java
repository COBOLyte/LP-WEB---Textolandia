package RPG.Armes;

import RPG.Destructibles.Destructible;

public class Baton extends Arme {
    public Baton() {
        super.nom = "Baton";
        super.DOMMAGE_ENNEMI_RATIO = 1.0;
        super.DOMMAGE_OBSTACLE_RATIO = 0.2;
    }

    public Baton(int prix) {
        super(prix);
        super.nom = "Baton";
        super.DOMMAGE_ENNEMI_RATIO = 1.0;
        super.DOMMAGE_OBSTACLE_RATIO = 0.2;
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
                        ████
                      ██  ██
                    ██  ██ \s
                  ██  ██   \s
                ██  ██     \s
              ██  ██       \s
            ██  ██         \s
          ██  ██           \s
        ██  ██             \s
      ██  ██               \s
    ██  ██                 \s
  ██  ██                   \s
██  ██                     \s
████\s
""";
    }

    @Override
    public String toString() {
        return this.nom + " (" + "Dommage Ennemi: " + this.DOMMAGE_ENNEMI_RATIO + " Dommage Obstacle: " + this.DOMMAGE_OBSTACLE_RATIO + ")";
    }
}
