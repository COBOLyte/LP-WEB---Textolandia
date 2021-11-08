package RPG.Magasin;

import RPG.Armes.*;
import RPG.Joueur.Joueur;
import RPG.Potions.Potion;
import RPG.Potions.PotionMagie;
import RPG.Potions.PotionSoin;

import java.util.ArrayList;
import java.util.Objects;

public class Magasin {
    private final ArrayList<Arme> armes;
    private final ArrayList<Potion> potions;

    public Magasin() {
        this.armes = new ArrayList<>();
        this.potions = new ArrayList<>();
    }

    public ArrayList<Arme> getArmes() {
        return armes;
    }

    public ArrayList<Potion> getPotions() {
        return potions;
    }

    /**
     * Dans le jeu, les armes sont uniques et ne peuvent être achetées qu'une fois.
     * @param jPremiereArme Arme de départ du Joueur permettant de ne pas l'ajouter au Magasin, puisque déjà possédée
     */
    public void remplirStockArmes(Arme jPremiereArme) {
        Arme[] armesListe = {new Baton(50), new Dague(75), new Epee(100), new Hache(140), new Lance(60), new Marteau(125)};
        for (Arme arme : armesListe) {
            if (!Objects.equals(arme.getNom(), jPremiereArme.getNom())) this.armes.add(arme);
        }
    }

    /**
     * MAJ le prix des armes en fonction du niveau actuel du Joueur.
     * @param jLV Niveau actuel du Joueur
     */
    public void updatePrixArmes(int jLV) {
        for (Arme arme : this.armes) {
            arme.setPrix((arme.getPrixBase() * jLV) / 2);
        }
    }

    /**
     * Instancie l'ArrayList de potions, en fonction du niveau actuel du Joueur.
     * @param jLV Niveau actuel du Joueur
     */
    public void remplirStockPotions(int jLV) {
        this.potions.clear();
        for (int i = 0; i < 10; i++) {
            if (Math.random() >= 0.5) this.potions.add(new PotionSoin(30 * jLV, 10 * jLV));
            else this.potions.add(new PotionMagie(15 * jLV, 15 * jLV));
        }
    }

    /**
     * Permet la vente d'une arme au Joueur, s'il possède assez d'or.
     * @param indexArme Index de l'arme souhaitée par le Joueur dans l'ArrayList armes
     * @param j Joueur achetant l'arme
     */
    public void vendreArme(int indexArme, Joueur j) {
        if (j.getOr() < this.armes.get(indexArme).getPrix()) System.out.println("Or insuffisant.");
        else {
            System.out.println(this.armes.get(indexArme).getNom() + " ajouté(e) à l'inventaire.");
            j.useOr(this.armes.get(indexArme).getPrix());
            j.addArme(this.armes.remove(indexArme));
        }
    }

    /**
     * Permet la vente d'une potion au Joueur, s'il possède assez d'or.
     * @param indexPotion Index de la potion souhaitée par le Joueur dans l'ArrayList potions
     * @param j Joueur achetant la potion
     */
    public void vendrePotion(int indexPotion, Joueur j) {
        if (j.getOr() < this.potions.get(indexPotion).getPrix()) System.out.println("Or insuffisant.");
        else {
            System.out.println(this.potions.get(indexPotion).getNom() + " +" + this.potions.get(indexPotion).getValeur() + " ajouté(e) à l'inventaire.");
            j.useOr(this.potions.get(indexPotion).getPrix());
            j.addPotion(this.potions.remove(indexPotion));
        }
    }
}
