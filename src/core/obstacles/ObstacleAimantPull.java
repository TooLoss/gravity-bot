package core.obstacles;

public class ObstacleAimantPull extends Obstacle {

    private static final double rayonAction = 5.f;
    private static final double puissance = 1.f;

    @Override
    public void eventAction(Projectile projectile) {
        Vecteur posProjectile = projectile.getPosition();
        Vecteur posObstacle = this.getPosition();
        Vecteur directeur = posProjectile.soustraire(posObstacle).opposer();
        directeur.normaliser();
        directeur.multiplier(puissance);
        projectile.ajouterVitesse(directeur);
     }

    @Override
    public boolean estDansObstacle(Acteur acteur) {
        return ( Math.abs(this.distance(acteur)) < rayonAction );
    }

}
