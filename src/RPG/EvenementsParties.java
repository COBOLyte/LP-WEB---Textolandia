package RPG;

import RPG.Armes.*;
import RPG.Destructibles.Destructible;
import RPG.Destructibles.Ennemi;
import RPG.Destructibles.Obstacle;
import RPG.Joueur.Joueur;
import RPG.Magasin.Magasin;
import RPG.Potions.Potion;

import java.util.Objects;
import java.util.Scanner;

public class EvenementsParties {
    private static final Scanner sc = new Scanner(System.in);
    private final Joueur j;
    private final Magasin m;

    public EvenementsParties(Joueur j, Magasin m) {
        this.j = j;
        this.m = m;
        this.m.remplirStockArmes(j.getArmeActuel());
    }

    /**
     * Affichage du menu principal du Joueur.
     * [1] Afficher les statiques du Joueur
     * [2] Afficher les armes du Joueur et Changer d'arme
     * [3] Afficher les potions du Joueur et Consommer une potion
     * [4] Retour au menu précédent
     */
    public void afficherMenu() {
        int menuChoix = 0;
        while (menuChoix != 4) {
            System.out.print("""
                [1] Profil
                [2] Armes
                [3] Potions
                [4] Retour
                """);
            menuChoix = sc.nextInt();

            switch (menuChoix) {
                case 1 -> this.afficherStats();
                case 2 -> this.afficherArmes();
                case 3 -> this.afficherPotions();
            }
        }
    }

    public void afficherStats() {
        System.out.println("Nom: " + j.getNom());
        System.out.println("Classe: " + j.getClasse());
        System.out.println("LV: " + j.getLV() + " (XP: " + j.getXP() + '/' + j.getXPRequis() + ')');
        System.out.println("HP: " + j.getHP() + "/"+ j.getHPMax());
        System.out.println("OR: " + j.getOr());
        System.out.println("FOR: " + j.getFOR());
        System.out.println("DEF: " + j.getDEF_RATIO());
        System.out.println("MP: " + j.getMP() + "/"+ j.getMPMax());
        System.out.println("Arme actuel: " + j.getArmes().get(0).getNom());
    }

    private void afficherArmes() {
        System.out.println("[ Vos armes ]");
        for (Arme a : j.getArmes()) {
            if (Objects.equals(j.getArmeActuel().getNom(), a.getNom())) {
                System.out.println("\u001B[31m>\u001B[0m " + j.getArmeActuel().toString());
                System.out.println(a.ascii_art());
            } else System.out.println("* " + a.toString() + " (" + (j.getArmes().indexOf(a) + 1) + ")");
        }

        int menuChoix = 0;
        while (menuChoix != 2) {
            System.out.print("""
                [1] Changer d'arme
                [2] Retour
                """);
            menuChoix = sc.nextInt();

            if (menuChoix == 1) {
                this.afficherChangementArme();
            }
        }
    }

    /**
     * Le joueur saisis le numéro de l'arme (index - 1 dans l'ArrayList armes du Joueur) pour s'en équiper.
     */
    private void afficherChangementArme() {
        int armeChoix = 0;
        while (armeChoix <= 0 || armeChoix > j.getArmes().size() + 1) {
            System.out.print("Entrer le numéro de l'arme dont vous voulez vous équiper ou " + (j.getArmes().size() + 1) + " pour sortir: ");
            armeChoix = sc.nextInt();

            if (armeChoix > 0 && armeChoix <= j.getArmes().size()) {
                if (Objects.equals(j.getArmes().get(armeChoix - 1).getNom(), j.getArmeActuel().getNom())) { // Vérifie si l'arme n'est pas déjà équipée
                    System.out.println("Arme déjà équipée !");
                    armeChoix = j.getArmes().size() + 1;
            } else {
                    j.setArmeActuel(j.getArmes().get(armeChoix - 1));
                    System.out.println(j.getArmeActuel().ascii_art());
                    System.out.println("\u001B[31m" + j.getArmeActuel().getNom() + "\u001B[0m équipé(e).");
                }
            }
        }
    }

    private void afficherPotions() {
        System.out.println("[ Vos potions ]");
        int menuChoix = 0;
        while (menuChoix != 2) {
            for (Potion p : j.getPotions()) {
                System.out.println(p.toString() + " (" + (j.getPotions().indexOf(p) + 1) + ") ");
            }

            System.out.print("""
            [1] Consommer une potion
            [2] Retour
            """);
            menuChoix = sc.nextInt();

            if (menuChoix == 1) {
                this.afficherConsommerPotion();
            }
        }
    }

