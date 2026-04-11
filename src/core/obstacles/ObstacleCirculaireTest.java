package core.obstacles;

import org.junit.Test;
import static org.junit.Assert.*;

import core.obstacles.ObstacleCirculaire;
import core.Projectile;
import utils.Vecteur;

public class ObstacleCirculaireTest {

    @Test
    public void testRebondHorizontal() {
        // Initialisation : Une planète au centre (0,0) avec un rayon de 10
        ObstacleCirculaire planete = new ObstacleCirculaire(new Vecteur(0, 0), 10.0);

        // Un projectile situé sur le bord droit de la planète (10, 0)
        // Il se dirige vers la gauche (vitesse X = -5, Y = 0)
        Projectile projectile = new Projectile(new Vecteur(10, 0), new Vecteur(-5, 0));

        // Action : Le projectile heurte la planète
        planete.eventAction(projectile);

        // Vérification : Le projectile doit rebondir et repartir vers la droite (X = 5, Y = 0)
        Vecteur nouvelleVitesse = projectile.getVitesse();

        // assertEquals(message_erreur, valeur_attendue, valeur_réelle, marge_erreur)
        assertEquals("La vitesse en X devrait être inversée (rebond)", 5.0, nouvelleVitesse.getX(), 0.001);
        assertEquals("La vitesse en Y ne devrait pas changer", 0.0, nouvelleVitesse.getY(), 0.001);
    }

    @Test
    public void testRebondVertical() {
        // Initialisation
        ObstacleCirculaire planete = new ObstacleCirculaire(new Vecteur(0, 0), 10.0);

        // Un projectile situé sur le pôle Nord de la planète (0, 10)
        // Il "tombe" vers le bas (vitesse X = 0, Y = -3)
        Projectile projectile = new Projectile(new Vecteur(0, 10), new Vecteur(0, -3));

        // Action
        planete.eventAction(projectile);

        // Vérification : Il doit rebondir vers le haut (X = 0, Y = 3)
        Vecteur nouvelleVitesse = projectile.getVitesse();
        assertEquals("La vitesse en X ne devrait pas changer", 0.0, nouvelleVitesse.getX(), 0.001);
        assertEquals("La vitesse en Y devrait être inversée (rebond)", 3.0, nouvelleVitesse.getY(), 0.001);
    }
}