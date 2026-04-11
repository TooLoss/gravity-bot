package core.obstacles;

public class ObstacleAimantPull extends Obstacle {

    private static final double puissance = 1.f;
    private static final double rayon = 5.f;

    public ObstacleAimantPull() {
        this.setCollision(new CollisionCirculaire(this.getPosition, rayon));
    }

    @Override
    public void eventAction(Projectile projectile) {
        Vecteur posProjectile = projectile.getPosition();
        Vecteur posObstacle = this.getPosition();
        Vecteur directeur = new Vecteur(posProjectile);
        directeur.soustraire(posObstacle).opposer();
        directeur.normaliser();
        directeur.multiplier(puissance);
        projectile.ajouterVitesse(directeur);
     }

}