    /**
     * Le joueur saisit le numéro de la potion (index - 1 dans l'ArrayList potions du Joueur) pour la consommer.
     */
    private void afficherConsommerPotion() {
        System.out.println("HP actuel: " + j.getHP() + "/"+ j.getHPMax());
        System.out.println("MP actuel: " + j.getMP() + "/"+ j.getMPMax());

        int potionChoix = 0;
        while (potionChoix <= 0 || potionChoix > j.getPotions().size() + 1) {
            System.out.print("Entrer le numéro de la potion que vous souhaitez utiliser ou " + (j.getPotions().size() + 1) + " pour sortir: ");
            potionChoix = sc.nextInt();

            if (potionChoix > 0 && potionChoix <= j.getPotions().size()) {

                if (j.getPotion(potionChoix - 1).getClass().getSimpleName().equals("PotionSoin")) {
                    if (j.getHP() == j.getHPMax()) {  // Vérifie si les HP ne sont pas déjà pleines
                        System.out.println("Santé déjà pleine.");

                        potionChoix = j.getPotions().size() + 2;
                    } else {
                        System.out.println("Vous utilisez " + j.getPotion(potionChoix - 1).toString() + ".");
                        System.out.print("HP: " + j.getHP() + "/"+ j.getHPMax() + " >> ");
                        j.setHP(j.usePotion(potionChoix - 1));
                        System.out.println("HP: \u001B[32m" + j.getHP() + "\u001B[0m/"+ j.getHPMax());
                    }
                } else {
                    if (j.getMP() == j.getMPMax()) { // Vérifie si les MP ne sont pas déjà pleines
                        System.out.println("Magie déjà pleine.");

                        potionChoix = j.getPotions().size() + 2;
                    } else {
                        System.out.println("Vous utilisez " + j.getPotion(potionChoix - 1).toString() + ".");
                        System.out.print("MP: " + j.getMP() + "/"+ j.getMPMax() + " >> ");
                        j.setMP(j.usePotion(potionChoix - 1));
                        System.out.println("MP: \u001B[36m" + j.getMP() + "\u001B[0m/"+ j.getMPMax());
                    }
                }

            }

        }
    }

    /**
     * Affichage du menu du Magasin
     */
    public void afficherMagasin() {
        m.updatePrixArmes(j.getLV());
        m.remplirStockPotions(j.getLV());

        int magasinChoix = 0;
        System.out.print("""
                \u001B[35mBienvenue dans mon magasin, voyageur !
                Que puis-je pour vous ?\u001B[0m
                """);
        while (magasinChoix != 3) {
            System.out.print("""
                [1] Acheter arme
                [2] Acheter potion
                [3] Sortir
                """);
            magasinChoix = sc.nextInt();

            switch (magasinChoix) {
                case 1 -> this.afficherAchatArme();
                case 2 -> this.afficherAchatPotion();
                case 3 -> System.out.println("Au plaisir de vous revoir !\n");
            }
        }
    }

    /**
     * Le joueur saisis le numéro de l'arme souhaitée (index - 1 dans l'ArrayList armes du Magasin) pour l'acheter.
     */
    private void afficherAchatArme() {
        for (Arme a : m.getArmes()) {
            System.out.println(a.toString() + ", " + a.getPrix() + " or (" + (m.getArmes().indexOf(a) + 1) + ") ");
        }
        System.out.println("Votre or: " + j.getOr());

        int armeChoix = 0;
        while (armeChoix <= 0 || armeChoix > m.getArmes().size() + 1) {
            System.out.print("Entrer le numéro de l'arme que vous souhaitez acheter ou " + (m.getArmes().size() + 1) + " pour sortir: ");
            armeChoix = sc.nextInt();

            if (armeChoix > 0 && armeChoix <= m.getArmes().size()) {
                m.vendreArme(armeChoix - 1, j);
            }
        }
    }

    /**
     * Le joueur saisis le numéro de la potion souhaitée (index - 1 dans l'ArrayList potions du Magasin) pour l'acheter.
     */
    private void afficherAchatPotion() {
        for (Potion p : m.getPotions()) {
            System.out.println(p.toString() + ", " + p.getPrix() + " or (" + (m.getPotions().indexOf(p) + 1) + ") ");
        }
        System.out.println("Votre or: " + j.getOr());

        int potionChoix = 0;
        while (potionChoix <= 0 || potionChoix > m.getPotions().size() + 1) {
            System.out.print("Entrer le numéro de la potion que vous souhaitez acheter ou " + (m.getPotions().size() + 1) + " pour sortir: ");
            potionChoix = sc.nextInt();

            if (potionChoix > 0 && potionChoix <= m.getPotions().size()) {
                m.vendrePotion(potionChoix - 1, j);
            }
        }
    }

