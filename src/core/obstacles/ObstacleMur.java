package core.obstacles;

import core.Obstacle;
import core.Projectile;
import core.collisions.CollisionRectangle;
import utils.Vecteur;

public class ObstacleMur extends Obstacle {

    private Vecteur pointA;
    private Vecteur pointB;

    public ObstacleMur(Vecteur pointA, Vecteur pointB) {
        this.pointA = new Vecteur(pointA);
        this.pointB = new Vecteur(pointB);

        this.setPosition(pointA);
        this.setCollision(new CollisionRectangle(this.pointA, this.pointB));
    }

    @Override
    public void eventAction(Projectile projectile) {
        Vecteur position = projectile.getPosition();
        Vecteur vitesse = new Vecteur(projectile.vitesse);

        double x = position.getX();
        double y = position.getY();

        double minX = Math.min(pointA.getX(), pointB.getX());
        double maxX = Math.max(pointA.getX(), pointB.getX());
        double minY = Math.min(pointA.getY(), pointB.getY());
        double maxY = Math.max(pointA.getY(), pointB.getY());

        double distGauche = Math.abs(x - minX);
        double distDroite = Math.abs(x - maxX);
        double distHaut = Math.abs(y - minY);
        double distBas = Math.abs(y - maxY);

        double minDist = Math.min(Math.min(distGauche, distDroite),
                                  Math.min(distHaut, distBas));

        if (minDist == distGauche || minDist == distDroite) {
            vitesse.setX(-vitesse.getX());
        } else {
            vitesse.setY(-vitesse.getY());
        }

        projectile.setVitesse(vitesse);
    }

    
    @Override
    public Obstacle copier() {
        ObstacleMur copy = new ObstacleMur(pointA, pointB);
        return copy;
    }
}
