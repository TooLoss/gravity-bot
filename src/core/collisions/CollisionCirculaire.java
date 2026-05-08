package core.collisions;
import core.Acteur;
import core.Collision;
import utils.Vecteur;
import utils.Vecteur;
import core.Acteur;
import core.Collision;
import core.Obstacle;

public class CollisionCirculaire implements Collision {
    private Vecteur centre;
    private double rayon;

    public CollisionCirculaire(Vecteur centre, double rayon) {
        this.centre = centre;
        this.rayon = rayon;
    }

    @Override
    public boolean estEnCollision(Acteur acteur) {
        System.out.println("Centre de la collision : " + this.centre.toString());
        return centre.distance(acteur.getPosition()) < rayon;
    }
}
