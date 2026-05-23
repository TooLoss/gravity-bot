package core.obstacles;
import utils.Vecteur;
import core.*;

/** Classe abstraite de l'obstacle.
 * L'obstacle véfifie à chaque instant si le projectile vérifie les conditions
 * pour interragir avec lui (estDedans).
 */
public abstract class Obstacle extends Acteur {

    /** Collision utilisé par l'obstacle. */
    private Collision collision;
    public static final int prix = 0;
    /** Point final de l'obstacle pour l'orientation. */
    protected Vecteur pointFinal;

    /** Constructeur d'obstacle par défaut. */
    public Obstacle() {
        super();
    }

    /** Constructeur d'obstacle par position.
     * @param position Vecteur.
     */
    public Obstacle(Vecteur position) {
        super(position);
    }

    /** L'action à réaliser quand le projectile entre dans l'obstacle.
     * @param projectile Projectile qui interragit avec l'obstacle.
     */
    public abstract void eventAction(Projectile projectile);

    /** Méthode abstraite pour copier l'Obstacle.
     * @return Obstacle retourné par la position.
     */
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

    /** Setter position finale de l'obstacle.
     * @param point Vecteur finale.
     */
    public void setPointFinal(Vecteur point) {
        this.pointFinal = new Vecteur(point);
    }

    /** Getter point final de l'obstacle.
     * @return Vecteur point final.
     */
    public Vecteur getPointFinal() {
        return this.pointFinal;
    }
}
