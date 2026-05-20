package core.obstacles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import core.obstacles.ObstacleSegment;
import core.Projectile;
import utils.Vecteur;

public class ObstacleTeleporteurNeon extends ObstacleTeleporteur {

    public static final int prix = 5000;

    public ObstacleTeleporteurNeon(Vecteur pointA, Vecteur pointB, double
            epaisseur, double distance) {
        super(pointA, pointB, epaisseur, distance);
    }


    @Override
    public Obstacle copier() {
        return new ObstacleTeleporteurNeon(
                this.getPosition(),
                this.getPointFinal(), 
                this.getEpaisseur(),
                this.distanceMax);
    }

    @Override
    public void eventAction(Projectile projectile) {
        super.eventAction(projectile);
        Vecteur newVitesse = new Vecteur(projectile.getVitesse());
        newVitesse.setRotation(this.getDirection().getRotation());
        projectile.setVitesse(newVitesse);
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

        // --- 1. AURA EXTÉRIEURE NEON MAGENTA (Grand halo d'énergie) ---
        g2.setColor(new Color(255, 0, 128, 40)); // Rose néon très transparent
        g2.setStroke(new BasicStroke(epaisseurMax * 1.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(xA, yA, xB, yB);
        
        g2.setColor(new Color(255, 0, 128, 100)); // Plus opaque en se rapprochant
        g2.setStroke(new BasicStroke(epaisseurMax * 1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(xA, yA, xB, yB);

        // --- 2. DOUBLE EFFET ÉLECTRIQUE CYAN (L'énergie de confinement) ---
        g2.setColor(new Color(0, 240, 255, 150)); // Bleu Cyan Néon
        g2.setStroke(new BasicStroke(epaisseurMax * 0.7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(xA, yA, xB, yB);

        // Ligne de cœur du néon cyan (effet de brillance blanche/cyan)
        g2.setColor(new Color(220, 255, 255, 220));
        g2.setStroke(new BasicStroke(epaisseurMax * 0.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(xA, yA, xB, yB);

        // --- 3. LA FAILLE : LE VIDE ABSOLU (Ligne noire ultra-nette) ---
        // C'est l'entrée du téléporteur, un fil de néant parfait au milieu de la lumière
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(epaisseurMax * 0.25f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(xA, yA, xB, yB);

        // Restauration du tracé d'origine
        g2.setStroke(ancienneStroke);

        // --- 4. BORNES DE CONFINEMENT CHROME / GLOW ---
        // Extrémité A : Noyau blanc brillant entouré de cyan
        g2.setColor(new Color(0, 240, 255));
        g2.fillOval(xA - 7, yA - 7, 14, 14);
        g2.setColor(Color.WHITE);
        g2.fillOval(xA - 4, yA - 4, 8, 8);

        // Extrémité B : Noyau blanc brillant entouré de magenta
        g2.setColor(new Color(255, 0, 128));
        g2.fillOval(xB - 7, yB - 7, 14, 14);
        g2.setColor(Color.WHITE);
        g2.fillOval(xB - 4, yB - 4, 8, 8);
    }

    @Override
    public int getPrix() {
        return prix;
    }
}
