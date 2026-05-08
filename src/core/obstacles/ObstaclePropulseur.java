package core.obstacles;
import core.Obstacle;
import core.Projectile;
import utils.Vecteur;
import core.collisions.CollisionSegment;

public class ObstaclePropulseur extends Obstacle {

    private Vecteur direction;
    private Vecteur directeur = new Vecteur(10.f);
    private double epaisseur = 10.f;

    public ObstaclePropulseur(Vecteur position)
    {
        this.setPosition(position);
        this.setCollision(new CollisionSegment(this.getPosition(), directeur, epaisseur));
        this.direction = new Vecteur(directeur);
        this.direction.normaliser();
    }

    @Override
    public void eventAction(Projectile projectile) {

        projectile.setVitesse(new Vecteur(this.direction));

    }

    @Override
    public void actualiser(double dt) {

    }

    @Override
    public Obstacle copier() {
        ObstaclePropulseur copy = new ObstaclePropulseur(new Vecteur(this.getPosition()));
        return copy;
    }

}
