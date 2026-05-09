package core;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Color;
import utils.Vecteur;
import core.obstacles.*;
import core.Obstacle;
import java.util.HashSet;

/** Classe Map contient référence aux obstacles et projectiles. */
public class Map extends JPanel {

    private HashSet<Obstacle> obstacles = new HashSet<>();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0, 0, 100));
        g.fillRect(0, 0, 500, 500);

        for (Obstacle obs : obstacles) {
            int x = (int) obs.getPosition().getX();
            int y = (int) obs.getPosition().getY();
            if (obs instanceof ObstaclePropulseur) {
                Vecteur dir = ((ObstaclePropulseur) obs).getDirection();
                int x2 = x + (int)(dir.getX() * 40);
                int y2 = y + (int)(dir.getY() * 40);
                g.setColor(Color.CYAN);
                g.drawLine(x, y, x2, y2);
                g.fillOval(x - 4, y - 4, 8, 8);
                g.fillOval(x2 - 4, y2 - 4, 8, 8);
            } else {
                g.setColor(Color.RED);
                g.fillRect(x - 5, y - 5, 10, 10);
            }
        }

        if (projectile != null) {
            g.setColor(Color.YELLOW);
            int px = (int) projectile.getPosition().getX();
            int py = (int) projectile.getPosition().getY();
            g.fillOval(px - 3, py - 3, 6, 6);
        }

        if (cible != null) {
            g.setColor(Color.GREEN);
            int px = (int) cible.getPosition().getX();
            int py = (int) cible.getPosition().getY();
            g.fillOval(px - 3, py - 3, 6, 6);
        }
    }

    public static final Vecteur SCREEN_SIZE = new Vecteur(500.f);
    public static final double GAME_TICK = 1000000000L / 60;

    private boolean estDansEcran(Acteur acteur) {
        boolean estX = acteur.getPosition().getX() >= 0 && acteur.getPosition().getX() <= SCREEN_SIZE.getX();
        boolean estY = acteur.getPosition().getY() >= 0 && acteur.getPosition().getY() <= SCREEN_SIZE.getY();
        return estX && estY;
    }

    private Projectile projectile;
    private Vecteur position_initiale = new Vecteur(10.f);
    // TODO public que pour les tests
    private Vecteur vitesse_initiale = new Vecteur(10.f, .0f);
    private Vecteur cible_position = new Vecteur(450.f, 450.f);
    private ObstacleCible cible;
    
    private boolean estGagne;
    
    private Timer simulationTimer;
    
    public Map() {
        this.estGagne = false;
        initialiserParametres();
    }

    public void initialiserParametres() {
    	this.estGagne = false;
    	
        if (this.projectile == null) {
            this.projectile = new Projectile(position_initiale, vitesse_initiale);
        } else {
            this.projectile.setPosition(position_initiale);
            this.projectile.setVitesse(vitesse_initiale);
        }
        if (this.cible == null)
            this.cible = new ObstacleCible(cible_position);
    }

    public boolean ajouterObstacle(Obstacle obstacle) {
        boolean estAjoute = false;
        if (estDansEcran(obstacle)) {
            System.out.println("Obstacle register : " + obstacle.getPosition().toString());
            obstacles.add(obstacle);
            repaint();
            estAjoute = true;
        }
        return estAjoute;
    }

    public void lancerSimulation(Runnable onTermine) {
    	// Arrêter une simulation déjà en cours avant d'en lancer une nouvelle.
        if (simulationTimer != null && simulationTimer.isRunning()) {
            simulationTimer.stop();
        }
 
        initialiserParametres();
 
        // Le Timer tire sur l'EDT toutes les ~16 ms → l'interface reste réactive.
        int delayMs = (int) (GAME_TICK / 1_000_000);
 
        simulationTimer = new Timer(delayMs, e -> {
            testerCollisions();
 
            // FIX : 1e-9 (= 10⁻⁹) pour convertir des nanosecondes en secondes.
            // Avant : 10e-9 (= 10⁻⁸) donnait un dt 10× trop grand.
            projectile.actualiser(GAME_TICK * 1e-9);
 
            repaint();
 
            if (!estDansEcran(projectile) || estGagne) {
                simulationTimer.stop();
                if (onTermine != null) {
                    onTermine.run();
                }
            }
        });
 
        simulationTimer.start();

    }

    public void testerCollisions() {
        if (cible.estDansObstacle(this.projectile)) {
            gagne();
        }
        for (Obstacle obs : obstacles) {
            if (obs.estDansObstacle(this.projectile)) {
                obs.eventAction(this.projectile);
            }
        }
    }

    public void gagne() {
        this.estGagne = true;
    }

    public boolean estGagne() {
    return this.estGagne;
    }

}
