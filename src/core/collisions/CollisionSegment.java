package core.collisions;
import utils.Vecteur;
import core.Acteur;
import core.Collision;
import core.obstacles.Obstacle;
public class CollisionSegment implements Collision {

    /** Position d'origine du segment. */
    private Vecteur position;
    /** Vecteur directeur du segment. */
    private Vecteur directeur;
    /** Epaisseur du segment. */
    private double epaisseur;

    /** Constucteur du segment en fonction d'une position, directeur et
     * épaisseur.
     * @param pos Vecteur position d'origine du segment.
     * @param dir Vecteur directeur depuis l'origine.
     * @param e épaisseur du segment.
     */
    public CollisionSegment(Vecteur pos, Vecteur dir, double e) {
        this.position = pos;
        this.directeur = new Vecteur(dir);
        this.epaisseur = e;
    }

    /** Constructeur du segment en fonction d'une position, d'un angle, d'une
     * longueur et d'une épaisseur.
     * @param pos Vecteur position d'origine du segment.
     * @param angle double angle du segment en radiant.
     * @param longueur double longueur du Segment.
     * @param e double épaisseur du segment.
     */
    public CollisionSegment(Vecteur pos,
            double angle,
            double longueur,
            double e)
    {
        this.position = pos;
        this.directeur = new Vecteur(Math.cos(angle), Math.sin(angle));
        this.directeur.multiplier(longueur);
        this.epaisseur = e;
    }

    @Override
    public boolean estEnCollision(Acteur acteur) {
        Vecteur u = new Vecteur(this.directeur);
        u.normaliser();

        Vecteur n = u.vecteurNormal();

        Vecteur OP = new Vecteur(acteur.getPosition());
        OP.soustraire(this.position);

        double projDirection = OP.produitScalaire(u);
        double projNormale = OP.produitScalaire(n);

        boolean dansLaPortee = projDirection >= 0 && projDirection <= this.directeur.getNorme();
        boolean dansLEpaisseur = Math.abs(projNormale) <= this.epaisseur;

        return dansLaPortee && dansLEpaisseur;
    }

}
