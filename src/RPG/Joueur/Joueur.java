package RPG.Joueur;

import RPG.Armes.*;
import RPG.Destructibles.Destructible;
import RPG.Potions.Potion;
import RPG.Potions.PotionMagie;
import RPG.Potions.PotionSoin;

import java.util.ArrayList;

public class Joueur {
    private final String nom;
    private String classe;
    private int HP, HPMax, FOR, MP, MPMax, LV, XP, XPRequis, or;
    private double DEF_RATIO;
    private final ArrayList<Arme> armes;
    private Arme armeActuel;
    private final ArrayList<Potion> potions;

    public Joueur(String nom, int classeChoix) {
        this.nom = nom;
        this.armes = new ArrayList<>();
        this.potions = new ArrayList<>();
        this.addPotion(new PotionSoin(50));
        this.addPotion(new PotionMagie(50));
        setClasse(classeChoix);
        this.LV = 1;
        this.XP = 0;
        this.XPRequis = 100;
        this.or = 50;
    }

    /**
     * Définie la classe avec ses caractéristiques.
     * @param classeChoix Numéro de la classe choisie
     */
    private void setClasse(int classeChoix) {
        switch (classeChoix) {
            case 1 -> {
                this.classe = "Guerrier";
                this.HPMax = 180; this.HP = HPMax;
                this.FOR = 40;
                this.DEF_RATIO = 1.8;
                this.MPMax = 0; this.MP = MPMax;
                this.addArme(new Epee());
            }
            case 2 -> {
                this.classe = "Mage";
                this.HPMax = 150; this.HP = HPMax;
                this.FOR = 12;
                this.DEF_RATIO = 0.5;
                this.MPMax = 100; this.MP = MPMax;
                this.addArme(new Baton());
            }
            case 3 -> {
                this.classe = "Voleur";
                this.HPMax = 130; this.HP = HPMax;
                this.FOR = 20;
                this.DEF_RATIO = 0.8;
                this.MPMax = 0; this.MP = MPMax;
                this.addArme(new Dague());
            }
            case 4 -> {
                this.classe = "Barbare";
                this.HPMax = 200; this.HP = HPMax;
                this.FOR = 30;
                this.DEF_RATIO = 1.3;
                this.MPMax = 0; this.MP = MPMax;
                this.addArme(new Hache());
            }
            case 5 -> {
                this.classe = "Forgeron";
                this.HPMax = 160; this.HP = HPMax;
                this.FOR = 25;
                this.DEF_RATIO = 0.9;
                this.MPMax = 0; this.MP = MPMax;
                this.addArme(new Marteau());
            }
        }

        this.armeActuel = this.armes.get(0);
    }

    public void addArme(Arme a) {
        this.armes.add(a);
    }

    /**
     * Définie l'arme dont le Joueur souhaite s'équiper.
     * @param a Arme à équiper
     */
    public void setArmeActuel(Arme a) {
        this.armeActuel = a;
    }

    public Arme getArmeActuel() {
        return armeActuel;
    }

    /**
     * Augmente la quantité d'XP, si le niveau est inférieur à 99.
     * Et augmente le niveau si la quantité d'XP requise est atteinte et redéfinition de celle-ci.
     * @param XP Points d'expériences obtenus
     */
    public void addXp(int XP) {
        if (this.LV < 99) {
            this.XP += XP;
            while (this.XP >= XPRequis) {
                this.levelUp();
            }
        }
    }

    /**
     * Redéfinie les caractéristiques, suite à l'augmentation de niveau.
     */
    private void levelUp() {
        System.out.println("NIVEAU AUGMENTÉ !");
        System.out.println("LV: " + this.LV + " >> \u001B[32m" + (this.LV + 1) + "\u001B[0m");
        this.LV += 1;
        System.out.println("XP: " + this.XP + "/" + this.XPRequis + " >> " + this.XP + "/" + (this.XPRequis + 250 * this.LV));
        this.XPRequis += 250 * this.LV;
        System.out.println("HPMax: " + this.HP + "/" + this.HPMax + " >> " + (this.HPMax + 20) + "/\u001B[32m" + (this.HPMax + 20) + "\u001B[0m");
        this.HPMax += 20; this.HP = HPMax;
        System.out.println("FOR: " + this.FOR + " >> \u001B[32m" + (this.FOR + 5) + "\u001B[0m");
        this.FOR += 5;
        System.out.println("DEF: " + this.DEF_RATIO + " >> \u001B[32m" + (this.DEF_RATIO + 0.3) + "\u001B[0m");
        this.DEF_RATIO += 0.3;
        System.out.println("MPMax: " + this.MP + "/" + this.MPMax + " >> " + (this.MPMax + 10) + "/\u001B[32m" + (this.MPMax + 10) + "\u001B[0m");
        this.MPMax += 10; this.MP = MPMax;
    }

    public void addOr(int or) {
        this.or+= or;
    }

    public void addPotion(Potion p) {
        this.potions.add(p);
    }

    public void useOr(int or) {
        this.or-= or;
    }

    public int usePotion(int indexPotion) {
        return this.potions.remove(indexPotion).getValeur();
    }

    public void setHP(int HP) {
        if (this.HP + HP > this.HPMax) this.HP = HPMax;
        else this.HP += HP;
        }

    public void setMP(int MP) {
        if (this.MP + MP > this.MPMax) this.MP = MPMax;
        else this.MP += MP;
    }

    /**
     * Permet l'utilisation d'une attaque magique.
     * @param d Destructible prenant les dégâts
     * @param dommage Dommages à infliger au Destructible
     */
    public void attaqueMagique(Destructible d, double dommage) {
        d.hit_me((dommage * this.LV));
        this.MP -= 30;
    }

    /**
     * Permet la gestion des dommages pris.
     * @param damage Dommage pris
     */
    public void hit_me(double damage) {
        if (this.HP - damage / this.DEF_RATIO < 0) this.HP = 0;
        else this.HP -= Math.round(damage / this.DEF_RATIO);
    }

    public String getNom() {
        return nom;
    }

    public String getClasse() {
        return classe;
    }

    public int getHP() {
        return HP;
    }

    public int getHPMax() {
        return HPMax;
    }

    public int getFOR() {
        return FOR;
    }

    public int getMP() {
        return MP;
    }

    public int getMPMax() {
        return MPMax;
    }

    public int getLV() {
        return LV;
    }

    public int getXP() {
        return XP;
    }

    public int getXPRequis() {
        return XPRequis;
    }

    public int getOr() {
        return or;
    }

    public double getDEF_RATIO() {
        return DEF_RATIO;
    }

    public ArrayList<Arme> getArmes() {
        return armes;
    }

    public ArrayList<Potion> getPotions() {
        return potions;
    }

    public Potion getPotion(int indexPotion) {
        return this.potions.get(indexPotion);
    }
}
