package core;
import utils.Vecteur;

public abstract class Acteur {

    private Vecteur position;

    public Acteur(Vecteur position) {
        this();
        this.position = new Vecteur(position);
    }

    public Acteur() {
        Vecteur pos = new Vecteur(0);
        this.position = pos;
    }

    public abstract void actualiser(double dt);

    public Vecteur getPosition() {
        Vecteur pos = new Vecteur(this.position);
        return pos;
    }

    public Vecteur void setPosition(Vecteur v) {
        this.position = new Vecteur(v); 
    }

    public double distance(Acteur acteur) {
        return this.position.distance(acteur.getPosition());
    }

}
