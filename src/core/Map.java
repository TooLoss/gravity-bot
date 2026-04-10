package core;

/** Classe Map contient référence aux obstacles et projectiles. */
public class Map {

    public static final Vecteur SCREEN_SIZE = new Vecteur(500.f);
    public static final double GAME_TICK = 1.f;

    private boolean estDansEcran(Acteur acteur) {
        boolean estX = acteur.getX() - SCREEN_SIZE.getX() > 0;
        boolean estY = acteur.getY() - SCREEN_SIZE.getY() > 0;
        return estX && estY;
    }
    
    private Projectile projectile;
    private Obstacles[] obstacles;

    /** Méthode appelé chaque frame correspondant à la game loop.
     * @return boolean True si le projectile atteind l'objectif.
     */
    public boolean lancerSimulation() {
        long previous_time = System.nanoTime();
        long frame_time = 0;
        while (estDansEcran(projectile) {
            long time = System.nanoTime();
            long delta_time = time - previous_time;
            previous_time = time;

            frame_time += delta_time;

            while (frame_time >= GAME_TICK) {
                // TODO Update here with GameTick
            }
        }

        // Le projectile n'a pas atteind l'objectif
        return false;
    }

}
