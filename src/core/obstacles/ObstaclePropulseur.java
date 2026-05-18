package core.obstacles;
import core.obstacles.Obstacle;
import core.Projectile;
import utils.Vecteur;
import core.collisions.CollisionSegment;

public class ObstaclePropulseur extends ObstacleSegment {

    private Vecteur direction;

    public ObstaclePropulseur(Vecteur pointA, Vecteur pointB) {
        super(pointA, pointB);
        this.setCollision(new CollisionSegment(this.pointA, getDirecteur(), 50.0));
        this.direction = getDirecteur();
        this.direction.normaliser();
    }

    public void setDirection(Vecteur dir) {
        this.direction = new Vecteur(dir);
        this.direction.normaliser();
        Vecteur newDirecteur = new Vecteur(this.direction);
        newDirecteur.multiplier(getLongueur());
        this.setCollision(new CollisionSegment(this.pointA, newDirecteur, 50.0));
    }

    public Vecteur getDirection() {
        return new Vecteur(this.direction);
    }

    @Override
    public void eventAction(Projectile projectile) {
        Vecteur nouvelleVitesse = new Vecteur(this.direction);
        nouvelleVitesse.multiplier(Projectile.vitesseSatellite);
        projectile.setVitesse(nouvelleVitesse);
    }

    @Override
    public void actualiser(double dt) {

    }

    @Override
    public Obstacle copier() {
    	ObstaclePropulseur copy = new ObstaclePropulseur(new Vecteur(this.pointA), new Vecteur(this.pointB));
        copy.setDirection(this.direction);
        return copy;
    }

}
