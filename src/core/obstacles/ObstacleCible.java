package core.obstacles;
import utils.Vecteur;
import core.Obstacle;
import core.collisions.*;
import core.Projectile;

public class ObstacleCible extends Obstacle {

    private static final double rayon = 10.f;

    public ObstacleCible(Vecteur position) {
        super(position);
        this.setCollision(new CollisionCirculaire(this.getPosition(), rayon));
    }

    @Override
    public void eventAction(Projectile projectile) {
        System.out.println("WIIIIN");
    }

    @Override
    public void actualiser(double dt) {
        // Acteur class abstract
        // TODO à enlever c'est top chiant rapidement
    }

    @Override
    public Obstacle copier() {
        ObstacleCible copy = new ObstacleCible(new Vecteur(this.getPosition()));
        return copy;
    }
}
