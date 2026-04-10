package utils;

/** Classe vecteur dans un plan.
 * Cette classe décrit le comportement d'un vecteur (a, b).
 * Utilisé pour les coordonnées ou les vecteurs dans le plan.
 */
public class Vecteur {
    
    /** Valeur sur x. */
    private double x; 
    /** Valeur sur y. */
    private double y;

    public Vecteur(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vecteur(double a) {
        this(a, a);
    }

    public Vecteur() {
        this(0);
    }

    public Vecteur(Vecteur v) {
        this.x = v.x;
        this.y = v.y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double distance(Vecteur v) {
        // TODO
    }

    public void translater(double dx, double dy) {
        // TODO
    }

}
