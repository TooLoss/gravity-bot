package utils;

/** Classe vecteur dans un plan.
 * Cette classe décrit le comportement d'un vecteur (a, b).
 * Utilisé pour les coordonnées ou les vecteurs dans le plan.
 */
public class Vecteur {

    /** Composante du vecteur sur l'axe des abscisses (axe x). */
    private double x;
    /** Composante du vecteur sur l'axe des ordonnées (axe y). */
    private double y;

    /** Construit un vecteur avec les composantes {@code x} et {@code y} spécifiées.
     * @param x la composante sur l'axe x
     * @param y la composante sur l'axe y
     */
    public Vecteur(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** Construit un vecteur dont les deux composantes sont égales à {@code a}.
     * @param a la valeur commune pour {@code x} et {@code y}
     */
    public Vecteur(double a) {
        this(a, a);
    }

    /** Construit un vecteur nul {@code (0, 0)}. */
    public Vecteur() {
        this(0);
    }

    /** Construire un vecteur AB avec 2 points.
     * @param a Position A.
     * @param b Position B.
     */
    public Vecteur(Vecteur a, Vecteur b) {
        this.x = b.getX() - a.getX();
        this.y = b.getY() - a.getY();
    }

    /** Construit un vecteur par copie d'un vecteur existant.
     * @param v le vecteur à copier, ne doit pas être {@code null}
     */
    public Vecteur(Vecteur v) {
        this.x = v.x;
        this.y = v.y;
    }

    /** Retourne la composante {@code x} du vecteur.
     * @return la valeur de la composante x
     */
    public double getX() {
        return this.x;
    }

    /** Retourne la composante {@code y} du vecteur.
     * @return la valeur de la composante y
     */
    public double getY() {
        return this.y;
    }

    /** Modifie la composante {@code x} du vecteur.
     * @param x la nouvelle valeur de la composante x
     */
    public void setX(double x) {
        this.x = x;
    }

    /** Modifie la composante {@code y} du vecteur.
     * @param y la nouvelle valeur de la composante y
     */
    public void setY(double y) {
        this.y = y;
    }

    /** Calcule la distance euclidienne entre ce vecteur et le vecteur {@code v}.
     * @param v le vecteur cible
     * @return la distance euclidienne entre les deux vecteurs, toujours positive ou nulle
     */
    public double distance(Vecteur v) {
        assert(v != null);

        double dx = this.x - v.x;
        double dy = this.y - v.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /** Translate ce vecteur en ajoutant les valeurs {@code dx} et {@code dy} à
     * ses composantes respectives.
     * @param dx le déplacement à appliquer sur l'axe x
     * @param dy le déplacement à appliquer sur l'axe y
     */
    public void translater(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    /** Applique l'opposé sur le vecteur. */
    public void opposer() {
        this.x = -1 * this.x;
        this.y = -1 * this.y;
    }

    /** Translate d'un vecteur v.
     * @param v Vecteur à ajouter.
     */
    public void ajouter(Vecteur v) {
        this.x += v.getX();
        this.y += v.getY();
    }

    /** Soustraire un vecteur v.
     * @param v Vecteur qui soustrait.
     */
    public void soustraire(Vecteur v) {
        this.x -= v.getX();
        this.y -= v.getY();
    }

    /** Récupérer la norme du vecteur.
     * @return double norme du vecteur.
     */
    public double getNorme() {
        return Math.sqrt((this.x * this.x) + (this.y * this.y));
    }

    /** Normalise le vecteur. Le vecteur à une norme de 1 après l'opération. */
    public void normaliser() {
        double norme = this.getNorme();
        this.x /= norme;
        this.y /= norme;
    }

    /** Multiplie le vecteur par un autre coordonnées à coordonnées.
     * @param v Vecteur utilisé pour multiplication.
     */
    public void multiplier(Vecteur v) {
        this.x *= v.getX();
        this.y *= v.getY();
    }

    /** Multiplie le vecteur par un scalaire.
     * @param f double scalaire.
     */
    public void multiplier(double f) {
        this.multiplier(new Vecteur(f));
    }

    /** Fait le produit scalaire de ce vecteur avec un autre.
     * @param v Vecteur.
     * @return double valeur du produit scalaire.
     */
    public double produitScalaire(Vecteur v) {
        return this.x * v.x + this.y * v.y;
    }

    /** Fait une rotation autour de 0 du vecteur d'un angle theta.
     * @param theta double angle en radians.
     */
    public void rotationner(double theta) {
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);

        double nouveauX = this.x * cos - this.y * sin;
        double nouveauY = this.x * sin + this.y * cos;

        this.x = nouveauX;
        this.y = nouveauY;
    }

    /** Effectue une rotation du vecteur d'un angle theta autour d'un point pivot spécifié.
     * @param theta l'angle de rotation en radians.
     * @param pivot le centre de rotation.
     */
    public void rotationner(double theta, Vecteur pivot) {
        this.soustraire(pivot);
        this.rotationner(theta);
        this.ajouter(pivot);
    }

    /** Projette orthogonalement ce vecteur sur un autre vecteur (axe).
     * @param axe le vecteur sur lequel on souhaite faire la projection
     */
    public void projeterOrthogonalement(Vecteur axe) {
        double normeAxeCarre = axe.produitScalaire(axe);

        if (normeAxeCarre == 0) {
            this.x = 0;
            this.y = 0;
            return;
        }

        double produitScalaire = this.produitScalaire(axe);
        double facteur = produitScalaire / normeAxeCarre;

        this.x = axe.getX() * facteur;
        this.y = axe.getY() * facteur;
    }

    /** Calcule et retourne un vecteur normal (perpendiculaire) au vecteur actuel.
     * Le vecteur retourné correspond à une rotation de 90° vers la gauche.
     * @return un nouveau Vecteur perpendiculaire
     */
    public Vecteur vecteurNormal() {
        // n = (-y, x)
        return new Vecteur(-this.y, this.x);
    }

    @Override
    public String toString() { 
        return "(" + this.x + "," + this.y + ")";
    }
}
