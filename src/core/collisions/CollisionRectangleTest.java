package core.collisions;

// On importe les classes dont on a besoin
import core.Projectile;
import utils.Vecteur;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de CollisionRectangle.
 *
 * Un rectangle est défini par 2 coins opposés : A et B.
 *
 *   A(2,2) ──────────┐
 *          │  dedans  │
 *          └────────── B(8,8)
 *
 * On crée un projectile (satellite) et on vérifie s'il est dedans ou dehors.
 */
class CollisionRectangleTest {

    // Méthode utilitaire : crée un Projectile à la position (x, y)
    private Projectile projectileEn(double x, double y) {
        return new Projectile(new Vecteur(x, y), new Vecteur(0, 0));
    }

    // ── CAS : DEDANS ──────────────────────────────────────────

    @Test
    void auCentre_estDedans() {
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(2,2), new Vecteur(8,8));
        // (5,5) est bien au milieu du rectangle
        assertTrue(rect.estEnCollision(projectileEn(5, 5)));
    }

    @Test
    void surLeBordGauche_estDedans() {
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(2,2), new Vecteur(8,8));
        // x=2 est exactement sur le bord → doit être inclus
        assertTrue(rect.estEnCollision(projectileEn(2, 5)));
    }

    @Test
    void surLeBordDroit_estDedans() {
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(2,2), new Vecteur(8,8));
        // x=8 est exactement sur le bord → doit être inclus
        assertTrue(rect.estEnCollision(projectileEn(8, 5)));
    }

    @Test
    void surUnCoin_estDedans() {
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(2,2), new Vecteur(8,8));
        // Le coin A lui-même doit être inclus
        assertTrue(rect.estEnCollision(projectileEn(2, 2)));
    }

    // ── CAS : DEHORS ──────────────────────────────────────────

    @Test
    void tropAGauche_pasDedans() {
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(2,2), new Vecteur(8,8));
        // x=1 est avant la limite gauche x=2
        assertFalse(rect.estEnCollision(projectileEn(1, 5)));
    }

    @Test
    void tropADroite_pasDedans() {
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(2,2), new Vecteur(8,8));
        // x=9 dépasse la limite droite x=8
        assertFalse(rect.estEnCollision(projectileEn(9, 5)));
    }

    @Test
    void tropEnBas_pasDedans() {
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(2,2), new Vecteur(8,8));
        // y=1 est avant la limite basse y=2
        assertFalse(rect.estEnCollision(projectileEn(5, 1)));
    }

    @Test
    void tropEnHaut_pasDedans() {
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(2,2), new Vecteur(8,8));
        // y=9 dépasse la limite haute y=8
        assertFalse(rect.estEnCollision(projectileEn(5, 9)));
    }

    @Test
    void completementEnDehors_pasDedans() {
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(2,2), new Vecteur(8,8));
        // (0,0) est loin du rectangle
        assertFalse(rect.estEnCollision(projectileEn(0, 0)));
    }

    // ── CAS SPÉCIAL : coins donnés dans l'ordre inverse ───────

    @Test
    void coinsInverses_fonctionne() {
        // On donne B en premier et A en second → doit quand même marcher
        // car CollisionRectangle utilise Math.min/max
        CollisionRectangle rect = new CollisionRectangle(new Vecteur(8,8), new Vecteur(2,2));
        assertTrue(rect.estEnCollision(projectileEn(5, 5)));
    }
}
