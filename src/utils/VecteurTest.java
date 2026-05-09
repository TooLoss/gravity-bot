import static org.junit.Assert.*;
import org.junit.Test;
import utils.Vecteur;

public class VecteurTest {

    // Delta pour la comparaison de nombres flottants 
    private static final double EPSILON = 1e-10;

    @Test
    public void testConstructeurs() {
        // Test constructeur vide (0,0)
        Vecteur v1 = new Vecteur();
        assertEquals(0.0, v1.getX(), EPSILON);
        assertEquals(0.0, v1.getY(), EPSILON);

        // Test constructeur avec une valeur (a,a)
        Vecteur v2 = new Vecteur(5.5);
        assertEquals(5.5, v2.getX(), EPSILON);
        assertEquals(5.5, v2.getY(), EPSILON);

        // Test constructeur AB avec deux points
        Vecteur a = new Vecteur(1, 2);
        Vecteur b = new Vecteur(4, 6);
        Vecteur v3 = new Vecteur(a, b);
        assertEquals(3.0, v3.getX(), EPSILON);
        assertEquals(4.0, v3.getY(), EPSILON);

        // Test constructeur par copie
        Vecteur v4 = new Vecteur(v3);
        assertEquals(v3.getX(), v4.getX(), EPSILON);
        assertEquals(v3.getY(), v4.getY(), EPSILON);
    }

    @Test
    public void testDistance() {
        Vecteur v1 = new Vecteur(0, 0);
        Vecteur v2 = new Vecteur(3, 4);
        // Distance = sqrt(3^2 + 4^2) = 5
        assertEquals(5.0, v1.distance(v2), EPSILON);
    }

    @Test
    public void testAjouterEtSoustraire() {
        Vecteur v = new Vecteur(10, 10);
        v.ajouter(new Vecteur(5, -2));
        assertEquals(15.0, v.getX(), EPSILON);
        assertEquals(8.0, v.getY(), EPSILON);

        v.soustraire(new Vecteur(5, 8));
        assertEquals(10.0, v.getX(), EPSILON);
        assertEquals(0.0, v.getY(), EPSILON);
    }

    @Test
    public void testMultiplier() {
        Vecteur v = new Vecteur(2, 3);
        v.multiplier(2.0);
        assertEquals(4.0, v.getX(), EPSILON);
        assertEquals(6.0, v.getY(), EPSILON);
    }

    @Test
    public void testNormeEtNormalisation() {
        Vecteur v = new Vecteur(3, 4);
        assertEquals(5.0, v.getNorme(), EPSILON);

        v.normaliser();
        assertEquals(1.0, v.getNorme(), EPSILON);
        assertEquals(0.6, v.getX(), EPSILON);
        assertEquals(0.8, v.getY(), EPSILON);
    }

    @Test
    public void testProduitScalaire() {
        Vecteur v1 = new Vecteur(2, 3);
        Vecteur v2 = new Vecteur(4, 5);
        // (2*4) + (3*5) = 8 + 15 = 23
        assertEquals(23.0, v1.produitScalaire(v2), EPSILON);
    }

    @Test
    public void testRotationner() {
        Vecteur v = new Vecteur(1, 0);
        // Rotation de 90 degrés 
        v.rotationner(Math.PI / 2);
        
        // Après 90°, (1,0) devient (0,1)
        assertEquals(0.0, v.getX(), EPSILON);
        assertEquals(1.0, v.getY(), EPSILON);
    }

    @Test
    public void testRotationnerAutourPivot() {
        Vecteur v = new Vecteur(2, 1);
        Vecteur pivot = new Vecteur(1, 1);
        
        // Rotation de 180 degrés autour de (1,1)
        // Le point (2,1) devrait se retrouver en (0,1)
        v.rotationner(Math.PI, pivot);
        
        assertEquals(0.0, v.getX(), EPSILON);
        assertEquals(1.0, v.getY(), EPSILON);
    }

    @Test
    public void testProjeterOrthogonalement() {
        Vecteur v = new Vecteur(3, 4);
        Vecteur axe = new Vecteur(1, 0); // Axe X
        
        v.projeterOrthogonalement(axe);
        
        // La projection de (3,4) sur l'axe X est (3,0)
        assertEquals(3.0, v.getX(), EPSILON);
        assertEquals(0.0, v.getY(), EPSILON);
    }

    @Test
    public void testVecteurNormal() {
        Vecteur v = new Vecteur(1, 0);
        Vecteur n = v.vecteurNormal();
        
        // Le vecteur normal à (1,0) est (0,1)
        assertEquals(0.0, n.getX(), EPSILON);
        assertEquals(1.0, n.getY(), EPSILON);
        
        // Le produit scalaire entre un vecteur et son normal doit être 0
        assertEquals(0.0, v.produitScalaire(n), EPSILON);
    }

    @Test
    public void testToString() {
        Vecteur v = new Vecteur(1.5, 2.5);
        assertEquals("(1.5,2.5)", v.toString());
    }
}