package core.obstacles;

import static org.junit.Assert.*;
import org.junit.Test;
import core.Projectile;
import utils.Vecteur;

public class ObstaclePropulseurTest {

    private static final double EPSILON = 1e-10;

    /**
     * Teste que le constructeur initialise bien la position et
     * que la direction est normalisée.
     */
    @Test
    public void testConstructeur() {
        Vecteur pos = new Vecteur(10.0, 20.0);
        ObstaclePropulseur propulseur = new ObstaclePropulseur(pos);

        // Vérification position
        assertEquals(10.0, propulseur.getPosition().getX(), EPSILON);
        assertEquals(20.0, propulseur.getPosition().getY(), EPSILON);

        // Vérification normalisation direction (par défaut basée sur directeur(50,50))
        // La norme d'un vecteur (50, 50) normalisé doit être 1.0
        assertEquals(1.0, propulseur.getDirection().getNorme(), EPSILON);
    }

    /**
     * Teste que setDirection normalise bien le vecteur passé en paramètre.
     */
    @Test
    public void testSetDirection() {
        ObstaclePropulseur propulseur = new ObstaclePropulseur(new Vecteur(0, 0));

        // On donne une direction non normalisée (3, 4) -> norme = 5
        propulseur.setDirection(new Vecteur(3.0, 4.0));

        Vecteur dir = propulseur.getDirection();

        // La direction doit être (3/5, 4/5) = (0.6, 0.8)
        assertEquals(0.6, dir.getX(), EPSILON);
        assertEquals(0.8, dir.getY(), EPSILON);
        assertEquals(1.0, dir.getNorme(), EPSILON);
    }

    /**
     * Teste l'effet du propulseur sur un projectile.
     * Le projectile doit voir sa vitesse alignée sur la direction du propulseur
     * avec une intensité égale à Projectile.vitesseSatellite.
     */
    @Test
    public void testEventAction() {
        // Propulseur orienté vers la gauche (-1, 0)
        ObstaclePropulseur propulseur = new ObstaclePropulseur(new Vecteur(0, 0));
        propulseur.setDirection(new Vecteur(-1.0, 0.0));

        // Projectile qui allait initialement vers le haut (0, 10)
        Projectile proj = new Projectile(new Vecteur(0, 0), new Vecteur(0.0, 10.0));

        // Activation du propulseur
        propulseur.eventAction(proj);

        // La nouvelle vitesse doit être (-10, 0) car vitesseSatellite = 10
        assertEquals(-10.0, proj.getVitesse().getX(), EPSILON);
        assertEquals(0.0, proj.getVitesse().getY(), EPSILON);
        assertEquals(Projectile.vitesseSatellite, proj.getVitesse().getNorme(), EPSILON);
    }

    /**
     * Teste la méthode de copie profonde.
     */
    @Test
    public void testCopier() {
        Vecteur pos = new Vecteur(5, 5);
        ObstaclePropulseur original = new ObstaclePropulseur(pos);
        original.setDirection(new Vecteur(1, 0));

        ObstaclePropulseur copie = (ObstaclePropulseur) original.copier();

        // Vérification des valeurs
        assertEquals(original.getPosition().getX(), copie.getPosition().getX(), EPSILON);
        assertEquals(original.getDirection().getX(), copie.getDirection().getX(), EPSILON);

        // Vérification de l'indépendance
        assertNotSame(original, copie);
        assertNotSame(original.getDirection(), copie.getDirection());

        // Si on modifie l'original, la copie ne doit pas bouger
        original.setDirection(new Vecteur(0, 1));
        assertNotEquals(original.getDirection().getX(), copie.getDirection().getX(), EPSILON);
    }
}