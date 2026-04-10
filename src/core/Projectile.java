package core;
import utils.Vecteur;

public class Projectile extends Acteur {
    
    public Vecteur vitesse;

    public Projectile(Vecteur position, Vecteur vitesse) {
        this.vitesse = vitesse;
        this.setPosition(position);
    }

    @Override
    public void actualiser(double dt) {
        // Translater le vecteur actualiser
        double x = vitesse.getX();
        double y = vitesse.getY();
        Vecteur vecteurTranslation = new Vecteur(x * dt, y * dt);
        this.translater(vecteurTranslation)
    }

    /** Ajoute à la vitesse du projecteur v.
     * @param v Vecteur à ajouter.
     */
    public void ajouterVitesse(Vecteur v) {
        // TODO
    }

    public void setVitesse(Vecteur v) {
        this.vitesse = new Vecteur(v);
    }

}
