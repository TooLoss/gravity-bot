package core.obstacles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import core.Acteur;
import core.obstacles.Obstacle;
import core.Projectile;
import utils.Vecteur;
import core.collisions.CollisionCirculaire; 
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Obstacle de forme circulaire.
 * Le projectile rebondit physiquement sur sa surface.
 */
public class ObstacleCirculaire extends Obstacle {

    private double rayon;
    
    public ObstacleCirculaire(Vecteur position, double rayon) {
        this.setPosition(position);
        this.rayon = rayon;
        this.setCollision(new CollisionCirculaire(this.getPosition(), rayon));
    }

    @Override
    public void eventAction(Projectile projectile) {
        Vecteur posProjectile = projectile.getPosition();
        Vecteur posObstacle = this.getPosition();
        Vecteur vitesseActuelle = projectile.getVitesse();
        double nx = posProjectile.getX() - posObstacle.getX();
        double ny = posProjectile.getY() - posObstacle.getY();
        double longueurN = Math.sqrt((nx * nx) + (ny * ny));
        if (longueurN != 0) {
            nx = nx / longueurN;
            ny = ny / longueurN;
        }
        double produitScalaire = (vitesseActuelle.getX() * nx) + (vitesseActuelle.getY() * ny);
        double nouvelleVitesseX = vitesseActuelle.getX() - (2 * produitScalaire * nx);
        double nouvelleVitesseY = vitesseActuelle.getY() - (2 * produitScalaire * ny);
        projectile.setVitesse(new Vecteur(nouvelleVitesseX, nouvelleVitesseY));
    }

    @Override
    public void actualiser(double dt) {
        // Abstract
    }

    @Override
    public Obstacle copier() {
        ObstacleCirculaire copy = new ObstacleCirculaire(this.getPosition(), this.rayon);
        return copy;
    }

    @Override
    public void afficher(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = (int)(this.getPosition().getX() - rayon);
        int y = (int)(this.getPosition().getY() - rayon);
        int d = (int)(rayon * 2);

        // Dégradé radial effet 3D
        java.awt.RadialGradientPaint gradient = new java.awt.RadialGradientPaint(
            (float)(this.getPosition().getX() - rayon * 0.3),
            (float)(this.getPosition().getY() - rayon * 0.3),
            (float)(rayon),
            new float[]{0f, 1f},
            new Color[]{new Color(180, 180, 200), new Color(50, 50, 80)}
        );
        g2d.setPaint(gradient);
        g2d.fillOval(x, y, d, d);

        // Contour lumineux
        g2d.setColor(new Color(150, 150, 255, 180));
        g2d.setStroke(new java.awt.BasicStroke(2));
        g2d.drawOval(x, y, d, d);
    }
} 
