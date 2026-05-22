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

    public Game(Joueur j1, Joueur j2) {
        this.joueur1 = j1;
        this.joueur2 = j2;
        this.joueurCourant = j1;
        this.tour = 1;
        Audio.GestionnaireAudio audio = new Audio.GestionnaireAudio();
        this.map = new Map();
        this.chargeur = new ChargeurMap();
        chargeur.chargerDepuisFichier("./src/core/maps/map1.txt", this.map);
        map.setGestionnaireAudio(audio);
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
        joueur.ajouterBudget();
    }

    public Map getMap() { return map; }
    public Joueur getJoueurCourant() { return joueurCourant; }
    public int getTour() { return tour; }
}
