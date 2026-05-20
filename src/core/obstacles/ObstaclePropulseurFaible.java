package core.obstacles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import core.obstacles.ObstacleSegment;
import core.Projectile;
import utils.Vecteur;

public class ObstaclePropulseurFaible extends ObstaclePropulseur {

    public static final int prix = 500;

    public ObstaclePropulseurFaible(Vecteur pointA, Vecteur pointB, double force, double epaisseur, double distanceMax) {
        super(pointA, pointB, force, epaisseur, distanceMax);
    }

    @Override
    public Obstacle copier() {
        System.out.println(this.getDirection());
        System.out.println(this.getPosition());
        ObstaclePropulseurFaible copy = new ObstaclePropulseurFaible(
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

        double epaisseurMax = this.getEpaisseur(); 
        java.awt.Stroke ancienneStroke = g2.getStroke();

        // 1. COULEUR CHEAP : Un violet délavé, grisâtre et fatigué
        Color violetCheap = new Color(110, 60, 140, 180);
        g2.setColor(violetCheap);

        // 2. TRACÉ DES BORDURES EXTÉRIEURES (On ne remplit pas le centre)
        // On dessine juste deux lignes fines aux extrémités de l'épaisseur pour simuler un tube en plastique vide
        float rayonEpaisseur = (float) (epaisseurMax / 2.0);
        g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));

        // Calcul de la perpendiculaire pour décaler les lignes de bordure
        double dx = xB - xA;
        double dy = yB - yA;
        double longueur = Math.sqrt(dx * dx + dy * dy);

        if (longueur > 0) {
            double px = (-dy / longueur) * rayonEpaisseur;
            double py = (dx / longueur) * rayonEpaisseur;

            // Bordure gauche du tube
            g2.drawLine((int)(xA + px), (int)(yA + py), (int)(xB + px), (int)(yB + py));
            // Bordure droite du tube
            g2.drawLine((int)(xA - px), (int)(yA - py), (int)(xB - px), (int)(yB - py));
        }

        // 3. LE CŒUR DÉFECTUEUX : Une ligne blanche pointillée (effet grésillement / faux contact)
        g2.setColor(new Color(240, 220, 255)); // Un blanc un peu sale/jauni

        // Pattern de pointillés : 10px allumé, 15px éteint, 2px allumé, 20px éteint
        float[] patternGresillement = {10.0f, 15.0f, 2.0f, 20.0f};
        BasicStroke pointille = new BasicStroke(
                2.0f, 
                BasicStroke.CAP_BUTT, 
                BasicStroke.JOIN_MITER, 
                1.0f, 
                patternGresillement, 
                0.0f
                );

        g2.setStroke(pointille);
        g2.drawLine(xA, yA, xB, yB);

        // Restauration de la stroke d'origine
        g2.setStroke(ancienneStroke);

        // 4. POIGNÉES "RÉCUP" : Des ronds grossiers gris au lieu de jolies poignées colorées
        g2.setColor(Color.GRAY);
        g2.drawOval(xA - 4, yA - 4, 8, 8);
        g2.drawOval(xB - 4, yB - 4, 8, 8);
    }

    @Override
    public int getPrix() {
        return this.prix;
    }

}
