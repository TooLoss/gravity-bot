package core.collisions;
 
import org.junit.Test;
import static org.junit.Assert.*;
 
import core.Projectile;
import utils.Vecteur;
 
public class CollisionCirculaireTest {
 
    private Projectile projectileEn(double x, double y) {
        return new Projectile(new Vecteur(x, y), new Vecteur(0, 0));
    }
 
    // ── DEDANS ────────────────────────────────────────────────
 
    @Test
    public void auCentre_estDedans() {
        // centre=(5,5), rayon=5, projectile en (5,5) → distance=0 < 5 → dedans
        CollisionCirculaire cercle = new CollisionCirculaire(new Vecteur(5,5), 5.0);
        assertTrue(cercle.estEnCollision(projectileEn(5, 5)));
    }
 
    @Test
    public void procheduCentre_estDedans() {
        // projectile en (7,5) → distance=2 < rayon=5 → dedans
        CollisionCirculaire cercle = new CollisionCirculaire(new Vecteur(5,5), 5.0);
        assertTrue(cercle.estEnCollision(projectileEn(7, 5)));
    }
 
    @Test
    public void diagonal_petitTriangle_estDedans() {
        // centre=(0,0), rayon=10, projectile en (3,4) → distance=5 < 10 → dedans
        CollisionCirculaire cercle = new CollisionCirculaire(new Vecteur(0,0), 10.0);
        assertTrue(cercle.estEnCollision(projectileEn(3, 4)));
    }
 
    // ── DEHORS ────────────────────────────────────────────────
 
    @Test
    public void loin_pasDedans() {
        // projectile en (20,20) → très loin → dehors
        CollisionCirculaire cercle = new CollisionCirculaire(new Vecteur(5,5), 5.0);
        assertFalse(cercle.estEnCollision(projectileEn(20, 20)));
    }
 
    @Test
    public void exactementSurLeBord_pasDedans() {
        // distance = rayon → condition stricte < → PAS dedans
        CollisionCirculaire cercle = new CollisionCirculaire(new Vecteur(0,0), 5.0);
        assertFalse(cercle.estEnCollision(projectileEn(5, 0)));
    }
 
    @Test
    public void diagonal_exactementSurLeBord_pasDedans() {
        // centre=(0,0), rayon=5, projectile en (3,4) → distance=5 = rayon → dehors
        CollisionCirculaire cercle = new CollisionCirculaire(new Vecteur(0,0), 5.0);
        assertFalse(cercle.estEnCollision(projectileEn(3, 4)));
    }
}
