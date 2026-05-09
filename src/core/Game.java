package core;

import core.joueurs.Joueur;
import java.util.ArrayList;

public class Game {
    private Map map;
    private Joueur joueur1;
    private Joueur joueur2;
    private Joueur joueurCourant;
    private int tour;

    public Game(Joueur j1, Joueur j2) {
        this.joueur1 = j1;
        this.joueur2 = j2;
        this.joueurCourant = j1;
        this.tour = 1;
        Audio.GestionnaireAudio audio = new Audio.GestionnaireAudio();
        this.map = new Map();
        map.setGestionnaireAudio(audio);
    }

    public void executerTour(Runnable callback) {
        map.lancerSimulation(() -> {
            if (!map.estGagne()) {
                passerTour();
            }
            if (callback != null) callback.run();
        });
    }

    private void passerTour() {
        tour++;
        joueurCourant = (tour % 2 != 0) ? joueur1 : joueur2;
    }

    public Map getMap() { return map; }
    public Joueur getJoueurCourant() { return joueurCourant; }
    public int getTour() { return tour; }
}
