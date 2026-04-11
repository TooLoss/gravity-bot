package core.obstacles;

import core.Obstacle;
import core.Projectile;
import core.collisions.CollisionCirculaire;
import utils.Vecteur;

/**
 * Obstacle de type ventilateur/propulseur.
 * Lorsqu'un projectile entre dans sa zone, il subit une force directionnelle constante.
 */
public class ObstaclePropulseur extends Obstacle {

    // Le vecteur qui contient la direction et la puissance de la poussée
    private Vecteur forcePropulsion;

    /**
     * Constructeur du propulseur.
     * * @param position Le centre de la zone d'effet du propulseur.
     * @param rayon La taille (rayon) de la zone d'effet circulaire.
     * @param propulsion Le vecteur représentant vers où ça pousse et avec quelle force.
     */
    public ObstaclePropulseur(Vecteur position, double rayon, Vecteur propulsion) {
        // On place l'obstacle sur le terrain
        this.setPosition(position);
        
        // On définit sa zone de détection (un cercle autour de sa position)
        this.setCollision(new CollisionCirculaire(this.getPosition(), rayon));
        
        // On enregistre la force de propulsion (on crée un nouveau vecteur par sécurité)
        this.forcePropulsion = new Vecteur(propulsion);
    }

    /**
     * L'action déclenchée quand le projectile entre dans la zone.
     */
    @Override
    public void eventAction(Projectile projectile) {
        // On ajoute le vecteur de propulsion à la vitesse actuelle du projectile
        projectile.ajouterVitesse(this.forcePropulsion);
    }
    
}