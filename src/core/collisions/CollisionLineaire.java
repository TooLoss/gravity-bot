package core.collisions;
import utils.Vecteur;
import core.Acteur;
import core.Collision;
import core.Obstacle;
public class CollisionLineaire implements Collision {

    private Vecteur position;
    private Vecteur direction;
    private double epaisseur;
    private double portee;

    public CollisionLineaire(Vecteur pos, Vecteur dir, double portee, double e) {
        this.position = pos;

        this.direction = new Vecteur(dir);
        this.direction.normaliser();
        
        this.portee = portee;
        this.epaisseur = e;
    }
    
    public CollisionLineaire(Vecteur pos, Vecteur dir, double e) {
        this.position = pos;

        this.direction = new Vecteur(dir);
        this.portee = dir.getNorme();
        
        this.direction.normaliser();
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
