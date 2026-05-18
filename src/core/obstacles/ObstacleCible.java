package core.obstacles;


import java.awt.Graphics;
import java.awt.Color;
import utils.Vecteur;
import core.obstacles.Obstacle;
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

    @Override
    public void afficher(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(
                (int) (this.getPosition().getX() - rayon / 2),
                (int) (this.getPosition().getY() - rayon / 2),
                (int) rayon,
                (int) rayon);
    }
}
