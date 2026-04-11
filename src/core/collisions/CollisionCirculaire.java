package core.collisions;
import utils.Vecteur;
import core.Acteur;
import core.Collision;
import core.Obstacle;

public class CollisionCirculaire implements Collision {

    private Vecteur centre;
    private double rayon;

    public CollisionCirculaire(Vecteur centre, double rayon) {
        // Le centre peut être modifié ailleur. ne pas faire de copies !!!
        this.centre = centre;
        this.rayon = rayon;
    }

    @Override
    public boolean estEnCollision(Acteur acteur) {
        return centre.distance(acteur) < rayon;
    }
}
