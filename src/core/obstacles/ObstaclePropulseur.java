package core.obstacles;
import core.Obstacle;
import core.Projectile;
import utils.Vecteur;
import core.collisions.CollisionSegment;

public class ObstaclePropulseur extends Obstacle {
    
  
    // La direction du propulseur 
    private Vecteur direction;
 
    
    public ObstaclePropulseur(Vecteur position, Vecteur directeur, double epaisseur)
    {
        this.setPosition(position);
        this.setCollision(new CollisionSegment(position, directeur, epaisseur));
        
	// On garde la direction normalisée 
        this.direction = new Vecteur(directeur);
        this.direction.normaliser();
    }
    
    @Override
    public void eventAction(Projectile projectile) {
 
        projectile.setVitesse(new Vecteur(this.direction));
        
    }
    
    @Override
	public void actualiser(double dt) {
    		
	}
    
}
