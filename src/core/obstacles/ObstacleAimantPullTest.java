package core.obstacles;

import org.junit.Test;
import static org.junit.Assert.*;

import core.obstacles.ObstacleAimantPull;
import core.Projectile;
import utils.Vecteur;

public class ObstacleAimantPullTest {

    @Test
    public void testAttractionProjectile() {
        // Initialisation de l'aimant
        ObstacleAimantPull aimant = new ObstacleAimantPull();

        // On s'assure qu'il est bien placé en (0,0) pour faciliter les calculs
        aimant.setPosition(new Vecteur(0, 0));

        // Un projectile placé à droite de l'aimant, en (4, 0).
        // Il est immobile au départ (0,0).
        // (Il est à une distance de 4, donc dans le rayon d'action de 5 de l'aimant)
        Projectile projectile = new Projectile(new Vecteur(4, 0), new Vecteur(0, 0));

        // Action : L'aimant agit sur le projectile
        aimant.eventAction(projectile);

        // Vérification
        // L'aimant est en (0,0), le projectile en (4,0).
        // Le vecteur pour aller du projectile à l'aimant est (-4, 0).
        // Normalisé, cela donne (-1, 0).
        // Multiplié par la puissance (1.0), cela donne une force de (-1, 0).
        // Ta classe Projectile va ajouter cette force, puis normaliser la vitesse totale
        // et la multiplier par vitesseSatellite (1.0).

        Vecteur nouvelleVitesse = projectile.getVitesse();

        // Le projectile doit avoir été attiré vers la gauche
        assertEquals("Le projectile doit être attiré vers l'aimant en X (-1.0)", -1.0, nouvelleVitesse.getX(), 0.001);
        assertEquals("La composante Y doit rester nulle", 0.0, nouvelleVitesse.getY(), 0.001);
    }
}
