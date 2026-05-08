package core.obstacles;
import core.Obstacle;
import core.Projectile;
import utils.Vecteur;
import core.collisions.CollisionSegment;

public class ObstaclePropulseur extends Obstacle {

    private Vecteur direction;
    private Vecteur directeur = new Vecteur(50.f);
    private double epaisseur = 50.f;

    public ObstaclePropulseur(Vecteur position)
    {
        this.setPosition(position);
        this.setCollision(new CollisionSegment(this.getPosition(), directeur, epaisseur));
        this.direction = new Vecteur(directeur);
        this.direction.normaliser();
    }

    public void setDirection(Vecteur dir) {
        this.direction = new Vecteur(dir);
        this.direction.normaliser();
        this.directeur = new Vecteur(this.direction);
        this.directeur.multiplier(50.f);
        this.setCollision(new CollisionSegment(this.getPosition(), this.directeur, this.epaisseur));
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
        ObstaclePropulseur copy = new ObstaclePropulseur(new Vecteur(this.getPosition()));
        copy.setDirection(this.direction);
        return copy;
    }

}
