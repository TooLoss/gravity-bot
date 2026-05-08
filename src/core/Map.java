package core;

import javax.swing.JPanel;
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

        g.setColor(new Color(0, 0, 100)); // Dark Blue
        g.fillRect(0, 0, 500, 500);

        g.setColor(Color.RED);
        for (Obstacle obs : obstacles) {
            int x = (int) obs.getPosition().getX();
            int y = (int) obs.getPosition().getY();
            g.fillRect(x - 5, y - 5, 10, 10); 
        }

        if (projectile != null) {
            g.setColor(Color.BLUE);
            int px = (int) projectile.getPosition().getX();
            int py = (int) projectile.getPosition().getY();
            g.fillOval(px - 3, py - 3, 6, 6);
        }
    }

    public static final Vecteur SCREEN_SIZE = new Vecteur(500.f);
    public static final double GAME_TICK = 1000000000L / 60; // 60 TPS

    private boolean estDansEcran(Acteur acteur) {
        boolean estX = acteur.getPosition().getX() >= 0 && acteur.getPosition().getX() <= SCREEN_SIZE.getX();
        boolean estY = acteur.getPosition().getY() >= 0 && acteur.getPosition().getY() <= SCREEN_SIZE.getY();
        return estX && estY;
    }

    // NOTE n'est pas statique car doit être initialisé depuis un fichier save map
    private Projectile projectile;
    // TODO public que pour les tests
    public Vecteur position_initiale = new Vecteur(10.f);
    // TODO public que pour les tests
    public Vecteur vitesse_initiale = new Vecteur(10.f);
    private Vecteur cible_position = new Vecteur(50.f, .0f);
    private ObstacleCible cible;

    private boolean estSimulation;
    private boolean estGagne;

    public Map() {
        this.estSimulation = false;
        this.estGagne = false;
    }

    public void initialiserParametres() {
        this.projectile = new Projectile(position_initiale, vitesse_initiale);
        this.cible = new ObstacleCible(cible_position);
    }

    public void ajouterObstacle(Obstacle obstacle) {
        if (!estSimulation) {
            obstacles.add(obstacle);
            repaint();
        }
    }

    /** Méthode appelé chaque frame correspondant à la game loop.
     * @return boolean True si le projectile atteind l'objectif.
     */
    public boolean lancerSimulation() {
        this.estSimulation = true;
        long previous = System.nanoTime();
        long accumulator = 0;

        initialiserParametres();

        while (estDansEcran(projectile) && !estGagne) {
            long current = System.nanoTime();
            long elapsed = current - previous;
            previous = current;
            accumulator += elapsed;

            while (accumulator >= GAME_TICK) {
                testerCollisions();
                projectile.actualiser(GAME_TICK*10e-9);
                accumulator -= GAME_TICK;
                Vecteur sat_pos = projectile.getPosition();
                Vecteur sat_speed = projectile.getVitesse();
                System.out.println("Satellite position : " + sat_speed.getX() + " - " + sat_speed.getY());
                System.out.println("Satellite position : " + sat_pos.getX() + " - " + sat_pos.getY());
            }
            // TODO Faire l'affichage Swing
        }
        return estGagne;
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

}
