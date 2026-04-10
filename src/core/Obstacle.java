package core;

/** Classe abstraite de l'obstacle.
 * L'obstacle véfifie à chaque instant si le projectile vérifie les conditions
 * pour interragir avec lui (estDedans).
 */
public abstract class Obstacle extends Acteur {
    
    private static final Collision collision;

    /** L'action à réaliser quand le projectile entre dans l'obstacle.
     * @param projectile Projectile qui interragit avec l'obstacle.
     */
    public abstract void eventAction(Projectile projectile);

    /** Vérification des conditions de l'acteur pour intéragit avec l'obstacle.
     * @param acteur Acteur dont on vérifie si il collisionne.
     * @return boolean true si l'acteur est dans l'obstacle.
     */
    public boolean estDansObstacle(Acteur acteur) {
        // Acteur acteur, Acteur source
        return collision.estEnCollision(acteur, this);
    }
    // TODO conseil peut être faire une interface VerificationObstacle et
    // implémenter des types de collisions (rectangle, spherique, ...).
    
}
