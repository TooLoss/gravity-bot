package core;

import core.joueurs.Joueur;
import java.util.ArrayList;
import core.ChargeurMap;

/** Classe game qui gère la game loop. */
public class Game {
    /** Map utilisé pour la partie. */
    private Map map;
    /** Joueur 1. */
    private Joueur joueur1;
    /** Joueur 2. */
    private Joueur joueur2;
    /** Joueur qui joue le tour actuel. */
    private Joueur joueurCourant;
    /** Numéro de tour de la partie. */
    private int tour;
    /** Chargeur de map. */
    private ChargeurMap chargeur;

    /** Constructeur de la partie, nécéssite 2 joueurs.
     * @param j1 Joueur 1.
     * @param j2 Joueur 2.
     */
    public Game(Joueur j1, Joueur j2) {
        this(j1, j2, "./src/core/maps/map1.txt");
    }

    /**
     * Charge une map depuis un fichier .txt.
     * @param j1 Joueur 1.
     * @param j2 Joueur 2.
     * @param cheminMap Chemin vers le fichier de map (ex: "src/core/maps/map2.txt").
     */
    public Game(Joueur j1, Joueur j2, String cheminMap) {
        this.joueur1 = j1;
        this.joueur2 = j2;
        this.joueurCourant = j1;
        this.tour = 1;
        this.map = new Map();
        this.chargeur = new ChargeurMap();
        ChargeurMap.chargerDepuisFichier(cheminMap, this.map);
    }

    /**
     * Utilise une Map déjà configurée (ex : depuis l'éditeur de niveaux).
     * L'audio doit avoir été injecté sur la map avant l'appel.
     * @param j1 Joueur 1.
     * @param j2 Joueur 2.
     * @param mapPreChargee Map configurée avec obstacles, position et cible.
     */
    public Game(Joueur j1, Joueur j2, Map mapPreChargee) {
        this.joueur1 = j1;
        this.joueur2 = j2;
        this.joueurCourant = j1;
        this.tour = 1;
        this.map = mapPreChargee;
        this.chargeur = new ChargeurMap();
    }

    /** Executer le tour actuelle, enregistre si la simulation est gagnante. */
    public void executerTour() {
        boolean aGagne = map.lancerSimulation();

        if (!aGagne) {
            passerTour();
        } else {
            /* TODO faire gagner */
            System.out.println("Gagné");
        }
    }

    /** Passer le tour si la partie n'est pas gagnante. */
    private void passerTour() {
        tour++;
        joueurCourant = (tour % 2 != 0) ? joueur1 : joueur2;
        joueurCourant.ajouterBudget(map.getBudgetParTour());
    }

    /** Getter à gagné.
     * @return boolean true si gagné.
     */
    public boolean estGagne() { return map.estGagne(); }

    /** Getter de map.
     * @return Map map actuelle.
     */
    public Map getMap() { return map; }

    /** Getter du joueur courant.
     * @return Joueur joueur courant.
     */
    public Joueur getJoueurCourant() { return joueurCourant; }

    /** Getter du tour courant.
     * @return int tour.
     */
    public int getTour() { return tour; }
}
