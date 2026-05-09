package core;

import java.awt.Image;
import javax.swing.ImageIcon;
import utils.Vecteur;

/** Classe acteur.
 * Classe abstraite. Tout objet qui se place sur le plateau hérite de cette
 * classe.
 */
public abstract class Acteur {

    /** Position de l'acteur dans l'écran. */
    private Vecteur position;

    /** Constructeur de l'acteur en fonction d'une position.
     * @param position Position sur l'écran.
     */
     
    protected Image texture; 
    public Acteur(Vecteur position) {
        this();
        this.position = new Vecteur(position);
    }
    /**
     * Charge une image à partir d'un chemin de fichier.
     * @param chemin Le chemin vers l'image (ex: "ressources/mur.png")
     */
    public void setTexture(String chemin) {
        // ImageIcon est un moyen de charger une image
        this.texture = new ImageIcon(chemin).getImage();
    }

    public Image getTexture() {
        return this.texture;
    }
    /** Constructeur de l'acteur avec une position nulle. */
    public Acteur() {
        Vecteur pos = new Vecteur(0);
        this.position = pos;
    }

    /** Fonction abstraite qui est executé à chaque frame du jeu.
     * @param dt Delta t écoulé depuis la dernière frame.
     */
    public void actualiser(double dt) {}

    /** Récupérer la position de l'acteur.
     * @return Vecteur position copié de l'acteur.
     */
    public Vecteur getPosition() {
        Vecteur pos = new Vecteur(this.position);
        return pos;
    }

    /** Setter de la position de l'acteur.
     * @param v Vecteur position de l'acteur.
     */
    public void setPosition(Vecteur v) {
        assert (v != null);
        this.position = new Vecteur(v); 
    }

    /** Setter de la position de l'acteur.
     * @param x Position x de l'acteur.
     * @param y Position y de l'acteur.
     */
    public void setPosition(double x, double y) {
        this.position = new Vecteur(x, y);
    }

    /** Distance de l'acteur à une autre.
     * @param acteur Acteur dont on mesure la distance.
     * @return double distance entre les acteurs.
     */
    public double distance(Acteur acteur) {
        return this.position.distance(acteur.getPosition());
    }

    /** Translater l'acteur d'un vecteur v.
     * @param v Vecteur translation.
     */
    public void translater(Vecteur v) {
    	this.position.translater(v.getX(), v.getY());
    }

}
