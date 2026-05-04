package core;
import utils.Vecteur;
import core.obstacles.*;
import java.util.HashSet;


/** Classe Map contient référence aux obstacles et projectiles. */
public class Map {

    public static final Vecteur SCREEN_SIZE = new Vecteur(500.f);
    public static final double GAME_TICK = 1000000000L / 60; // 60 TPS

    private boolean estDansEcran(Acteur acteur) {
        boolean estX = acteur.getPosition().getX() >= 0 && acteur.getPosition().getX() <= SCREEN_SIZE.getX();
        boolean estY = acteur.getPosition().getY() >= 0 && acteur.getPosition().getY() <= SCREEN_SIZE.getY();
        return estX && estY;
    }

    // NOTE n'est pas statique car doit être initialisé depuis un fichier save map
    private Projectile projectile;
    private HashSet<Obstacle> obstacles;
    // TODO public que pour les tests
    public Vecteur position_initiale;
    // TODO public que pour les tests
    public Vecteur vitesse_initiale;
    private Vecteur cible_position;
    private ObstacleCible cible;

    private boolean estSimulation;
    private boolean estGagne;

    public Map() {
        this.estSimulation = false;
        this.estGagne = false;
    }

    public void initialiserParametres() {
        this.projectile = new Projectile(position_initiale, vitesse_initiale);
        this.cible = new ObstacleCible(cible_position);
    }

    public void ajouterObstacle(Obstacle obstacle) {
        // TODO danger, doit enregistrer une copie
        if (!estSimulation) {
            obstacles.add(obstacle);
        }
    }

    /** Méthode appelé chaque frame correspondant à la game loop.
     * @return boolean True si le projectile atteind l'objectif.
     */
    public boolean lancerSimulation() {
        this.estSimulation = true;
        long previous = System.nanoTime();
        long accumulator = 0;

        initialiserParametres();

        while (estDansEcran(projectile) && !estGagne) {
            long current = System.nanoTime();
            long elapsed = current - previous;
            previous = current;
            accumulator += elapsed;

            while (accumulator >= GAME_TICK) {
                testerCollisions();
                projectile.actualiser(GAME_TICK*10e-9);
                accumulator -= GAME_TICK;
                Vecteur sat_pos = projectile.getPosition();
                Vecteur sat_speed = projectile.getVitesse();
                System.out.println("Satellite position : " + sat_speed.getX() + " - " + sat_speed.getY());
                System.out.println("Satellite position : " + sat_pos.getX() + " - " + sat_pos.getY());
            }
            // TODO Faire l'affichage Swing
        }
        return estGagne;
    }

    public void testerCollisions() {
        if (cible.estDansObstacle(this.projectile)) {
            gagne();
        }
        for (Obstacle obs : obstacles) {
            if (obs.estDansObstacle(this.projectile)) {
                obs.eventAction(this.projectile);
            }
        }
    }

    public void gagne() {
        this.estGagne = true;
    }

}
