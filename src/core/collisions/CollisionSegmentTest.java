package core.collisions;

import org.junit.Test;
import static org.junit.Assert.*;

import core.Projectile;
import utils.Vecteur;

public class CollisionLineaireTest {

    @Test
    public void testCollisionDansLaZone() {
        CollisionLineaire rayon = new CollisionLineaire(
            new Vecteur(0, 0),
            new Vecteur(1, 0),
            10.0,
            2.0
        );

        Projectile projectile = new Projectile(new Vecteur(5, 1), new Vecteur(0, 0));

        // CORRECTION : On a enlevé le ", null"
        assertTrue("Le projectile (5,1) devrait être touché par le rayon.",
                   rayon.estEnCollision(projectile));
    }

    @Test
    public void testCollisionHorsPortee() {
        CollisionLineaire rayon = new CollisionLineaire(new Vecteur(0, 0), new Vecteur(1, 0), 10.0, 2.0);
        Projectile projectile = new Projectile(new Vecteur(15, 0), new Vecteur(0, 0));

        assertFalse("Le projectile (15,0) est trop loin, il ne devrait pas être touché.",
                    rayon.estEnCollision(projectile));
    }

    @Test
    public void testCollisionHorsEpaisseur() {
        CollisionLineaire rayon = new CollisionLineaire(new Vecteur(0, 0), new Vecteur(1, 0), 10.0, 2.0);
        Projectile projectile = new Projectile(new Vecteur(5, 5), new Vecteur(0, 0));

        assertFalse("Le projectile (5,5) est trop haut, il ne devrait pas être touché.",
                    rayon.estEnCollision(projectile));
    }

    @Test
    public void testCollisionDerriereLigne() {
        CollisionLineaire rayon = new CollisionLineaire(new Vecteur(0, 0), new Vecteur(1, 0), 10.0, 2.0);
        Projectile projectile = new Projectile(new Vecteur(-2, 0), new Vecteur(0, 0));

        assertFalse("Le projectile (-2,0) est derrière le rayon, il ne devrait pas être touché.",
                    rayon.estEnCollision(projectile));
    }
}