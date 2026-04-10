package core.collision;

public class CollisionLineaire implements Collision {

    private Vecteur position;
    private Vecteur direction;
    private double taille;

    public CollisionLineaire(Vecteur pos, Vecteur dir, double t) {
        this.position = pos;
        this.direction = dir;
        this.taille = t;
    }
    
    @Override
    public boolean estEnCollision(Acteur acteur) {
        
    }

}
