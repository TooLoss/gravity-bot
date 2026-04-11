package core;
import utils.Vecteur;


/** Classe Map contient référence aux obstacles et projectiles. */
public class Map {

    public static final Vecteur SCREEN_SIZE = new Vecteur(500.f);
    public static final double GAME_TICK = 1000000000L / 60; // 60 TPS

    private boolean estDansEcran(Acteur acteur) {
        boolean estX = acteur.getPosition().getX() >= 0 && acteur.getPosition().getX() <= SCREEN_SIZE.getX();
        boolean estY = acteur.getPosition().getY() >= 0 && acteur.getPosition().getY() <= SCREEN_SIZE.getY();
        EEN_SIZE.getY();
        return estX && estY;
    }

    private Projectile projectile;
    private Obstacle[] obstacles;

    /** Méthode appelé chaque frame correspondant à la game loop.
     * @return boolean True si le projectile atteind l'objectif.
     */
    public boolean lancerSimulation() {
        long previous = System.nanoTime();
        long accumulator = 0;

        while (estDansEcran(projectile)) {
            long current = System.nanoTime();
            long elapsed = current - previous;
            previous = current;
            accumulator += elapsed;

            while (accumulator >= GAME_TICK) {
                testerCollisions();
                projectile.actualiser(GAME_TICK);
                accumulator -= GAME_TICK;
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