    /**
     * Gestion du système de Combat
     */
    public void startCombat() {
        Destructible d;
        String destructibleNom;
        if (Math.random() <= 0.5) { // Génération aléatoire d'un Monstre ou d'un Obstacle
            System.out.println("\u001B[31mVous rencontrez un monstre !\u001B[0m");
            d = new Ennemi((80 * j.getLV()) / 2, j.getLV());
            destructibleNom = "Monstre";
        } else {
            System.out.println("\u001B[35mVous tombez sur un obstacle sur le chemin.\u001B[0m");
            d = new Obstacle((80 * j.getLV()) / 2);
            destructibleNom = "Obstacle";
        }


        int combatChoix;
        // Un combat se termine si les HP d'une des deux parties tombent à 0
        while (d.getHP() > 0 && j.getHP() > 0) {
            // Affichage du menu de combat du Joueur
            System.out.println("{ " + j.getNom() + ", HP: " + j.getHP() + "/" + j.getHPMax() + " MP: " + j.getMP() + "/" + j.getMPMax() + " }");
            System.out.println("{ " + destructibleNom + ", HP: " + d.getHP() + "/" + d.getHPMax() + " }");
            System.out.print("""
                [1] Attaquer
                [2] Magie (30 MP)
                [3] Changer d'arme
                [4] Utiliser une potion
                [5] Fuir
                """);
            combatChoix = sc.nextInt();

            if (combatChoix == 1) {
                System.out.println("Vous utilisez votre " + j.getArmeActuel().getNom() + " pour attaquer.");
                double dommage = (Math.random() * 10) + j.getFOR();
                j.getArmeActuel().attaquer(d, dommage);
            } else if (combatChoix == 2) {
                if (j.getMP() >= 30) {
                    System.out.println("Vous lancez votre attaque magique !");
                    double dommage = (Math.random() * 100) + 30;
                    j.attaqueMagique(d, dommage);
                } else {
                    System.out.println("Vous n'avez pas assez de magie. Impossible de lancer l'attaque !");
                }
            } else if (combatChoix == 3) {
                for (Arme a : j.getArmes()) {
                    if (Objects.equals(j.getArmeActuel().getNom(), a.getNom())) {
                        System.out.println("\u001B[31m>\u001B[0m " + j.getArmeActuel().toString());
                        System.out.println(a.ascii_art());
                    } else System.out.println("* " + a.toString() + " (" + (j.getArmes().indexOf(a) + 1) + ")");
                }
                this.afficherChangementArme();
            } else if (combatChoix == 4) {
                for (Potion p : j.getPotions()) {
                    System.out.println(p.toString() + " (" + (j.getPotions().indexOf(p) + 1) + ") ");
                }
                this.afficherConsommerPotion();
            } else if (combatChoix == 5) {
                if (d.getClass().getSimpleName().equals("Ennemi")) { // Si le combat se déroule face à un Obstacle, il n'y a pas de raison que la fuite échoue...
                    if ((int)(Math.random() * 4) > 1) { // Possibilité d'échouer la fuite
                        System.out.println("Vous prenez la fuite...");
                        break;
                    } else {
                        System.out.println("Vous ne parvenez pas à fuir !");
                    }
                } else break;
            }

            if (d.getHP() <= 0) {
                System.out.println("VICTOIRE !");
                int orGagnee = (int)((Math.random() * 5) + 10) * j.getLV();
                int xpGagnee = (int)((Math.random() * 70) + 20) * j.getLV();
                System.out.println("Vous gagnez \u001B[33m" + orGagnee + " or\u001B[0m et \u001B[35m" + xpGagnee + " XP\u001B[0m.");
                j.addOr(orGagnee);
                j.addXp(xpGagnee);
                break;
            }

            // Action de l'Énnemi attaquant le Joueur
            if (d.getClass().getSimpleName().equals("Ennemi")) {
                System.out.println("Le monstre vous attaque !");
                double dommage = (Math.random() * 25) * j.getLV();
                ((Ennemi) d).attack(j, dommage);
            }
        }
    }

    public void afficherGameOver() {
        System.out.println("""
     _.--""--._
    /  _    _  \\
 _  ( (_\\  /_) )  _
{ \\._\\   /\\   /_./ }
/_"=-.}______{.-="_\\
 _  _.=(""\"")=._  _
(_'"_.-"`~~`"-._"'_)
 {_"            "_}
 [ GAME OVER ]
 """);
    }
}
