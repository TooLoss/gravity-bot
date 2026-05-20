package core.obstacles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import core.obstacles.ObstacleSegment;
import core.Projectile;
import utils.Vecteur;

public class ObstacleTeleporteur extends ObstacleSegment {

    public static final int prix = 3000;
    private boolean used;

    public ObstacleTeleporteur(Vecteur pointA, Vecteur pointB, double
            epaisseur, double distance) {
        super(pointA, pointB, epaisseur, distance);
        this.used = false;
    }

    @Override
    public void eventAction(Projectile projectile) {
        if (!used) {
            Vecteur end = new Vecteur(this.getPointFinal());
            Vecteur direction = new Vecteur(this.getDirection());
            direction.normaliser();
            direction.multiplier(this.getEpaisseur());
            end.ajouter(direction);
            projectile.setPosition(end);
            this.used = true;

            javax.swing.Timer timer = new javax.swing.Timer(2000, e -> {
                this.used = false;
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    @Override
    public void actualiser(double dt) {
    }

    @Override
    public Obstacle copier() {
        return new ObstacleTeleporteur(
                this.getPosition(),
                this.getPointFinal(), 
                this.getEpaisseur(),
                this.distanceMax);
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

        // --- 1. RAYONNEMENT DE FUITE (Lueur violette très terne et hachée) ---
        // Simule une distorsion magnétique instable de mauvaise qualité autour du téléporteur
        g2.setColor(new Color(90, 40, 120, 100));
        float[] patternDistorsion = {15.0f, 10.0f, 5.0f, 15.0f};
        g2.setStroke(new BasicStroke(
                epaisseurMax, 
                BasicStroke.CAP_BUTT, 
                BasicStroke.JOIN_MITER, 
                1.0f, 
                patternDistorsion, 
                0.0f));
        g2.drawLine(xA, yA, xB, yB);

        // --- 2. CONTOUR GRIS "MÉTAL SOUFFRANT" ---
        // Donne une délimitation au tube de téléportation, un peu asymétrique
        g2.setColor(new Color(60, 60, 65, 200));
        float[] patternMetal = {30.0f, 15.0f};
        g2.setStroke(new BasicStroke(
                epaisseurMax * 0.75f, 
                BasicStroke.CAP_BUTT, 
                BasicStroke.JOIN_ROUND, 
                1.0f, 
                patternMetal, 
                0.0f));
        g2.drawLine(xA, yA, xB, yB);

        // --- 3. LE CŒUR : LE VIDE ABSOLU (Noir opaque version cheap) ---
        // Une fente noire rectiligne et nette, sans dégradé, comme une coupure dans l'espace
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(epaisseurMax * 0.4f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        g2.drawLine(xA, yA, xB, yB);

        // Restauration du tracé d'origine
        g2.setStroke(ancienneStroke);

        // --- 4. POIGNÉES "GÉNÉRATEURS BRUTALISTES" ---
        // Des carrés gris foncés boulonnés, pour faire pièce de récupération industrielle
        g2.setColor(new Color(50, 50, 50));
        g2.fillRect(xA - 6, yA - 6, 12, 12);
        g2.fillRect(xB - 6, yB - 6, 12, 12);
        
        // Un petit point rouge central sur les bornes pour faire "générateur sous tension"
        g2.setColor(Color.RED);
        g2.fillRect(xA - 2, yA - 2, 4, 4);
        g2.fillRect(xB - 2, yB - 2, 4, 4);
    }

    @Override
    public int getPrix() {
        return prix;
    }
}
