package core.obstacles;

import java.awt.Graphics;
import java.awt.Color;
import utils.Vecteur;
import core.Acteur;
import core.Collision;
import core.obstacles.Obstacle;
import core.Projectile;
import core.collisions.CollisionCirculaire;

public class ObstacleAimant extends Obstacle {

    private double puissance;
    private double rayon;

    public ObstacleAimant(Vecteur position, double puissance, double rayon) {
        super(position);
        this.puissance = puissance;
        this.rayon = rayon;
        this.setCollision(new CollisionCirculaire(this.getPosition(), this.rayon));
    }

    @Override
    public void eventAction(Projectile projectile) {
        Vecteur posProjectile = projectile.getPosition();
        Vecteur posObstacle = this.getPosition();
        Vecteur directeur = new Vecteur(posProjectile);
        directeur.soustraire(posObstacle);
        directeur.opposer();
        directeur.normaliser();
        directeur.multiplier(puissance/100);
        projectile.ajouterVitesse(directeur);
    }

    @Override
    public void actualiser(double dt) {
        // Acteur class abstract
    }

    @Override
    public Obstacle copier() {
        ObstacleAimant copy = new ObstacleAimant(
                new Vecteur(this.getPosition()),
                this.puissance,
                this.rayon);
        return copy;
    }

    @Override
    public void afficher(Graphics g) {
        if (this.puissance > 0) {
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.RED);
        }
        g.fillRect((int) this.getPosition().getX() - 5, (int) this.getPosition().getY() - 5, 10, 10);
        g.drawOval(
                (int) (this.getPosition().getX() - rayon),
                (int) (this.getPosition().getY() - rayon),
                (int) rayon*2,
                (int) rayon*2);
    }
}
