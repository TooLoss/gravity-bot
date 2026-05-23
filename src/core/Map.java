package core;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
    public static final Vecteur SCREEN_SIZE = new Vecteur(800.f, 700.f);
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
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Fond dégradé bleu foncé
        java.awt.GradientPaint bg = new java.awt.GradientPaint(
            0, 0, new Color(5, 5, 30),
            800, 700, new Color(10, 20, 60)
        );
        g2d.setPaint(bg);
        g2d.fillRect(0, 0, 800, 700);

        // Grille subtile
        g2d.setColor(new Color(255, 255, 255, 5));
        g2d.setStroke(new java.awt.BasicStroke(0.5f));
        for (int i = 0; i < 800; i += 50) g2d.drawLine(i, 0, i, 700);
        for (int j = 0; j < 700; j += 50) g2d.drawLine(0, j, 800, j);

        for (Obstacle obs : obstacles) {
            System.out.println("A affichier l'obstacle");
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

    /** Getter de estGagne. */
    public boolean estGagne() {
        return this.estGagne;
    }

    /** Getter budgetParTour. */
    public int getBudgetParTour(){
        return this.budgetParTour;
    }

    /** Setter position initiale. */
    public void setPositionInitiale(Vecteur pos) {
        this.position_initiale = pos;
    }

    /** Setter vitesse initiale. */
    public void setVitesseInitiale(Vecteur vit) {
        this.vitesse_initiale = vit;
    }

    /** Setter position cible d'arrivé. */
    public void setCiblePosition(Vecteur pos) { 
        this.cible_position = pos; 
        this.cible = new ObstacleCible(pos);
    }

    /** Méthode vider la carte. */
    public void viderCarte() {
        this.obstacles.clear();
    }
}
