package core.obstacles;

import java.awt.Graphics;
import java.awt.Color;
import utils.Vecteur;
import core.Acteur;
import core.Collision;
import core.obstacles.Obstacle;
import core.Projectile;
import core.collisions.CollisionCirculaire;

public class ObstacleAimantPull extends Obstacle {

    private static final double puissance = 10.f;
    private static final double rayon = 50.f;

    public ObstacleAimantPull() {
        this.setCollision(new CollisionCirculaire(this.getPosition(), rayon));
    }

    // TODO le mettre dans le constructeur Obstacle de base
    public ObstacleAimantPull(Vecteur position) {
        super(position);
        this.setCollision(new CollisionCirculaire(this.getPosition(), rayon));
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
        ObstacleAimantPull copy = new ObstacleAimantPull(new Vecteur(this.getPosition()));
        return copy;
    }

    @Override
    public void afficher(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int) this.getPosition().getX() - 5, (int) this.getPosition().getY() - 5, 10, 10);
        g.drawOval(
                (int) (this.getPosition().getX() - rayon / 2),
                (int) (this.getPosition().getY() - rayon / 2),
                (int) rayon,
                (int) rayon);
    }
}
