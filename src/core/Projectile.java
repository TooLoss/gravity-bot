package core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import utils.Vecteur;
import java.util.ArrayList;
import java.util.List;

/** Class projectile (satellite) qui se déplace sur la map. */
public class Projectile extends Acteur {

    /** Vitesse du satellite. */
    public Vecteur vitesse;
    /** Norme maximale de la vitesse du satellite. */
    public static final double VITESSE_MAX = 10.f;
    /** Liste des points parcouru par le satellite pour l'affichage. */
    private final List<Vecteur> historiquePositions = new ArrayList<>();

    /** Constructeur du projectile.
     * @param position Vecteur position initiale.
     * @param vitesse Vecteur vitesse initiale.
     */
    public Projectile(Vecteur position, Vecteur vitesse) {
        this.setVitesse(vitesse);
        this.setPosition(position);
    }

    /** Ajoute à la vitesse du projecteur v.
     * @param v Vecteur à ajouter.
     */
    public void ajouterVitesse(Vecteur v) {
        this.vitesse.ajouter(v);
        vitesse.normaliser();
        vitesse.multiplier(VITESSE_MAX);
    }

    /** Setter de la vitesse du satellite.
     * @param v Vecteur nouveau.
     */
    public void setVitesse(Vecteur v) {
        this.vitesse = new Vecteur(v);
    }

    /** Getter de la vitesse du satellite.
     * @return Vecteur vitesse.
     */
    public Vecteur getVitesse() {
        return this.vitesse;
    }

    /** Reset le satellite à la fin d'un tour. */
    public void reset() {
        historiquePositions.clear();
    }

    @Override
    public void actualiser(double dt) {
        // Translater le vecteur actualiser
        historiquePositions.add(new Vecteur(this.getPosition()));
        double x = vitesse.getX();
        double y = vitesse.getY();
        Vecteur vecteurTranslation = new Vecteur(x * dt, y * dt);
        this.translater(vecteurTranslation);
    }

    @Override
    public void afficher(Graphics g) {
        /* Généré par IA */
        // 1. Dessin de la traînée (Orbite historique)
        // Utilisation d'un orange/jaune semi-transparent plus fin (2x2 au lieu de 3x3)
        g.setColor(new Color(255, 200, 0, 100));
        for (Vecteur point : historiquePositions) {
            g.fillOval((int) point.getX() - 1, (int) point.getY() - 1, 2, 2);
        }

        // Récupération de la position centrale
        int posX = (int) this.getPosition().getX();
        int posY = (int) this.getPosition().getY();

        // 2. Calcul de l'orientation pour les panneaux solaires
        if (vitesse.getNorme() > 0) {
            // Création d'un vecteur direction unitaire basé sur la vitesse
            Vecteur direction = new Vecteur(vitesse);
            direction.normaliser();

            // Vecteur perpendiculaire pour aligner les panneaux sur les côtés
            Vecteur perpendiculaire = direction.vecteurNormal();

            // Paramètres de distance pour les panneaux
            int ecartPanneau = 8;  // Distance entre le centre et le début du panneau
            int taillePanneau = 12; // Longueur du panneau solaire

            // Dessin des bras/panneaux solaires (Bleu électrique)
            g.setColor(new Color(0, 150, 255));
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(4)); // Panneaux plus épais

            // Panneau Gauche
            int p1x = posX + (int) (perpendiculaire.getX() * ecartPanneau);
            int p1y = posY + (int) (perpendiculaire.getY() * ecartPanneau);
            int p2x = posX + (int) (perpendiculaire.getX() * (ecartPanneau + taillePanneau));
            int p2y = posY + (int) (perpendiculaire.getY() * (ecartPanneau + taillePanneau));
            g2d.drawLine(p1x, p1y, p2x, p2y);

            // Panneau Droit
            int p3x = posX - (int) (perpendiculaire.getX() * ecartPanneau);
            int p3y = posY - (int) (perpendiculaire.getY() * ecartPanneau);
            int p4x = posX - (int) (perpendiculaire.getX() * (ecartPanneau + taillePanneau));
            int p4y = posY - (int) (perpendiculaire.getY() * (ecartPanneau + taillePanneau));
            g2d.drawLine(p3x, p3y, p4x, p4y);
            
            // Remettre l'épaisseur de ligne par défaut pour la suite
            g2d.setStroke(new BasicStroke(1)); 
        }

        // 3. Dessin du corps central du satellite (Rond métallique/doré)
        g.setColor(new Color(200, 200, 200)); // Gris métal
        g.fillOval(posX - 5, posY - 5, 10, 10);
        g.setColor(Color.WHITE); // Petit reflet au centre
        g.fillOval(posX - 2, posY - 2, 4, 4);

        // 4. Vecteur Vitesse (Ligne de direction)
        // Conservé en vert, mais légèrement plus discret pour ne pas cacher le satellite
        g.setColor(new Color(0, 255, 0, 180));
        int x2 = posX + (int) (vitesse.getX() * 5);
        int y2 = posY + (int) (vitesse.getY() * 5);
        g.drawLine(posX, posY, x2, y2);
    }

}
