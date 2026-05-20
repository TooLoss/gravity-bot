package core.obstacles;

import core.obstacles.ObstacleSegment;
import core.obstacles.Obstacle;
import core.collisions.CollisionSegment;
import utils.Vecteur;

public abstract class ObstacleSegment extends Obstacle {

    protected double epaisseur;
    protected double distanceMax;

    /**
     * @param pointA Origine du segment (= position de l'obstacle).
     * @param pointB Extrémité du segment.
     */
    public ObstacleSegment(Vecteur pointA, Vecteur pointB, double epaisseur, double distanceMax) {
        super(pointA);
        this.epaisseur = epaisseur;
        this.distanceMax = distanceMax;
        this.setPointFinal(pointB);
        this.setCollision(new CollisionSegment(this.getPosition(), this.getDirection(), epaisseur));
        if (this.getLongueur() > distanceMax)
            this.setLongueur(distanceMax);
    }

    public Vecteur getDirection() {
        Vecteur posA = this.getPosition();
        Vecteur posB = this.getPointFinal();
        return new Vecteur(posB.getX() - posA.getX(), posB.getY() - posA.getY());
    }

    public void setLongueur(double longueur) {
        Vecteur posA = this.getPosition();
        Vecteur direction = this.getDirection();
        
        double longueurActuelle = posA.distance(this.getPointFinal());
        
        if (longueurActuelle == 0) {
            Vecteur nouveauB = new Vecteur(posA.getX() + longueur, posA.getY());
            this.setPointFinal(nouveauB);
            return;
        }

        double dirX = direction.getX() / longueurActuelle;
        double dirY = direction.getY() / longueurActuelle;

        double nouveauX = posA.getX() + (dirX * longueur);
        double nouveauY = posA.getY() + (dirY * longueur);

        this.setPointFinal(new Vecteur(nouveauX, nouveauY));
    }

    public double getLongueur() {
        Vecteur posA = this.getPosition();
        Vecteur posB = this.getPointFinal();
        return posA.distance(posB);
    }

    public double getEpaisseur() {
        return this.epaisseur;
    }
}
