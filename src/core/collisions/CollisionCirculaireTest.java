package core.collisions;

import core.Projectile;
import utils.Vecteur;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de CollisionCirculaire.
 *
 * Un cercle est défini par un centre et un rayon.
 * Un acteur est "dedans" si sa distance au centre est STRICTEMENT inférieure au rayon.
 *
 *         . . .
 *       .       .
 *      .  (5,5)  .   ← rayon = 5
 *       .       .
 *         . . .
 *
 * ⚠️ BUG À CORRIGER AVANT DE LANCER CES TESTS :
 *    La méthode estEnCollision() prend actuellement 2 arguments (Acteur, Acteur)
 *    mais l'interface Collision en déclare seulement 1 (Acteur).
 *    → Changer la signature dans CollisionCirculaire.java :
 *
 *    AVANT :  public boolean estEnCollision(Acteur acteur, Acteur source)
 *    APRÈS :  public boolean estEnCollision(Acteur acteur)
 *
 *    Et remplacer "source.distance(acteur)" par "this.centre.distance(acteur.getPosition())"
 *    car on n'a plus accès à "source" directement.
 */
class CollisionCirculaireTest {

    // Obstacle fictif pour simuler la "source" (le centre du cercle)
    // → on utilise un Projectile immobile positionné au centre
    private Projectile projectileEn(double x, double y) {
        return new Projectile(new Vecteur(x, y), new Vecteur(0, 0));
    }

    // ── CAS : DEDANS ──────────────────────────────────────────

    @Test
    void auCentre_estDedans() {
        // centre=(5,5), rayon=5
        // projectile en (5,5) → distance=0 < 5 → dedans
        CollisionCirculaire cercle = new CollisionCirculaire(new Vecteur(5, 5), 5.0);
        Projectile p = projectileEn(5, 5);
        Projectile centre = projectileEn(5, 5); // le "source" = centre de l'obstacle

        assertTrue(cercle.estEnCollision(p, centre));
    }

    @Test
    void procheduCentre_estDedans() {
        // projectile en (7,5) → distance=2 < rayon=5 → dedans
        CollisionCirculaire cercle = new CollisionCirculaire(new Vecteur(5, 5), 5.0);
        assertTrue(cercle.estEnCollision(projectileEn(7, 5), projectileEn(5, 5)));
    }

    @Test
    void justeAvantLeBord_estDedans() {
        // projectile en (9.9, 5) → distance ≈ 4.9 < rayon=5 → dedans
        CollisionCirculaire cercle = new CollisionCirculaire(new Vecteur(5, 5), 5.0);
        assertTrue(cercle.estEnCollision(projectileEn(9.9, 5), projectileEn(5, 5)));
    }

    @Test
    void diagonale_trianglePythagore_estDedans() {
        // centre=(0,0), rayon=10
        // projectile en (3,4) → distance=sqrt(9+16)=5 < 10 → dedans
        CollisionCirculaire cercle = new CollisionCirculaire(new Vecteur(0, 0), 10.0);
        assertTrue(cercle.estEnCollision(projectileEn(3, 4), projectileEn(0, 0)));
    }

    // ── CAS : DEHORS ──────────────────────────────────────────

    @Test
    void loin_pasDedans() {
        // projectile en (20,20) → très loin du centre (5,5) → dehors
        CollisionCirculaire cercle = new CollisionCirculaire(new Vecteur(5, 5), 5.0);
        assertFalse(cercle.estEnCollision(projectileEn(20, 20), projectileEn(5, 5)));
    }

    @Test
    void justeApresLeBord_pasDedans() {
        // projectile en (10.1, 5) → distance ≈ 5.1 > rayon=5 → dehors
        CollisionCirculaire cercle = new CollisionCirculaire(new Vecteur(5, 5), 5.0);
        assertFalse(cercle.estEnCollision(projectileEn(10.1, 5), projectileEn(5, 5)));
    }

    @Test
    void exactementSurLeBord_pasDedans() {
        // centre=(0,0), rayon=5
        // projectile en (5,0) → distance=5 = rayon → PAS dedans (condition stricte <)
        CollisionCirculaire cercle = new CollisionCirculaire(new Vecteur(0, 0), 5.0);
        assertFalse(cercle.estEnCollision(projectileEn(5, 0), projectileEn(0, 0)));
    }

    @Test
    void diagonale_trianglePythagore_pasDedans() {
        // centre=(0,0), rayon=5
        // projectile en (3,4) → distance=5 = rayon → PAS dedans
        CollisionCirculaire cercle = new CollisionCirculaire(new Vecteur(0, 0), 5.0);
        assertFalse(cercle.estEnCollision(projectileEn(3, 4), projectileEn(0, 0)));
    }
}
