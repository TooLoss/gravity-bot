public class CollisionCirculaire implements Collision {

    private Vecteur centre;
    private double rayon;

    public CollisionCirculaire(Vecteur centre, double rayon) {
        this.centre = centre;
        this.rayon = rayon;
    }

    @Override
    public boolean estEnCollision(Acteur acteur) {
        return centre.distance(acteur) < rayon;
    }
}
