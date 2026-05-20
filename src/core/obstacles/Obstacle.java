package core.obstacles;
import utils.Vecteur;
import core.*;

/** Classe abstraite de l'obstacle.
 * L'obstacle véfifie à chaque instant si le projectile vérifie les conditions
 * pour interragir avec lui (estDedans).
 */
public abstract class Obstacle extends Acteur {

    private Collision collision;
    public static final int prix = 0;
    protected Vecteur pointFinal;

    public Obstacle() {
        super();
    }

    public Obstacle(Vecteur position) {
        super(position);
    }

    /** L'action à réaliser quand le projectile entre dans l'obstacle.
     * @param projectile Projectile qui interragit avec l'obstacle.
     */
    public abstract void eventAction(Projectile projectile);

    // TODO peut être le mettre dans Acteur car on a besoin de les copier ?
    public abstract Obstacle copier();

    /** Vérification des conditions de l'acteur pour intéragit avec l'obstacle.
     * @param acteur Acteur dont on vérifie si il collisionne.
     * @return boolean true si l'acteur est dans l'obstacle.
     */
    public boolean estDansObstacle(Acteur acteur) {
        // Acteur acteur, Acteur source
        return collision.estEnCollision(acteur);
    }

    /**
     * Définit ou modifie la forme de collision de l'obstacle.
     * @param collision L'implémentation de la zone de collision.
     */
    public void setCollision(Collision collision) {
        this.collision = collision;
    }

    /**
     * Récupère le prix de l'obstacle pour les mécaniques d'achat du jeu.
     * @return La valeur entière du prix.
     */
    public int getPrix() {
        return this.prix;
    }

    public void setPointFinal(Vecteur point) {
        this.pointFinal = new Vecteur(point);
    }

    public Vecteur getPointFinal() {
        return this.pointFinal;
    }
}
