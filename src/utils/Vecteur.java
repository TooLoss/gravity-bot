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
        this.x = b.getX() - a.getX();
        this.y = b.getY() - a.getY();
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
        this.x -= v.getX(); 
        this.y -= v.getY();
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
    /**
     * Effectue une rotation du vecteur d'un angle theta autour d'un point pivot spécifié.
     *
     * @param theta l'angle de rotation en radians
     * @param pivot le point (Vecteur) autour duquel la rotation s'effectue
     */
     public void rotationnerAutourPivot(double theta, Vecteur pivot) {
        // On déplace le vecteur pour que le pivot devienne le centre (0,0)
        this.soustraire(pivot);

        // On applique la rotation simple (qui tourne toujours autour de 0,0)
        this.rotationner(theta);

        // On remet le vecteur à sa vraie place
        this.ajouter(pivot);
    }
    /**
     * Projette orthogonalement ce vecteur sur un autre vecteur (axe).
     * * @param axe le vecteur sur lequel on souhaite faire la projection
     */
    public void projeterOrthogonalement(Vecteur axe) {
        // Calcul de U . U (la norme de l'axe au carré)
        // On réutilise la fonction produitScalaire 
        double normeAxeCarre = axe.produitScalaire(axe);

        // Éviter la division par zéro
        // Si on essaie de projeter sur un point (0,0), la projection devient nulle.
        if (normeAxeCarre == 0) {
            this.x = 0;
            this.y = 0;
            return;
        }

        // Calcul de V . U (le produit scalaire entre ce vecteur et l'axe)
        double produitScalaire = this.produitScalaire(axe);

        // Calcul du facteur ((V . U)/(U.U))  
        double facteur = produitScalaire / normeAxeCarre;

        // On applique ce facteur à l'axe pour obtenir notre nouveau vecteur. projuV = ((V . U)/(U.U))U
        this.x = axe.getX() * facteur;
        this.y = axe.getY() * facteur;
    }
    /**
     * Calcule et retourne un vecteur normal (perpendiculaire) au vecteur actuel.
     * Le vecteur retourné correspond à une rotation de 90° vers la gauche.
     * * @return un nouveau Vecteur perpendiculaire
     */
    public Vecteur vecteurNormal() {
        // n = (-y, x)
        return new Vecteur(-this.y, this.x);
    }
}
