package core.collisions;
import utils.Vecteur;
import core.Acteur;
import core.Collision;
import core.Obstacle;
public class CollisionSegment implements Collision {

    private Vecteur position;
    private Vecteur direction;
    private double epaisseur;

    public CollisionSegment(Vecteur pos, Vecteur dir, double e) {
        this.position = pos;
        this.direction = new Vecteur(dir);
        this.epaisseur = e;
    }

    public CollisionSegment(Vecteur pos,
            double angle,
            double longueur,
            double e)
    {
        this.position = pos;
        this.direction = new Vecteur(Math.cos(angle), Math.sin(angle));
        this.direction.multiplier(longueur);
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
