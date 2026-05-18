package core.obstacles;

import core.obstacles.Obstacle;
import utils.Vecteur;

/**
 * Obstacle défini par deux points A (origine) et B (extrémité).
 * La position de l'obstacle au sens de {@link core.Acteur} est toujours pointA.
 *
 * Fournit des méthodes utilitaires (directeur, longueur) que les sous-classes
 * utilisent pour construire leur zone de collision.
 *
 * Sous-classes : ObstacleMur, ObstaclePropulseur, ObstacleTeleporteur.
 */
public abstract class ObstacleSegment extends Obstacle {

    protected final Vecteur pointA;
    protected final Vecteur pointB;

    /**
     * @param pointA Origine du segment (= position de l'obstacle).
     * @param pointB Extrémité du segment.
     */
    public ObstacleSegment(Vecteur pointA, Vecteur pointB) {
        super(pointA);
        this.pointA = new Vecteur(pointA);
        this.pointB = new Vecteur(pointB);
    }

    // ── Helpers partagés ────────────────────────────────────────────────────

    /**
     * Vecteur directeur A→B (non normalisé, sa norme est la longueur du segment).
     * Utilisé par les sous-classes pour construire une CollisionSegment.
     */
    protected Vecteur getDirecteur() {
        return new Vecteur(pointA, pointB); // = B - A
    }

    /** Longueur du segment (distance entre A et B). */
    protected double getLongueur() {
        return pointA.distance(pointB);
    }

    public Vecteur getPointA() { return new Vecteur(pointA); }
    public Vecteur getPointB() { return new Vecteur(pointB); }
}