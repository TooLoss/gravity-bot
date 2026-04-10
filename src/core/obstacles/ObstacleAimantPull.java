package core.obstacles;

public class ObstacleAimantPull extends Obstacle {

    private static final double puissance = 1.f;
    private static final Collision collision;

    @Override
    public void eventAction(Projectile projectile) {
        Vecteur posProjectile = projectile.getPosition();
        Vecteur posObstacle = this.getPosition();
        Vecteur directeur = posProjectile.soustraire(posObstacle).opposer();
        directeur.normaliser();
        directeur.multiplier(puissance);
        projectile.ajouterVitesse(directeur);
     }

}
