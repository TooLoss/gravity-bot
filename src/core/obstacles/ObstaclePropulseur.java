package core.obstacles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import core.obstacles.ObstacleSegment;
import core.Projectile;
import utils.Vecteur;

public class ObstaclePropulseur extends ObstacleSegment {

    protected double forceDeviation;
    public static final int prix = 1000;

    public ObstaclePropulseur(Vecteur pointA, Vecteur pointB, double force, double epaisseur, double distanceMax) {
        super(pointA, pointB, epaisseur, distanceMax);
        this.forceDeviation = force;
    }

    @Override
    public void eventAction(Projectile projectile) {
        Vecteur vitesse = projectile.getVitesse();

        Vecteur direction = new Vecteur(this.getDirection());
        direction.normaliser();

        double vitesseNorme = vitesse.getNorme();
        direction.multiplier(vitesseNorme);

        double x = (vitesse.getX() * (1.0 - forceDeviation)) + (direction.getX() * forceDeviation);
        double y = (vitesse.getY() * (1.0 - forceDeviation)) + (direction.getY() * forceDeviation);

        Vecteur newVitesse = new Vecteur(x, y);
        projectile.setVitesse(newVitesse);
    }

    @Override
    public void actualiser(double dt) {

    }

    @Override
    public Obstacle copier() {
        System.out.println(this.getDirection());
        System.out.println(this.getPosition());
        ObstaclePropulseur copy = new ObstaclePropulseur(
                this.getPosition(),
                this.getPointFinal(),
                this.forceDeviation,
                this.getEpaisseur(),
                this.distanceMax);
        return copy;
    }

    @Override
    public void afficher(Graphics g) {
        /* Généré par IA */
        Graphics2D g2 = (Graphics2D) g;

        int xA = (int) this.getPosition().getX();
        int yA = (int) this.getPosition().getY();
        int xB = (int) this.getPointFinal().getX();
        int yB = (int) this.getPointFinal().getY();

        float epaisseurMax = (float) this.getEpaisseur();
        java.awt.Stroke ancienneStroke = g2.getStroke();

        // Pour lisser les contours des néons (Antialiasing)
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        // --- 1. AURA EXTÉRIEURE VIOLETTE (Grand halo de confinement) ---
        g2.setColor(new Color(150, 25, 215, 35)); // Violet d'origine très transparent
        g2.setStroke(new BasicStroke(epaisseurMax * 1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(xA, yA, xB, yB);

        g2.setColor(new Color(170, 40, 240, 90)); // Violet plus électrique en se rapprochant
        g2.setStroke(new BasicStroke(epaisseurMax * 1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(xA, yA, xB, yB);

        // --- 2. CŒUR ÉNERGÉTIQUE MAGENTA-VIOLET (Le rail conducteur) ---
        g2.setColor(new Color(220, 50, 255, 160)); // Violet/Magenta brillant
        g2.setStroke(new BasicStroke(epaisseurMax * 0.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(xA, yA, xB, yB);

        // --- 3. FLUX DE PROPULSION LUMINEUX (Effet haché directionnel) ---
        // Tirets blancs/mauves superposés pour donner l'illusion d'un déplacement de particules
        g2.setColor(new Color(245, 220, 255, 230));
        float[] patternPropulsion = {12.0f, 18.0f, 4.0f, 12.0f}; // Pattern asymétrique de flux
        g2.setStroke(new BasicStroke(
                    epaisseurMax * 0.25f, 
                    BasicStroke.CAP_ROUND, 
                    BasicStroke.JOIN_ROUND, 
                    1.0f, 
                    patternPropulsion, 
                    0.0f));
        g2.drawLine(xA, yA, xB, yB);

        // Restauration du tracé d'origine
        g2.setStroke(ancienneStroke);

        // --- 4. BORNES VECTORIELLES VIOLETTES / GLOW ---
        // Extrémité A (Entrée / Base du propulseur) : Noyau stable de chargement
        g2.setColor(new Color(150, 25, 215));
        g2.fillOval(xA - 7, yA - 7, 14, 14);
        g2.setColor(Color.WHITE);
        g2.fillOval(xA - 4, yA - 4, 8, 8);

        // Extrémité B (Sortie / Direction du boost)
        // On dessine une flèche polygonale dynamique à l'extrémité B dans le même ton violet
        g2.setColor(new Color(200, 30, 255));

        double dx = xB - xA;
        double dy = yB - yA;
        double longueur = Math.sqrt(dx * dx + dy * dy);

        if (longueur > 0) {
            double uX = dx / longueur; // Vecteur unitaire directionnel
            double uY = dy / longueur;
            double pX = -uY;           // Vecteur perpendiculaire
            double pY = uX;

            // Les 3 sommets du triangle pointant vers la sortie
            int size = 10;
            int[] xPoints = {
                xB + (int)(uX * size),       
                xB - (int)(uX * size - pX * size), 
                xB - (int)(uX * size + pX * size)  
            };
            int[] yPoints = {
                yB + (int)(uY * size),
                yB - (int)(uY * size - pY * size),
                yB - (int)(uY * size + pY * size)
            };
            g2.fillPolygon(xPoints, yPoints, 3);

            // Petit point blanc au centre de la flèche pour préserver le look néon
            g2.setColor(Color.WHITE);
            g2.fillOval(xB - 3, yB - 3, 6, 6);
        } else {
            g2.fillOval(xB - 7, yB - 7, 14, 14);
        }
    }

    @Override
    public int getPrix() {
        return this.prix;
    }

}
