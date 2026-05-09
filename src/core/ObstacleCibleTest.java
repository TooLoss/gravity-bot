package core.obstacles;

import static org.junit.Assert.*;
import org.junit.Test;
import core.Projectile;
import utils.Vecteur;
import core.collisions.CollisionCirculaire;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ObstacleCibleTest {

    private static final double EPSILON = 1e-10;

    @Test
    public void testInitialisation() {
        Vecteur pos = new Vecteur(50, 50);
        ObstacleCible cible = new ObstacleCible(pos);

        assertEquals(50.0, cible.getPosition().getX(), EPSILON);
        assertEquals(50.0, cible.getPosition().getY(), EPSILON);

        assertTrue("La collision doit être de type CollisionCirculaire", 
                    cible.getCollision() instanceof CollisionCirculaire);
        
        CollisionCirculaire col = (CollisionCirculaire) cible.getCollision();
        assertEquals(10.0, col.getRayon(), EPSILON);
    }

    @Test
    public void testEventAction() {
        // Redirection du flux de sortie pour lire ce que System.out.println écrit
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ObstacleCible cible = new ObstacleCible(new Vecteur(0, 0));
        Projectile proj = new Projectile(new Vecteur(0, 0), new Vecteur(0, 0));

        cible.eventAction(proj);

        assertEquals("WIIIIN", outContent.toString().trim());

        // Reset du flux de sortie vers la console normale
        System.setOut(System.out);
    }


    @Test
    public void testCopier() {
        Vecteur posOrigine = new Vecteur(100, 200);
        ObstacleCible cibleOrigine = new ObstacleCible(posOrigine);
        
        ObstacleCible cibleCopie = (ObstacleCible) cibleOrigine.copier();

        // Les positions doivent être identiques en valeur
        assertEquals(cibleOrigine.getPosition().getX(), cibleCopie.getPosition().getX(), EPSILON);
        assertEquals(cibleOrigine.getPosition().getY(), cibleCopie.getPosition().getY(), EPSILON);

        // Mais ce doivent être deux instances d'objets différentes 
        assertNotSame(cibleOrigine, cibleCopie);
        assertNotSame(cibleOrigine.getPosition(), cibleCopie.getPosition());
    }
}