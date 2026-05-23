package core.obstacles;

import java.awt.Graphics;
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
    
    private static BufferedImage image;
    static {
        try {
            image = ImageIO.read(new File("src/interface/moon.png"));
        } catch (IOException e) {
            System.err.println("Impossible de charger moon.png: " + e.getMessage());
        }
    }

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
        int diametre = (int)(rayon * 2);
        int x = (int)(this.getPosition().getX() - rayon);
        int y = (int)(this.getPosition().getY() - rayon);
        
        if (image != null) {
            g.drawImage(image, x, y, diametre, diametre, null);
        } else {
            // Si l'image n'a pas pu être chargée, on garde l'ancien rendu
            g.setColor(Color.GRAY);
            g.fillOval(x, y, diametre, diametre);
        }
    }
}
