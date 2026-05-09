package core;

/**
 * Contrat définissant les capacités de détection de contact.
 * Toute forme géométrique de collision doit implémenter
 * cette interface.
 */
public interface Collision {

    /**
     * Détermine si un acteur donné se trouve à l'intérieur ou touche
     * la zone définie par cette collision.
     * @param acteur L'objet dont on veut tester la position.
     * @return true si l'acteur est en contact avec la zone, false sinon.
     */
    boolean estEnCollision(Acteur acteur);

}