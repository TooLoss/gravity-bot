package core;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.awt.Dimension;
import java.awt.RadialGradientPaint;
import java.awt.GradientPaint;
import java.util.Random;
import java.awt.geom.Point2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.GeneralPath;
import java.awt.geom.*;
import java.awt.RenderingHints;
import java.awt.Color;
import utils.Vecteur;
import core.obstacles.*;

import java.util.HashSet;

/** Classe Map contient une liste d'obstacles, une position initiale, vitesse
 * initiale et position finale, un budget et un budget par tour. */
public class Map extends JPanel {

    /** Liste d'obstacle de la map. */
    private HashSet<Obstacle> obstacles = new HashSet<>();
    /** Taille fixe de la Map. */
    public static final Vecteur SCREEN_SIZE = new Vecteur(500.f, 500.f);
    /** Tick pour le rafraichissement des acteurs. */
    public static final double GAME_TICK = 1000000000L / 60;
    /** Classe projectile satellite. */
    private Projectile projectile;
    /** Attribu position initiale du projectile. Lu dans un fichier. */
    private Vecteur position_initiale = new Vecteur(10.f);
    /** Attribu vitesse initiale du projectile. Lu dans un fichier. */
    private Vecteur vitesse_initiale = new Vecteur(10.f, .0f);
    /** Attribu position arrivé du projectile. Lu dans un fichier. */
    private Vecteur cible_position = new Vecteur(450.f, 450.f);
    /** Classe cible pour faire gagner le  joueur. */
    private ObstacleCible cible;
    /** Budget initiale de la map. Lu dans un fichier */
    private int budgetInitial;
    /** Budget ajouté par tour. */
    private int budgetParTour = 500;
    /** Définit si le joueur à gagné lors de la simulation ou non. */
    private boolean estGagne;

    /** Constructeur de la map. */
    public Map() {
        this.estGagne = false;
        initialiserParametres();
    }

    @Override
    protected void paintComponent(Graphics g) {
        /* Généré par IA */
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Activer l'anticrénelage pour des étoiles bien lisses
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Vecteur dimensions = new Vecteur(500, 500);
        this.setPreferredSize(new Dimension((int) dimensions.getX(), (int) dimensions.getY()));

        // --- 1. Gradient Bleu Foncé Spatial ---
        // Un dégradé du bleu nuit presque noir vers un bleu cosmique plus électrique
        GradientPaint bgGradient = new GradientPaint(
            0, 0, new Color(3, 3, 15),
            (float) dimensions.getX(), (float) dimensions.getY(), new Color(15, 20, 55)
        );
        g2d.setPaint(bgGradient);
        g2d.fillRect(0, 0, (int) dimensions.getX(), (int) dimensions.getY());

        // --- 2. Tracé de la Voie Lactée (Nébuleuse diagonale) ---
        // On crée une forme organique qui traverse l'écran
        GeneralPath voieLactee = new GeneralPath();
        voieLactee.moveTo(0, 400);
        voieLactee.quadTo(150, 250, 250, 250);
        voieLactee.quadTo(350, 250, 500, 100);
        voieLactee.lineTo(500, 150);
        voieLactee.quadTo(350, 300, 250, 300);
        voieLactee.quadTo(150, 300, 0, 450);
        voieLactee.closePath();

        // Remplissage de la Voie Lactée avec un dégradé radial pour un effet de halo "gazeux"
        Vecteur centreGalactique = new Vecteur(250, 250);
        float[] fractionsNébuleuse = {0.0f, 0.6f, 1.0f};
        Color[] couleursNébuleuse = {
            new Color(120, 100, 250, 45), // Centre violet/bleu lumineux
            new Color(70, 50, 180, 20),   // Corps de la nébuleuse atténué
            new Color(0, 0, 0, 0)         // Disparition dans le vide
        };
        RadialGradientPaint rgpNébuleuse = new RadialGradientPaint(
            new Point2D.Float((float) centreGalactique.getX(), (float) centreGalactique.getY()),
            300f, fractionsNébuleuse, couleursNébuleuse
        );
        g2d.setPaint(rgpNébuleuse);
        g2d.fill(voieLactee);

        // --- 3. Génération du Champ d'Étoiles ---
        // Utilisation d'un "seed" fixe (par exemple 42) pour que les étoiles restent à la même position à chaque frame
        Random rand = new Random(42); 
        int nbEtoiles = 65;

        for (int i = 0; i < nbEtoiles; i++) {
            // Position de l'étoile via Vecteur
            Vecteur posEtoile = new Vecteur(rand.nextInt((int) dimensions.getX()), rand.nextInt((int) dimensions.getY()));
            
            int taille = rand.nextInt(3) + 1; // Étoiles de 1 à 3 pixels
            int opacite = rand.nextInt(155) + 100; // Opacité aléatoire pour simuler le scintillement (100 à 255)
            
            // Nuance de couleur (légèrement bleutée, jaune ou blanche)
            int typeCouleur = rand.nextInt(3);
            if (typeCouleur == 0) {
                g2d.setColor(new Color(200, 220, 255, opacite)); // Étoile jeune / bleue
            } else if (typeCouleur == 1) {
                g2d.setColor(new Color(255, 245, 200, opacite)); // Étoile chaude / jaune
            } else {
                g2d.setColor(new Color(255, 255, 255, opacite)); // Étoile blanche standard
            }

            // Dessin de l'étoile
            g2d.fillOval((int) posEtoile.getX(), (int) posEtoile.getY(), taille, taille);

            // De temps en temps, dessiner un petit éclat (croix) sur les étoiles les plus grosses
            if (taille == 3 && rand.nextBoolean()) {
                g2d.setColor(new Color(255, 255, 255, opacite / 2));
                g2d.drawLine((int) posEtoile.getX() - 2, (int) posEtoile.getY() + 1, (int) posEtoile.getX() + 4, (int) posEtoile.getY() + 1);
                g2d.drawLine((int) posEtoile.getX() + 1, (int) posEtoile.getY() - 2, (int) posEtoile.getX() + 1, (int) posEtoile.getY() + 4);
            }
        }

        // --- 4. Affichage des éléments du jeu ---
        for (Obstacle obs : obstacles) {
            obs.afficher(g);
        }
        cible.afficher(g);
        projectile.afficher(g);
    }

