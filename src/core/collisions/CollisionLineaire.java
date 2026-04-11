package core.collision;

public class CollisionLineaire implements Collision {

    private Vecteur position;
    private Vecteur direction;
    private double epaisseur;

    public CollisionLineaire(Vecteur pos, Vecteur dir, double e) {
        this.position = pos;
        this.direction = dir;
        this.epaisseur = e;
    }
    
    @Override
    public boolean estEnCollision(Acteur acteur) {
        Vecteur AP = new Vecteur(this.position, acteur.getPosition());
        Vecteur AB = new Vecteur(this.direction); AB.ajouter(this.position);
        double distance = AP.produitScalaire(AB);
        return distance < epaisseur;
    }

}
