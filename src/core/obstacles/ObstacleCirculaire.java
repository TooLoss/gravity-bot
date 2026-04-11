package core.obstacles;

import core.Acteur;
import core.Obstacle;
import core.Projectile;

/**
 * Obstacle de forme circulaire.
 * Le projectile ne peut pas pénétrer à l'intérieur : collision = arrêt.
 */
public class ObstacleCirculaire extends Obstacle {

    private double rayon; // rayon physique du cercle

    public ObstacleCirculaire(Vecteur position, double rayon) {
        this.setPosition(position);
        this.rayon = rayon;
    }

    @Override
    public void eventAction(Projectile projectile) {
        projectile.Collision(true);
    }
}
