package core;
import core.Map;
import core.joueurs.Joueur;
import core.obstacles.*;

public class Game {

    private Map map;
    private Joueur joueur1;
    private Joueur joueur2;
    private int tour;

    private Obstacle selectedObstacle;

    private boolean estCoupFini;
    private boolean mapTermine;

    public Game() {
        this.estCoupFini = false;
        this.mapTermine = false;
        this.tour = 0;
    }

    public void lancerJeu() {
        while (!this.mapTermine) {
            tour++;
            faireTour();
        }
        // TODO changer la map et reset les joueurs
    }

    public void faireTour() {
        while (!estCoupFini) {
             
        }
        if (map.lancerSimulation()) {
            // Gagné
            this.mapTermine = true;
        } else {
            // Perdu
        }
        this.estCoupFini = false;
    }

    public Joueur getJoueurCourant() {
        return 
    }

}
