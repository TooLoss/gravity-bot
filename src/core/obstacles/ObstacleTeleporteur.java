package core.obstacles;

import core.Obstacle;
import core.Projectile;
import core.collisions.CollisionCirculaire;
import utils.Vecteur;

/**
 * Téléporteur : zone circulaire centrée sur pointA.
 * pointB définit le rayon (distance A→B).
 * Deux instances doivent être liées via {@link #lier(ObstacleTeleporteur)}.
 *
 * Quand le projectile entre dans le portail, il est téléporté au centre
 * du portail partenaire, décalé dans la direction de sa vitesse pour
 * éviter une re-téléportation immédiate.
 */
public class ObstacleTeleporteur extends ObstacleSegment {

    private ObstacleTeleporteur partenaire;

    /**
     * @param pointA Centre du portail.
     * @param pointB Définit le rayon : rayon = distance(A, B).
     */
    public ObstacleTeleporteur(Vecteur pointA, Vecteur pointB) {
        super(pointA, pointB);
        this.setCollision(new CollisionCirculaire(this.pointA, getLongueur()));
    }

    // ── Liaison ──────────────────────────────────────────────────────────────

    /**
     * Lie ce portail à {@code autre} (et réciproquement).
     * Appeler sur l'un des deux suffit.
     */
    public void lier(ObstacleTeleporteur autre) {
        if (autre == null) throw new IllegalArgumentException("Le partenaire ne peut pas être null.");
        if (autre == this) throw new IllegalArgumentException("Un portail ne peut pas se lier à lui-même.");
        this.partenaire = autre;
        if (autre.partenaire != this) autre.partenaire = this;
    }

    public boolean estLie()                    { return partenaire != null; }
    public ObstacleTeleporteur getPartenaire() { return partenaire; }

    // ── Téléportation ────────────────────────────────────────────────────────

    @Override
    public void eventAction(Projectile projectile) {
        if (partenaire == null) return;

        Vecteur sortie = new Vecteur(partenaire.pointA);

        Vecteur vitesse = new Vecteur(projectile.getVitesse());
        if (vitesse.getNorme() > 0) {
            vitesse.normaliser();
            vitesse.multiplier(partenaire.getLongueur() + 1.0);
            sortie.ajouter(vitesse);
        }

        projectile.setPosition(sortie);
    }

    @Override
    public void actualiser(double dt) { }

    /**
     * Retourne une copie NON liée. Appelez {@link #lier} pour reconstituer la paire.
     */
    @Override
    public Obstacle copier() {
        return new ObstacleTeleporteur(new Vecteur(this.pointA), new Vecteur(this.pointB));
    }
}