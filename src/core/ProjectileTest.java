package core;

import org.junit.Test;
import static org.junit.Assert.*;

import utils.Vecteur;

public class ProjectileTest {

    @Test
    public void testActualiserDeplacement() {
        // Initialisation : Projectile en (0,0) avec une vitesse de (10, 5)
        Vecteur positionInitiale = new Vecteur(0, 0);
        Vecteur vitesse = new Vecteur(10, 5);
        Projectile projectile = new Projectile(positionInitiale, vitesse);

        // Action : On fait avancer le temps de 0.5 seconde (dt = 0.5)
        projectile.actualiser(0.5);

        // Vérification :
        // En X : 0 + (10 * 0.5) = 5.0
        // En Y : 0 + (5 * 0.5) = 2.5
        Vecteur nouvellePosition = projectile.getPosition();

        assertEquals("La position X après actualisation est fausse", 5.0, nouvellePosition.getX(), 0.001);
        assertEquals("La position Y après actualisation est fausse", 2.5, nouvellePosition.getY(), 0.001);
    }

    @Test
    public void testAjouterVitesseEtNormalisation() {
        // Initialisation : Projectile avec une vitesse de (1, 0)
        Projectile projectile = new Projectile(new Vecteur(0, 0), new Vecteur(1, 0));

        // Action : On ajoute une force vers le haut (0, 1)
        // La vitesse brute devient (1, 1).
        // Mais ta méthode normalise, donc la longueur totale doit redevenir 1.0 
        projectile.ajouterVitesse(new Vecteur(0, 1));

        // 3. Vérification : La nouvelle vitesse doit être d'environ (0.707, 0.707)
        Vecteur nouvelleVitesse = projectile.getVitesse();

        // Math.sqrt(2) / 2 donne environ 0.7071
        double valeurAttendue = Math.sqrt(2) / 2.0;

        assertEquals("La vitesse X n'a pas été bien normalisée", valeurAttendue, nouvelleVitesse.getX(), 0.001);
        assertEquals("La vitesse Y n'a pas été bien normalisée", valeurAttendue, nouvelleVitesse.getY(), 0.001);
    }

    @Test
    public void testSetVitesseIndependance() {
        // Ce test vérifie que tu as bien fait un "new Vecteur(v)" dans ton setter
        // pour éviter les bugs de référence mémoire.
        Projectile projectile = new Projectile(new Vecteur(0, 0), new Vecteur(0, 0));

        Vecteur nouvelleVitesse = new Vecteur(3, 4);
        projectile.setVitesse(nouvelleVitesse);

        // Si on modifie le vecteur externe, ça ne doit PAS modifier le projectile
        nouvelleVitesse.ajouter(new Vecteur(10, 10));

        Vecteur vitesseProjectile = projectile.getVitesse();

        assertEquals("La vitesse X devrait être protégée (encapsulation)", 3.0, vitesseProjectile.getX(), 0.001);
        assertEquals("La vitesse Y devrait être protégée (encapsulation)", 4.0, vitesseProjectile.getY(), 0.001);
    }
}