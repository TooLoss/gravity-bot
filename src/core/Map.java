package core;
import utils.Vecteur;
import core.obstacles.ObstacleAimantPull;


/** Classe Map contient référence aux obstacles et projectiles. */
public class Map {

    public static final Vecteur SCREEN_SIZE = new Vecteur(500.f);
    public static final double GAME_TICK = 1000000000L / 60; // 60 TPS

    private boolean estDansEcran(Acteur acteur) {
        boolean estX = acteur.getPosition().getX() >= 0 && acteur.getPosition().getX() <= SCREEN_SIZE.getX();
        boolean estY = acteur.getPosition().getY() >= 0 && acteur.getPosition().getY() <= SCREEN_SIZE.getY();
        return estX && estY;
    }

    private Projectile projectile;
    private Obstacle[] obstacles = { new ObstacleAimantPull(new Vecteur(200.f, 20.f)) };
    // TODO public que pour les tests
    public Vecteur position_initiale;
    // TODO public que pour les tests
    public Vecteur vitesse_initiale;

    public void initialiserParametres() {
        this.projectile = new Projectile(position_initiale, vitesse_initiale);
    }

    /** Méthode appelé chaque frame correspondant à la game loop.
     * @return boolean True si le projectile atteind l'objectif.
     */
    public boolean lancerSimulation() {
        long previous = System.nanoTime();
        long accumulator = 0;

        initialiserParametres();

        while (estDansEcran(projectile)) {
            long current = System.nanoTime();
            long elapsed = current - previous;
            previous = current;
            accumulator += elapsed;

            while (accumulator >= GAME_TICK) {
                testerCollisions();
                projectile.actualiser(GAME_TICK*10e-9);
                accumulator -= GAME_TICK;
                Vecteur sat_pos = projectile.getPosition();
                System.out.println("Satellite position : " + sat_pos.getX() + " - " + sat_pos.getY());
            }
            // TODO Faire l'affichage Swing
        }
        return false;
    }

    public void testerCollisions() {
        for (Obstacle obs : obstacles) {
            if (obs.estDansObstacle(this.projectile)) {
                obs.eventAction(this.projectile);
            }
        }
    }

}
