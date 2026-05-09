package core.obstacles;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import utils.Vecteur;
import core.Projectile;
import core.Obstacle;

public class ObstacleSegmentTest {

    // On utilise notre classe abstraite comme type
    private ObstacleSegment segment;

    @Before
    public void setUp() {
        Vecteur pointA = new Vecteur(0, 0);
        Vecteur pointB = new Vecteur(3, 4); 
        // rectangle classique rectangle de côtés 3, 4, 5
        
        segment = new ObstacleSegment(pointA, pointB) {
            @Override
            public void eventAction(Projectile projectile) {
            }

            @Override
            public Obstacle copier() {
                return null;
            }
        };
    }

    @Test
    public void testGettersPoints() {
        assertEquals(0.0, segment.getPointA().getX(), 0.01);
        assertEquals(0.0, segment.getPointA().getY(), 0.01);
        assertEquals(3.0, segment.getPointB().getX(), 0.01);
        assertEquals(4.0, segment.getPointB().getY(), 0.01);
    }

    @Test
    public void testGetLongueur() {
        // La distance de notre segment doit être exactement 5
        assertEquals("La longueur du segment est incorrecte", 5.0, segment.getLongueur(), 0.01);
    }

    @Test
    public void testGetDirecteur() {
        // Le vecteur directeur (B - A) doit être de (3, 4)
        Vecteur directeur = segment.getDirecteur();
        assertEquals(3.0, directeur.getX(), 0.01);
        assertEquals(4.0, directeur.getY(), 0.01);
    }
}