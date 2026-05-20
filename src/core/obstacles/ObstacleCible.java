package core.obstacles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Color;
import utils.Vecteur;
import core.obstacles.Obstacle;
import core.collisions.*;
import core.Projectile;

public class ObstacleCible extends Obstacle {

    private static final double rayon = 10.f;

    public ObstacleCible(Vecteur position) {
        super(position);
        this.setCollision(new CollisionCirculaire(this.getPosition(), rayon));
    }

    @Override
    public void eventAction(Projectile projectile) {
        System.out.println("WIIIIN");
    }

    @Override
    public void actualiser(double dt) {
        // Acteur class abstract
        // TODO à enlever c'est top chiant rapidement
    }

    @Override
    public Obstacle copier() {
        ObstacleCible copy = new ObstacleCible(new Vecteur(this.getPosition()));
        return copy;
    }

    @Override
    public void afficher(Graphics g) {
        int posX = (int) this.getPosition().getX();
        int posY = (int) this.getPosition().getY();
        
        // --- COEFFS DE TAILLE AUGMENTÉS ---
        // On multiplie l'échelle globale pour obtenir une structure beaucoup plus imposante
        int longueurAxe = (int) (rayon * 2.6); 
        int hauteurPanneau = (int) (rayon * 1.8);
        int largeurPanneau = (int) (rayon * 0.55);
        int epaisseurAxe = Math.max(4, (int) (rayon * 0.1));

        Graphics2D g2d = (Graphics2D) g;
        // Activation de l'anti-aliasing pour un rendu propre malgré la grande taille
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. La structure / Mât central massif (Gris métallique)
        g2d.setColor(new Color(120, 120, 120));
        g2d.setStroke(new BasicStroke(epaisseurAxe));
        g2d.drawLine(posX - longueurAxe / 2, posY, posX + longueurAxe / 2, posY);

        // 2. Les Modules Centraux Rectangulaires (Cœur de la station agrandi)
        // Module principal de commande
        g2d.setColor(new Color(180, 180, 180)); 
        g2d.fillRect(posX - (int)(rayon * 0.6), posY - (int)(rayon * 0.3), (int)(rayon * 1.2), (int)(rayon * 0.6));
        
        // Blocs de laboratoires verticaux emboîtés
        g2d.setColor(new Color(205, 205, 205)); 
        g2d.fillRect(posX - (int)(rayon * 0.3), posY - (int)(rayon * 0.45), (int)(rayon * 0.2), (int)(rayon * 0.9));
        g2d.fillRect(posX + (int)(rayon * 0.1), posY - (int)(rayon * 0.45), (int)(rayon * 0.2), (int)(rayon * 0.9));
        
        // Modules de jonction d'extrémité
        g2d.setColor(new Color(225, 225, 225)); 
        g2d.fillRect(posX - (int)(rayon * 0.8), posY - (int)(rayon * 0.2), (int)(rayon * 0.2), (int)(rayon * 0.4));
        g2d.fillRect(posX + (int)(rayon * 0.6), posY - (int)(rayon * 0.2), (int)(rayon * 0.2), (int)(rayon * 0.4));
        
        // 3. Les immenses panneaux solaires (Or/Cuivre avec quadrillage renforcé)
        Color couleurPanneau = new Color(200, 110, 20); 
        Color bordurePanneau = new Color(90, 50, 10);
        
        // Espacement proportionnel des 4 grands blocs de panneaux
        int espaceInterieur = (int)(rayon * 0.1);
        int[] positionsX = {
            posX - longueurAxe / 2,                                  // Extrême gauche
            posX - longueurAxe / 2 + largeurPanneau + espaceInterieur, // Gauche intérieur
            posX + longueurAxe / 2 - largeurPanneau,                  // Extrême droite
            posX + longueurAxe / 2 - 2 * largeurPanneau - espaceInterieur // Droite intérieur
        };

        for (int x : positionsX) {
            int yHaut = posY - hauteurPanneau / 2;
            
            // Fond du panneau rectangulaire
            g2d.setColor(couleurPanneau);
            g2d.fillRect(x, yHaut, largeurPanneau, hauteurPanneau);
            
            // Bordure épaissie pour la grande taille
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(bordurePanneau);
            g2d.drawRect(x, yHaut, largeurPanneau, hauteurPanneau);
            
            // Grille interne de cellules solaires (plus visible)
            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(new Color(40, 25, 5, 120));
            g2d.drawLine(x, posY, x + largeurPanneau, posY); // Ligne horizontale centrale
            
            int numDivisions = 6; // Plus de divisions car les panneaux sont plus grands
            int offsetStep = hauteurPanneau / (numDivisions * 2);
            for (int i = 1; i < numDivisions; i++) {
                int offset = offsetStep * i;
                g2d.drawLine(x, posY - offset, x + largeurPanneau, posY - offset);
                g2d.drawLine(x, posY + offset, x + largeurPanneau, posY + offset);
            }
        }

        // 4. Les radiateurs thermiques (Structures blanches rectangulaires)
        g2d.setColor(new Color(235, 235, 235)); 
        int hauteurRadiateur = (int)(hauteurPanneau * 0.45);
        int largeurRadiateur = (int)(rayon * 0.08);
        
        g2d.fillRect(posX - (int)(rayon * 0.12), posY - hauteurRadiateur / 2, largeurRadiateur, hauteurRadiateur);
        g2d.fillRect(posX + (int)(rayon * 0.04), posY - hauteurRadiateur / 2, largeurRadiateur, hauteurRadiateur);
    }
}
