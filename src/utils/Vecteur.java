package utils;

/** Classe vecteur dans un plan.
 * Cette classe décrit le comportement d'un vecteur (a, b).
 * Utilisé pour les coordonnées ou les vecteurs dans le plan.
 */
public class Vecteur {

    /**
     * Composante du vecteur sur l'axe des abscisses (axe x).
     */
    private double x;
     /**
     * Composante du vecteur sur l'axe des ordonnées (axe y).
     */
    private double y;

    /**
     * Construit un vecteur avec les composantes {@code x} et {@code y} spécifiées.
     *
     * @param x la composante sur l'axe x
     * @param y la composante sur l'axe y
     */
    public Vecteur(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Construit un vecteur dont les deux composantes sont égales à {@code a}.
     *
     * <p>Équivalent à {@code new Vecteur(a, a)}.</p>
     *
     * @param a la valeur commune pour {@code x} et {@code y}
     */
    public Vecteur(double a) {
        this(a, a);
    }

    /**
     * Construit un vecteur nul {@code (0, 0)}.
     *
     * <p>Équivalent à {@code new Vecteur(0, 0)}.</p>
     */

    public Vecteur() {
        this(0);
    }

    /** 
     * Construire un vecteur AB avec 2 points.
     *
     * @param a Position A.
     * @param b Position B.
     */
    public Vecteur(Vecteur a, Vecteur b) {
        this.x = b.getX() - b.getX();
        this.x = b.getY() - b.getY();
    }

     /**
     * Construit un vecteur par copie d'un vecteur existant.
     *
     * <p>Le vecteur créé est indépendant du vecteur source :
     * toute modification de l'un n'affecte pas l'autre.</p>
     *
     * @param v le vecteur à copier, ne doit pas être {@code null}
     */
    public Vecteur(Vecteur v) {
        this.x = v.x;
        this.y = v.y;
    }

    /**
     * Retourne la composante {@code x} du vecteur.
     *
     * @return la valeur de la composante x
     */
    public double getX() {
        return this.x;
    }

     /**
     * Retourne la composante {@code y} du vecteur.
     *
     * @return la valeur de la composante y
     */
    public double getY() {
        return this.y;
    }

    /**
     * Modifie la composante {@code x} du vecteur.
     *
     * @param x la nouvelle valeur de la composante x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Modifie la composante {@code y} du vecteur.
     *
     * @param y la nouvelle valeur de la composante y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Calcule la distance euclidienne entre ce vecteur et le vecteur {@code v}.
     *
     * <p>La distance est calculée selon la formule :</p>
     * <pre>
     *   d = √((x₂ - x₁)² + (y₂ - y₁)²)
     * </pre>
     *
     * @param v le vecteur cible, ne doit pas être {@code null}
     * @return la distance euclidienne entre les deux vecteurs, toujours positive ou nulle
     */
    public double distance(Vecteur v) {
        double dx = this.x - v.x;
        double dy = this.y - v.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

     /**
     * Translate ce vecteur en ajoutant les valeurs {@code dx} et {@code dy}
     * à ses composantes respectives.
     *
     * <p>Cette méthode modifie directement l'objet courant :</p>
     * <pre>
     *   x ← x + dx
     *   y ← y + dy
     * </pre>
     *
     * @param dx le déplacement à appliquer sur l'axe x (peut être négatif)
     * @param dy le déplacement à appliquer sur l'axe y (peut être négatif)
     */
    public void translater(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public void opposer() {
        this.x = -1 * this.x;
        this.y = -1 * this.y;
    }

    public void ajouter(Vecteur v) {
        this.x += v.getX();
        this.y += v.getY();
    }

    public void soustraire(Vecteur v) {
        this.x -= v.x; 
        this.y -= v.y;
    }

    public double getNorme() {
        return Math.sqrt((this.x * this.x) + (this.y * this.y));
    }

    public void normaliser() {
        double norme = this.getNorme();
        this.x /= norme;
        this.y /= norme;
    }

    public void multiplier(Vecteur v) {
        this.x *= v.getX();
        this.y *= v.getY();
    }

    public void multiplier(double f) {
        this.multiplier(new Vecteur(f));
    }
    
    public double produitScalaire(Vecteur v) {
        return this.x * v.x + this.y * v.y;
    }
    
    public void rotationner(double theta) {
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);

        double nouveauX = this.x * cos - this.y * sin;
        double nouveauY = this.x * sin + this.y * cos;

        this.x = nouveauX;
        this.y = nouveauY;
    }

}
