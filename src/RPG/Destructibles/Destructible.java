package RPG.Destructibles;

public abstract class Destructible {
    protected int HP, HPMax;

    public Destructible(int HP) {
        this.HP = HP;
        this.HPMax = HP;
    }

    public int getHP() {
        return HP;
    }

    public int getHPMax() {
        return HPMax;
    }

    /**
     * Sert à gérer la prise de dégâts du Destructible.
     * @param damage Dommage infligé au Destructible
     */
    public void hit_me(double damage) {
        if (this.HP - damage < 0) this.HP = 0;
        else this.HP -= damage;
    }
}
