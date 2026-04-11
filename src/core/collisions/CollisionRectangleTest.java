package core.collisions;

import org.junit.Test;
import static org.junit.Assert.*;

import core.Projectile;
import utils.Vecteur;

public class CollisionRectangleTest {

    // Méthode utilitaire : crée un Projectile à la position (x, y)
    private Projectile projectileEn(double x, double y) {
        return new Projectile(new Vecteur(x, y), new Vecteur(0, 0));
    }

    // ── DEDANS ────────────────────────────────────────────────

    @Test
    public void auCentre_estDedans() {
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(2,2), new Vecteur(8,8));
        assertTrue(rect.estEnCollision(projectileEn(5, 5)));
    }

    @Test
    public void surLeBordGauche_estDedans() {
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(2,2), new Vecteur(8,8));
        assertTrue(rect.estEnCollision(projectileEn(2, 5)));
    }

    @Test
    public void surLeBordDroit_estDedans() {
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(2,2), new Vecteur(8,8));
        assertTrue(rect.estEnCollision(projectileEn(8, 5)));
    }

    @Test
    public void surUnCoin_estDedans() {
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(2,2), new Vecteur(8,8));
        assertTrue(rect.estEnCollision(projectileEn(2, 2)));
    }

    // ── DEHORS ────────────────────────────────────────────────

    @Test
    public void tropAGauche_pasDedans() {
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(2,2), new Vecteur(8,8));
        assertFalse(rect.estEnCollision(projectileEn(1, 5)));
    }

    @Test
    public void tropADroite_pasDedans() {
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(2,2), new Vecteur(8,8));
        assertFalse(rect.estEnCollision(projectileEn(9, 5)));
    }

    @Test
    public void tropEnBas_pasDedans() {
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(2,2), new Vecteur(8,8));
        assertFalse(rect.estEnCollision(projectileEn(5, 1)));
    }

    @Test
    public void tropEnHaut_pasDedans() {
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(2,2), new Vecteur(8,8));
        assertFalse(rect.estEnCollision(projectileEn(5, 9)));
    }

    @Test
    public void completementEnDehors_pasDedans() {
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(2,2), new Vecteur(8,8));
        assertFalse(rect.estEnCollision(projectileEn(0, 0)));
    }

    // ── CAS SPÉCIAL : coins dans l'ordre inverse ──────────────

    @Test
    public void coinsInverses_fonctionne() {
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(8,8), new Vecteur(2,2));
        assertTrue(rect.estEnCollision(projectileEn(5, 5)));
    }
}