    /** Fonction vérifie si l'acteur est dans l'écran.
     * @param acteur Acteur qui doit passer la vérification.
     * @return true si il est dans l'écran.
     */
    private boolean estDansEcran(Acteur acteur) {
        boolean estX = acteur.getPosition().getX() >= 0 &&
            acteur.getPosition().getX() <= SCREEN_SIZE.getX();
        boolean estY = acteur.getPosition().getY() >= 0 &&
            acteur.getPosition().getY() <= SCREEN_SIZE.getY();
        return estX && estY;
    }

    /** Initialise les paramètres pour lancer la simulation. Création du
     * projectile, position et vitesse, cible d'arrivé. */
    public void initialiserParametres() {
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

    /** Fonction pour ajouter obstacle.
     * @param obstacle Obstacle à ajouter (doit être une copie).
     * @return true si l'obstacle peut être placé.
     */
    public boolean ajouterObstacle(Obstacle obstacle) {
        System.out.println("getWidth: " + getWidth() + " getHeight: " + getHeight());
        System.out.println("Position obstacle: " + obstacle.getPosition().getX() + " " + obstacle.getPosition().getY());
        boolean estAjoute = false;
        if (estDansEcran(obstacle)) {
            obstacles.add(obstacle);
            repaint();
            estAjoute = true;
        }
        return estAjoute;
    }

    /** Fonction pour lancer la simulation du projectile.
     * Chaque frame le projectile est rafraichi. Les collisions sont testés
     * pour l'obstacle.
     * @return true si la simulation est gagnante.
     */
    public boolean lancerSimulation() {
        long previous = System.nanoTime();
        long accumulator = 0;

        initialiserParametres();

        // Se stop si le projectile est trop lent (bloquage) = crash.
        while (estDansEcran(projectile) && !estGagne &&
                this.projectile.getVitesse().getNorme() > 2.f) {
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
                paintImmediately(0, 0, getWidth(), getHeight());
            }
        }
        return estGagne;
    }

    /** Fonction qui test les collistions entre le projectile et les obstacles.
     * Si c'est le cas, la méthode eventAction est appelé. */
    public void testerCollisions() {
        if (cible.estDansObstacle(this.projectile)) {
            this.estGagne = true;
        }
        for (Obstacle obs : obstacles) {
            if (obs.estDansObstacle(this.projectile)) {
                obs.eventAction(this.projectile);
            }
        }
    }

    /** Getter de estGagne.
     * @return boolean true si gagné.
     */
    public boolean estGagne() {
        return this.estGagne;
    }

    /** Getter budgetParTour.
     * @return int budget par tour.
     */
    public int getBudgetParTour(){
        return this.budgetParTour;
    }

    /** Setter position initiale.
     * @param pos Vecteur initiale.
     */
    public void setPositionInitiale(Vecteur pos) {
        this.position_initiale = pos;
    }

    /** Setter vitesse initiale.
     * @param vit Vecteur initiale.
     */
    public void setVitesseInitiale(Vecteur vit) {
        this.vitesse_initiale = vit;
    }

    /** Setter position cible d'arrivé.
     * @param pos Vecteur.
     */
    public void setCiblePosition(Vecteur pos) { 
        this.cible_position = pos; 
        this.cible = new ObstacleCible(pos);
    }

    /** Méthode vider la carte. */
    public void viderCarte() {
        this.obstacles.clear();
    }
}
