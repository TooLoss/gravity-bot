package core.collisions;
<<<<<<< HEAD
import core.Acteur;
import core.Collision;

import utils.Vecteur;
import core.Acteur;
import core.Collision;
import core.Obstacle;


public class CollisionRectangle implements Collision {

    // Les deux points qui définissent le rectangle de collision
    private Vecteur pointA;
    private Vecteur pointB;

    /**
     * Constructeur
     * @param a Le premier coin du rectangle
     * @param b Le coin opposé du rectangle
     */
    public CollisionRectangle(Vecteur a, Vecteur b) {
        this.pointA = a;
        this.pointB = b;
    }

    @Override
    public boolean estEnCollision(Acteur acteur) {
        // On récupère la position du projectile/satellite
        Vecteur positionActeur = acteur.getPosition();

        if (positionActeur == null) {
            return false; // Sécurité si l'acteur n'a pas de position
        }

        double x = positionActeur.getX();
        double y = positionActeur.getY();

        // On trouve les limites minimales et maximales en X
        double minX = Math.min(pointA.getX(), pointB.getX());
        double maxX = Math.max(pointA.getX(), pointB.getX());

        // On trouve les limites minimales et maximales en Y
        double minY = Math.min(pointA.getY(), pointB.getY());
        double maxY = Math.max(pointA.getY(), pointB.getY());

        // On vérifie si la position X et Y de l'acteur est DANS ces limites
        boolean dansLesLimitesX = (x >= minX && x <= maxX);
        boolean dansLesLimitesY = (y >= minY && y <= maxY);

        // Il y a collision si l'acteur est à la fois dans les limites X ET Y
        return dansLesLimitesX && dansLesLimitesY;
    }

}
