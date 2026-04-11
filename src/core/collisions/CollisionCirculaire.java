public class CollisionCirculaire implements Collision {

    private Vecteur centre;
    private double rayon;

    public CollisionCirculaire(Vecteur centre, double rayon) {
        this.centre = centre;
        this.rayon = rayon;
    }

    @Override
    public boolean estEnCollision(Acteur acteur, Acteur source) {
        return source.distance(acteur) < rayon;
    }
}
