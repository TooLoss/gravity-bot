package core.obstacles;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import core.Projectile;
import utils.Vecteur;

public class ObstacleTeleporteurTest {

    private ObstacleTeleporteur portailA;
    private ObstacleTeleporteur portailB;

    @Before
    public void setUp() {
        portailA = new ObstacleTeleporteur(new Vecteur(100, 100));
        portailB = new ObstacleTeleporteur(new Vecteur(400, 400));
        portailA.lier(portailB);
    }

    // ── Liaison ──────────────────────────────────────────────────────────────

    @Test
    public void lier_rendLaLiaisonReciproque() {
        // lier() sur A doit aussi lier B vers A
        assertSame("portailA doit pointer vers portailB", portailB, portailA.getPartenaire());
        assertSame("portailB doit pointer vers portailA", portailA, portailB.getPartenaire());
    }

    @Test
    public void portailNonLie_estLieFaux() {
        ObstacleTeleporteur seul = new ObstacleTeleporteur(new Vecteur(0, 0));
        assertFalse("Un portail sans partenaire ne doit pas être lié", seul.estLie());
    }

    @Test(expected = IllegalArgumentException.class)
    public void lier_avecNull_leveException() {
        portailA.lier(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void lier_avecLuiMeme_leveException() {
        portailA.lier(portailA);
    }

    // ── Téléportation A → B ──────────────────────────────────────────────────

    @Test
    public void teleportation_deplaceLePprojectileVersLePartenaire() {
        // Projectile entrant dans le portail A, se dirigeant vers la droite
        Projectile projectile = new Projectile(new Vecteur(100, 100), new Vecteur(1, 0));

        portailA.eventAction(projectile);

        Vecteur posApresTeleportation = projectile.getPosition();

        // Il doit se retrouver autour du portail B (400, 400)
        // On vérifie qu'il est "loin" du portail A et "proche" du portail B
        double distanceDeA = posApresTeleportation.distance(portailA.getPosition());
        double distanceDeB = posApresTeleportation.distance(portailB.getPosition());

        assertTrue("Le projectile doit être loin du portail A après téléportation",
                   distanceDeA > 50);
        assertTrue("Le projectile doit être proche du portail B après téléportation",
                   distanceDeB <= ObstacleTeleporteur.RAYON + 5);
    }

    @Test
    public void teleportation_BversA_fonctionne() {
        // La téléportation dans l'autre sens doit aussi marcher
        Projectile projectile = new Projectile(new Vecteur(400, 400), new Vecteur(-1, 0));

        portailB.eventAction(projectile);

        Vecteur pos = projectile.getPosition();
        double distanceDeA = pos.distance(portailA.getPosition());

        assertTrue("Le projectile doit se retrouver près du portail A",
                   distanceDeA <= ObstacleTeleporteur.RAYON + 5);
    }

    // ── Sortie hors de la zone de collision ──────────────────────────────────

    @Test
    public void apresTeleporation_projectileEstHorsZoneDeCollisionDeSortie() {
        // Après téléportation, le projectile ne doit plus être dans la zone
        // du portail de sortie (évite la re-téléportation immédiate)
        Projectile projectile = new Projectile(new Vecteur(100, 100), new Vecteur(1, 0));

        portailA.eventAction(projectile);

        // Le portail B ne doit plus détecter de collision
        assertFalse("Le projectile ne doit pas être encore en collision avec le portail de sortie",
                    portailB.estDansObstacle(projectile));
    }

    // ── Portail sans partenaire ───────────────────────────────────────────────

    @Test
    public void portailSansPartenaire_neBougePasLeProjectile() {
        ObstacleTeleporteur seul = new ObstacleTeleporteur(new Vecteur(200, 200));

        Vecteur positionAvant = new Vecteur(50, 50);
        Projectile projectile = new Projectile(positionAvant, new Vecteur(1, 0));

        seul.eventAction(projectile);

        // La position ne doit pas avoir changé
        assertEquals("Position X ne doit pas changer", positionAvant.getX(),
                     projectile.getPosition().getX(), 0.001);
        assertEquals("Position Y ne doit pas changer", positionAvant.getY(),
                     projectile.getPosition().getY(), 0.001);
    }

    // ── copier() ─────────────────────────────────────────────────────────────

    @Test
    public void copier_retourneUnPortailNonLie() {
        ObstacleTeleporteur copie = (ObstacleTeleporteur) portailA.copier();

        assertFalse("La copie ne doit pas être liée", copie.estLie());
    }

    @Test
    public void copier_conserveLaPosition() {
        ObstacleTeleporteur copie = (ObstacleTeleporteur) portailA.copier();

        assertEquals("La copie doit avoir la même position X",
                     portailA.getPosition().getX(), copie.getPosition().getX(), 0.001);
        assertEquals("La copie doit avoir la même position Y",
                     portailA.getPosition().getY(), copie.getPosition().getY(), 0.001);
    }
}