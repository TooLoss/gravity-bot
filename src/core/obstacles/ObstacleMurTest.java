package core.obstacles;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import utils.Vecteur;
import core.Projectile;

public class ObstacleMurTest {

    private ObstacleMur mur;
    private Projectile projectile;

    @Before
    public void setUp() {
        // On crée un mur rectangulaire qui va du coin (100, 100) au coin (200, 200)
        Vecteur pointA = new Vecteur(100, 100);
        Vecteur pointB = new Vecteur(200, 200);
        mur = new ObstacleMur(pointA, pointB);
    }

    @Test
    public void testProjectileHorsDuMur() {
        // Projectile loin du mur (en 0,0)
        projectile = new Projectile(new Vecteur(0, 0), new Vecteur(1, 1));
        
        assertFalse("Le projectile ne devrait pas toucher le mur", mur.estDansObstacle(projectile));
    }

    @Test
    public void testProjectileDansLeMur() {
        // Projectile en plein milieu du rectangle (en 150, 150)
        projectile = new Projectile(new Vecteur(150, 150), new Vecteur(1, 1));
        
        assertTrue("Le projectile devrait être détecté dans le mur", mur.estDansObstacle(projectile));
    }

    @Test
    public void testEventActionMur() {
        // On simule un projectile qui tape le bord GAUCHE du mur.
        // Position: (100, 150). Vitesse: va vers la droite (+10) et vers le bas (+5)
        projectile = new Projectile(new Vecteur(100, 150), new Vecteur(10, 5));
        
        // On déclenche le rebond
        mur.eventAction(projectile);
        
        // Puisqu'il tape le côté gauche, sa vitesse horizontale (X) doit s'inverser et devenir -10.
        assertEquals(-10.0, projectile.vitesse.getX(), 0.01);
        
        // Sa vitesse verticale (Y) ne doit pas changer, elle reste à 5.
        assertEquals(5.0, projectile.vitesse.getY(), 0.01); 
    }
}