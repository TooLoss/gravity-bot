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
        Vecteur u = new Vecteur(this.direction);
        u.normaliser();

        Vecteur n = u.vecteurNormal();

        Vecteur OP = new Vecteur(acteur.getPosition());
        OP.soustraire(this.position);

        double projDirection = OP.produitScalaire(u);
        double projNormale = OP.produitScalaire(n);

        boolean dansLaPortee = projDirection >= 0 && projDirection <= this.portee;
        boolean dansLEpaisseur = Math.abs(projNormale) <= this.epaisseur;

        return dansLaPortee && dansLEpaisseur;
    }

}
