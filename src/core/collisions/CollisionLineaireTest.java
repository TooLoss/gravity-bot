package core.collisions;

import core.Projectile;
import utils.Vecteur;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de CollisionLineaire.
 *
 * Une ligne est définie par :
 *   - une position (point de départ)
 *   - une direction (vecteur directionnel)
 *   - une épaisseur (tolérance autour de la ligne)
 *
 * Un acteur est "en collision" si sa distance perpendiculaire à la ligne
 * est inférieure à l'épaisseur.
 *
 * ⚠️  BUG À CORRIGER AVANT DE LANCER CES TESTS :
 *     Dans Vecteur.java, le constructeur Vecteur(Vecteur a, Vecteur b) est cassé :
 *
 *     BUGUÉ :
 *         this.x = b.getX() - b.getX();  // b-b = toujours 0 !
 *         this.x = b.getY() - b.getY();  // écrit dans x au lieu de y, et 0 aussi !
 *
 *     CORRIGÉ :
 *         this.x = b.getX() - a.getX();
 *         this.y = b.getY() - a.getY();
 *
 *     Sans cette correction, CollisionLineaire retourne toujours false.
 */
class CollisionLineaireTest {

    private Projectile projectileEn(double x, double y) {
        return new Projectile(new Vecteur(x, y), new Vecteur(0, 0));
    }

    // ── CAS : SUR LA LIGNE (dedans) ───────────────────────────

    @Test
    void surLaLigneHorizontale_estEnCollision() {
        // Ligne horizontale : part de (0,0), direction (1,0), épaisseur=1
        // Projectile en (3, 0) → il est pile sur la ligne → collision
        CollisionLineaire ligne = new CollisionLineaire(
            new Vecteur(0, 0),   // point de départ
            new Vecteur(1, 0),   // direction : vers la droite
            1.0                  // épaisseur
        );
        assertTrue(ligne.estEnCollision(projectileEn(3, 0)));
    }

    @Test
    void prosDeLaLigne_estEnCollision() {
        // Ligne horizontale, épaisseur=2
        // Projectile en (3, 1) → à distance 1 < épaisseur=2 → collision
        CollisionLineaire ligne = new CollisionLineaire(
            new Vecteur(0, 0),
            new Vecteur(1, 0),
            2.0
        );
        assertTrue(ligne.estEnCollision(projectileEn(3, 1)));
    }

    // ── CAS : LOIN DE LA LIGNE (dehors) ───────────────────────

    @Test
    void loinDeLaLigne_pasDedans() {
        // Ligne horizontale, épaisseur=1
        // Projectile en (3, 5) → à distance 5 > épaisseur=1 → pas de collision
        CollisionLineaire ligne = new CollisionLineaire(
            new Vecteur(0, 0),
            new Vecteur(1, 0),
            1.0
        );
        assertFalse(ligne.estEnCollision(projectileEn(3, 5)));
    }

    @Test
    void justeAuDela_pasDedans() {
        // Ligne horizontale, épaisseur=3
        // Projectile en (0, 3.1) → distance ≈ 3.1 > épaisseur=3 → pas de collision
        CollisionLineaire ligne = new CollisionLineaire(
            new Vecteur(0, 0),
            new Vecteur(1, 0),
            3.0
        );
        assertFalse(ligne.estEnCollision(projectileEn(0, 3.1)));
    }
}
