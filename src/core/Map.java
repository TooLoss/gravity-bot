package core;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import utils.Vecteur;
import core.obstacles.*;
import core.obstacles.Obstacle;
import java.util.HashSet;

/** Classe Map contient référence aux obstacles et projectiles. */
public class Map extends JPanel {
    private Audio.GestionnaireAudio gestionnaireAudio;
    private volatile boolean simulationEnCours = false;
    private HashSet<Obstacle> obstacles = new HashSet<>();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0, 0, 100));
        g.fillRect(0, 0, 500, 500);

        for (Obstacle obs : obstacles) {
            System.out.println("A affichier l'obstacle");
            obs.afficher(g);
        }
        cible.afficher(g);
        projectile.afficher(g);
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
    private Vecteur vitesse_initiale = new Vecteur(10.f, .0f);
    private Vecteur cible_position = new Vecteur(450.f, 450.f);
    private ObstacleCible cible;

    private boolean estGagne;

    public Map() {
        this.estGagne = false;
        initialiserParametres();
    }
    public void setGestionnaireAudio(Audio.GestionnaireAudio ga){
        this.gestionnaireAudio = ga;
    } 
    public void initialiserParametres() {
    	this.estGagne = false;
    	if (this.projectile == null) {
            this.projectile = new Projectile(position_initiale, vitesse_initiale);
        } else {
            this.projectile.setPosition(position_initiale);
            this.projectile.setVitesse(vitesse_initiale);
            this.projectile.reset();
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

    public void lancerSimulation(Runnable onFin) {
        if (simulationEnCours) return;
        simulationEnCours = true;
        initialiserParametres();

        new Thread(() -> {
            long previous = System.nanoTime();
            long accumulator = 0;

            while (simulationEnCours
                    && estDansEcran(projectile)
                    && !estGagne
                    && projectile.getVitesse().getNorme() > 2.f) {

                long current = System.nanoTime();
                long elapsed = current - previous;
                previous = current;
                accumulator += elapsed;

                while (accumulator >= GAME_TICK) {
                    testerCollisions();
                    projectile.actualiser(GAME_TICK * 10e-9);
                    accumulator -= GAME_TICK;
                }

                javax.swing.SwingUtilities.invokeLater(this::repaint);
                try { Thread.sleep(16); } catch (InterruptedException ignored) {}
            }

            simulationEnCours = false;
            if (onFin != null) javax.swing.SwingUtilities.invokeLater(onFin);

        }, "SimulationThread").start();
    }

    public void stopperSimulation() {
        simulationEnCours = false;
    }

    public void testerCollisions() {
        if (cible.estDansObstacle(this.projectile)) {
            this.estGagne = true;
        }
        for (Obstacle obs : obstacles) {
            if (obs.estDansObstacle(this.projectile)) {
                obs.eventAction(this.projectile);
                if (gestionnaireAudio != null) {
                gestionnaireAudio.jouerSFX("src/interface/Audio/collision.wav");
                }
            }
        }
    }

    public boolean estGagne() {
        return this.estGagne;
    }

    public void setPositionInitiale(Vecteur pos) {
        this.position_initiale = pos;
    }

    public void setVitesseInitiale(Vecteur vit) {
        this.vitesse_initiale = vit;
    }

    public void setCiblePosition(Vecteur pos) { 
        this.cible_position = pos; 
        this.cible = new ObstacleCible(pos);
    }

    public void viderCarte() {
        this.obstacles.clear();
    }
}
