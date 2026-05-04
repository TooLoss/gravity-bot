package core;
import core.Map;
import core.joueurs.Joueur;
import core.obstacles.*;

public class Game extends JFrame implements MouseListener {

    private Map map;
    private Joueur joueur1;
    private Joueur joueur2;
    private int tour;

    private Obstacle selectedObstacle;

    private boolean estCoupFini;
    private boolean mapTermine;

    public Game() {
        addMouseListener(this);
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
        return null;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouse pos X : " + e.getX());
        System.out.println("mouse pos Y : " + e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}
