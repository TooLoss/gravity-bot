package core.obstacles;
import core.Obstacle;
import core.Projectile;
import utils.Vecteur;
import core.collisions.CollisionSegment;

    
 
public class ObstaclePropulseur extends Obstacle {
    
  
    // La direction du propulseur 
    private Vecteur direction;
 
    // La vitesse à ajouter 
    private double vitesseBoost;
    
    public ObstaclePropulseur(Vecteur position, Vecteur directeur, double epaisseur, double vitesse)
    {
        this.setPosition(position);
        this.vitesseBoost = vitesse;
        this.setCollision(new CollisionSegment(position, directeur, epaisseur));
        
	// On garde la direction normalisée 
        this.direction = new Vecteur(directeur);
        this.direction.normaliser();
    }
    @Override
    public void eventAction(Projectile projectile) {
    
    	// Ancienne vitesse du projectile
        double vitesseAncienne = projectile.getVitesse().getNorme();
 
        // Nouvelle vitesse = direction du segment * (vitesseAncienne + vitesseBoost)
        Vecteur nouvelleVitesse = new Vecteur(direction);
        nouvelleVitesse.multiplier(vitesseAncienne + vitesseBoost);
 
        projectile.setVitesse(nouvelleVitesse);
    }
    
    @Override
	public void actualiser(double dt) {
    		
	}
    
}
