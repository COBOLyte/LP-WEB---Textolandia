package RPG;

import RPG.Carte.Carte;
import RPG.Joueur.Joueur;
import RPG.Magasin.Magasin;

import java.util.Objects;
import java.util.Scanner;

public class Jeu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Joueur joueur = null;
        EvenementsParties events = null;

        // Début - Introduction
        System.out.println("""
                        Bonjour aventurier et bienvenue dans la contrée de Textolandia, dans lequel vous attend maintes richesses et merveilles !
                        Mais attention à ne pas tomber nez à nez face à un des nombreux monstres peuplant la contrée.
                        Vous pourriez y laisser quelques cheveux, si vous n'êtes pas convenablement équipé...
                        Vous ne connaissez pas les détails qui vous ont poussés à venir ici.
                        Toutefois, une pensée résonne dans votre tête, depuis votre arrivée :
                        \u001B[33mAtteindre l'Extrémité Nord-Est.\u001B[0m
                        """);

        System.out.println("Avant de vous laissez partir, j'aurais 2 petites questions...");
        String confirmPerso = "NON";
        while (!confirmPerso.equals("OUI")) {
            System.out.print("Comment vous nommez-vous ? ");
            String nom = sc.next(); // Définition du nom du joueur

            int classeChoix = 0;
                System.out.print("""
                [1] Guerrier (HP: 180, FOR: 40, DEF: 1.8, MP: 0)
                        Première arme: Épée (Dommage Ennemi: 1.3, Dommage Obstacle: 0.8)
                                        
                [2] Mage (HP: 150, FOR: 12, DEF: 0.5, MP: 100)
                        Première arme: Baton (Dommage Ennemi: 1.0, Dommage Obstacle: 0.2)
                                    
                [3] Voleur (HP: 130, FOR: 20, DEF: 0.8, MP: 0)
                        Première arme: Dague (Dommage Ennemi: 1.1, Dommage Obstacle: 0.7)
                                        
                [4] Barbare (HP: 200, FOR: 30, DEF: 1.3, MP: 0)
                        Première arme: Hache (Dommage Ennemi: 0.8, Dommage Obstacle: 1.2)
                                        
                [5] Forgeron (HP: 160, FOR: 25, DEF_RATIO: 0.9, MP: 0)
                        Première arme: Marteau (Dommage Ennemi : 0.7, Dommage Obstacle : 1.5)
                """);
            while (classeChoix <= 0 || classeChoix > 5) {
                System.out.print("À quelle classe aspirez-vous ? ");
                classeChoix = sc.nextInt(); // Définition de la classe du Joueur
            }

            joueur = new Joueur(nom, classeChoix);
            events = new EvenementsParties(joueur, new Magasin());
            System.out.println("\nDonc, vous êtes...");
            events.afficherStats();
            System.out.print("Vous confirmez ? [OUI ou N'IMPORTE QUELLE TOUCHE pour recommencer] "); // Confirmation des informations du Joueur, sinon relance l'étape de "Création du personnage"
            confirmPerso = sc.next().toUpperCase();
        }

        System.out.println("\nJe ne vais pas vous retenir plus longtemps, " + joueur.getClasse() + " " + joueur.getNom() + ".\nQue la chance vous sourit en ces terres !\n");
        Carte carte = new Carte();
        // Fin - Introduction

        String joueurChoix = "Z";
        while (!joueurChoix.equals("2") || carte.isInputInvalid(joueurChoix)) {
            carte.displayCarte();

            if (carte.hasJoueurArrivedAtGoal()) { // Si le Joueur atteint l'Objectif final
                System.out.println("""
                Après plusieurs jours de marche, vous finissez enfin par atteindre votre objectif.
                Éreinté(e), vous vous asseyez au pied d'un arbre et vous remémorez votre voyage à Textolandia...
                """);
                System.out.println("[ SCORE FINAL ]");
                System.out.println("Jours passés: " + carte.getNbJour());
                System.out.println("LV: " + joueur.getLV());
                System.out.println("HP: " + joueur.getHP() + "/"+ joueur.getHPMax());
                System.out.println("MP: " + joueur.getMP() + "/"+ joueur.getMPMax());
                System.out.println("OR: " + joueur.getOr());
                System.out.println("Nombre d'armes possédées: " + joueur.getArmes().size());
                break;
            } else if (Objects.equals(carte.getCurrentjoueurElement(), "M")) { // Si le Joueur atteint le Magasin
                events.afficherMagasin();
                carte.displayCarte();
            } else { // Génération aléatoire d'un combat
                if ((int)(Math.random() * 3) == 1) {
                    events.startCombat();

                    if (joueur.getHP() == 0) {
                        events.afficherGameOver();
                        break;
                    } else carte.displayCarte();
                }
            }

            System.out.print("""
            [Z] Haut, [S] Bas, [Q] Gauche, [D] Droite
            [1] Menu
            [2] Quitter
            """);
            joueurChoix = sc.next();

            if ("1".equals(joueurChoix)) events.afficherMenu();
            else if (!"2".equals(joueurChoix) && !carte.isInputInvalid(joueurChoix)) carte.deplacerJoueur(joueurChoix);
        }
    }
}
