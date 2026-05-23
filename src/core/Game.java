package core;

import core.joueurs.Joueur;
import java.util.ArrayList;
import core.ChargeurMap;

public class Game {
    private Map map;
    private Joueur joueur1;
    private Joueur joueur2;
    private Joueur joueurCourant;
    private int tour;
    private ChargeurMap chargeur;

     /** Charge la map par défaut (map1.txt). */
    public Game(Joueur j1, Joueur j2) {
        this(j1, j2, "./src/core/maps/map1.txt");
    }
 
 
    /**
     * Charge une map depuis un fichier .txt.
     * @param cheminMap Chemin vers le fichier de map (ex: "src/core/maps/map2.txt").
     */
    public Game(Joueur j1, Joueur j2, String cheminMap) {
        this.joueur1       = j1;
        this.joueur2       = j2;
        this.joueurCourant = j1;
        this.tour          = 1;
        this.map     = new Map();
        this.chargeur = new ChargeurMap();
        ChargeurMap.chargerDepuisFichier(cheminMap, this.map);
    }
 
 
    /**
     * Utilise une Map déjà configurée (ex : depuis l'éditeur de niveaux).
     * L'audio doit avoir été injecté sur la map avant l'appel.
     * @param mapPreChargee Map configurée avec obstacles, position et cible.
     */
    public Game(Joueur j1, Joueur j2, Map mapPreChargee) {
        this.joueur1       = j1;
        this.joueur2       = j2;
        this.joueurCourant = j1;
        this.tour          = 1;
        this.map           = mapPreChargee;
        this.chargeur      = new ChargeurMap();
    }

    public void executerTour() {
        boolean aGagne = map.lancerSimulation();
        
        if (!aGagne) {
            passerTour();
        } else {
            /* TODO faire gagner */
            System.out.println("Gagné");
        }
    }

    private void passerTour() {
        tour++;
        joueurCourant = (tour % 2 != 0) ? joueur1 : joueur2;
        joueurCourant.ajouterBudget(map.getBudgetParTour());
    }
    public boolean estGagne() {return map.estGagne();}
    public Map getMap() { return map; }
    public Joueur getJoueurCourant() { return joueurCourant; }
    public int getTour() { return tour; }
}
