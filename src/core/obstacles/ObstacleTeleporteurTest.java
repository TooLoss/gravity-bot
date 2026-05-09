package core.obstacles;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import core.Projectile;
import utils.Vecteur;

public class ObstacleTeleporteurTest {

    private ObstacleTeleporteur portailA;
    private ObstacleTeleporteur portailB;

    // portailA : centre (100,100), rayon = distance((100,100),(120,100)) = 20
    // portailB : centre (400,400), rayon = distance((400,400),(420,400)) = 20
    @Before
    public void setUp() {
        portailA = new ObstacleTeleporteur(new Vecteur(100, 100), new Vecteur(120, 100));
        portailB = new ObstacleTeleporteur(new Vecteur(400, 400), new Vecteur(420, 400));
        portailA.lier(portailB);
    }

    // ── Liaison ──────────────────────────────────────────────────────────────

    @Test
    public void lier_rendLaLiaisonReciproque() {
        assertSame(portailB, portailA.getPartenaire());
        assertSame(portailA, portailB.getPartenaire());
    }

    @Test
    public void portailNonLie_estLieFaux() {
        ObstacleTeleporteur seul = new ObstacleTeleporteur(new Vecteur(0, 0), new Vecteur(20, 0));
        assertFalse(seul.estLie());
    }

    @Test(expected = IllegalArgumentException.class)
    public void lier_avecNull_leveException() {
        portailA.lier(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void lier_avecLuiMeme_leveException() {
        portailA.lier(portailA);
    }

    // ── Collision (zone circulaire) ───────────────────────────────────────────

    @Test
    public void projectileAuCentre_estDetecte() {
        Projectile p = new Projectile(new Vecteur(100, 100), new Vecteur(1, 0));
        assertTrue(portailA.estDansObstacle(p));
    }

    @Test
    public void projectileDansLeRayon_estDetecte() {
        // Distance 10 du centre, rayon = 20 → dedans
        Projectile p = new Projectile(new Vecteur(110, 100), new Vecteur(1, 0));
        assertTrue(portailA.estDansObstacle(p));
    }

    @Test
    public void projectileHorsRayon_nonDetecte() {
        Projectile p = new Projectile(new Vecteur(200, 200), new Vecteur(1, 0));
        assertFalse(portailA.estDansObstacle(p));
    }

    // ── Téléportation A → B ──────────────────────────────────────────────────

    @Test
    public void teleportation_deplaceLePprojectileVersPortailB() {
        Projectile p = new Projectile(new Vecteur(100, 100), new Vecteur(1, 0));
        portailA.eventAction(p);

        double distanceDeB = p.getPosition().distance(portailB.getPointA());
        assertTrue("Le projectile doit être proche du portail B", distanceDeB <= 50);
    }

    @Test
    public void teleportation_BversA_fonctionne() {
        Projectile p = new Projectile(new Vecteur(400, 400), new Vecteur(-1, 0));
        portailB.eventAction(p);

        double distanceDeA = p.getPosition().distance(portailA.getPointA());
        assertTrue("Le projectile doit être proche du portail A", distanceDeA <= 50);
    }

    @Test
    public void apresTeleporation_projectileEstHorsDeLaZoneDeSortie() {
        Projectile p = new Projectile(new Vecteur(100, 100), new Vecteur(1, 0));
        portailA.eventAction(p);

        assertFalse("Le projectile ne doit plus être dans la zone du portail de sortie",
                    portailB.estDansObstacle(p));
    }

    // ── Portail sans partenaire ───────────────────────────────────────────────

    @Test
    public void portailSansPartenaire_neBougePasLeProjectile() {
        ObstacleTeleporteur seul = new ObstacleTeleporteur(new Vecteur(0, 0), new Vecteur(20, 0));
        Projectile p = new Projectile(new Vecteur(50, 50), new Vecteur(1, 0));
        seul.eventAction(p);

        assertEquals("Position X ne doit pas changer", 50.0, p.getPosition().getX(), 0.001);
        assertEquals("Position Y ne doit pas changer", 50.0, p.getPosition().getY(), 0.001);
    }

    // ── Rayon défini par pointB ───────────────────────────────────────────────

    @Test
    public void rayonDefiniParPointB_grandeZone() {
        // rayon = 100
        ObstacleTeleporteur grand = new ObstacleTeleporteur(new Vecteur(0, 0), new Vecteur(100, 0));
        ObstacleTeleporteur sortie = new ObstacleTeleporteur(new Vecteur(300, 300), new Vecteur(400, 300));
        grand.lier(sortie);

        // Distance 80 du centre → dedans
        Projectile p = new Projectile(new Vecteur(80, 0), new Vecteur(1, 0));
        assertTrue(grand.estDansObstacle(p));
    }

    @Test
    public void rayonDefiniParPointB_petiteZone() {
        // rayon = 5
        ObstacleTeleporteur petit = new ObstacleTeleporteur(new Vecteur(0, 0), new Vecteur(5, 0));
        ObstacleTeleporteur sortie = new ObstacleTeleporteur(new Vecteur(300, 300), new Vecteur(305, 300));
        petit.lier(sortie);

        // Distance 10 du centre → dehors
        Projectile p = new Projectile(new Vecteur(10, 0), new Vecteur(1, 0));
        assertFalse(petit.estDansObstacle(p));
    }

    // ── copier() ─────────────────────────────────────────────────────────────

    @Test
    public void copier_retourneUnPortailNonLie() {
        ObstacleTeleporteur copie = (ObstacleTeleporteur) portailA.copier();
        assertFalse(copie.estLie());
    }

    @Test
    public void copier_conserveLaPosition() {
        ObstacleTeleporteur copie = (ObstacleTeleporteur) portailA.copier();
        assertEquals(portailA.getPointA().getX(), copie.getPointA().getX(), 0.001);
        assertEquals(portailA.getPointA().getY(), copie.getPointA().getY(), 0.001);
    }

    @Test
    public void copier_conserveLeRayon() {
        ObstacleTeleporteur copie = (ObstacleTeleporteur) portailA.copier();
        assertEquals(portailA.getLongueur(), copie.getLongueur(), 0.001);
    }
}