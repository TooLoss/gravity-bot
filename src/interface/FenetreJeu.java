import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import core.obstacles.*;
import core.Game;
import core.Map;
import core.obstacles.Obstacle;
import utils.Vecteur;
/**
 * La classe FenetreJeu constitue l'interface principale de la simulation.
 * Elle affiche la carte de jeu, les contrôles de placement d'obstacles,
 * ainsi que les informations de budget et de tour des joueurs.
 */
public class FenetreJeu extends JFrame {
    /** Moteur logique du jeu gérant les règles et les tours. */
    private Game engine;
    /** Étiquette affichant le budget restant du joueur courant. */
    private JLabel labelBudget;
    /** Étiquette affichant le numéro du tour et le nom du joueur actif. */
    private JLabel labelTour;
    /** Type d'obstacle actuellement sélectionné dans la barre latérale. */
    private Obstacle selectedObstacle;
    private Vecteur clickedPosition;
    private int prix;
    private JLabel labelStatut;
    private JButton btnLancer;

    /**
     * Constructeur de la fenêtre de jeu.
     * @param engine Le moteur de jeu (Game) à associer à cette interface.
     */
    public FenetreJeu(Game engine) {
        this.engine = engine;
        setTitle("Gravity-Bot");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        construireInterface();
        setVisible(true);
    }
    /**
     * Constructeur par défaut (vide).
     */
    public FenetreJeu() {}

    /**
     * Initialise et assemble les différents composants de l'interface graphique.
     * Utilise un BorderLayout pour organiser le bandeau, la barre latérale, 
     * la carte et le footer.
     */
    private void construireInterface() {
        getContentPane().setBackground(new Color(10, 10, 15));
        setLayout(new BorderLayout(5, 5));

        add(construireBandeauNord(), BorderLayout.NORTH);

        Map mapPanel = engine.getMap();
        mapPanel.addMouseListener(new MouseAdapter() {
            private int startX;
            private int startY;
            private long tempsPression;
            private static final long SEUIL_HOLD_MS = 300; 

            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
                tempsPression = System.currentTimeMillis();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int endX = e.getX();
                int endY = e.getY();

                long dureeClic = System.currentTimeMillis() - tempsPression;

                placerObstacleDirection(startX, startY, endX, endY);
                // if (dureeClic >= SEUIL_HOLD_MS) {
                //     // Click Hold
                //     placerObstacleDirection(startX, startY, endX, endY);
                // } else {
                //     // Click rapide
                //     placerObstacle(startX, startY);
                // }
            }
        });
        mapPanel.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 120), 2));
        add(mapPanel, BorderLayout.CENTER);

        add(construireSidebar(), BorderLayout.EAST);
        add(construireFooter(), BorderLayout.SOUTH);
    }
    /**
     * Crée le panneau supérieur affichant le budget et le tour actuel.
     * @return Le JPanel configuré pour le haut de l'écran.
     */
    private JPanel construireBandeauNord() {
        JPanel bandeau = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        bandeau.setBackground(new Color(25, 25, 30));
        
        labelBudget = new JLabel();
        labelTour = new JLabel();
        
        Font f = new Font("SansSerif", Font.BOLD, 18);
        labelBudget.setFont(f);
        labelBudget.setForeground(new Color(100, 255, 100));
        labelTour.setFont(f);
        labelTour.setForeground(new Color(180, 180, 255));
        
        rafraichirLabels();

        bandeau.add(labelBudget);
        bandeau.add(labelTour);
        return bandeau;
    }
    /**
     * Crée la barre latérale contenant les boutons de sélection d'obstacles.
     * @return Le JPanel de la sidebar.
     */
    private JPanel construireSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(20, 20, 40));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        JLabel titre = new JLabel("ÉLÉMENTS");
        titre.setFont(new Font("SansSerif", Font.BOLD, 16));
        titre.setForeground(Color.WHITE);
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(titre);
        sidebar.add(Box.createVerticalStrut(20));

        sidebar.add(creerBoutonElement("Aimant Pull LegacyTech",
                    String.valueOf(ObstacleAimant.prix),
                    new ObstacleAimant(new Vecteur(.0f), 5.0, 30.0),
                    new Color(35, 30, 35)));
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(creerBoutonElement("Aimant Push LegacyTech",
                    String.valueOf(ObstacleAimant.prix),
                    new ObstacleAimant(new Vecteur(.0f), -5.0, 30.0),
                    new Color(35, 30, 35)));
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(creerBoutonElement("Propulseur LegacyTech",
                    String.valueOf(ObstaclePropulseurFaible.prix), new
                    ObstaclePropulseurFaible(new Vecteur(0, 0), new Vecteur(50, 0), 0.03f, 10.f, 50.f),
                    new Color(35, 30, 35)));
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(creerBoutonElement("Teleporteur LegacyTech",
                    String.valueOf(ObstacleTeleporteur.prix), new
                    ObstacleTeleporteur(new Vecteur(0, 0), new Vecteur(50, 0), 15.f, 50.f),
                    new Color(35, 30, 35)));
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(creerBoutonElement("Aimant Pull Neon",
                    String.valueOf(ObstacleAimant.prix),
                    new ObstacleAimant(new Vecteur(.0f), 50.0, 20.0),
                    new Color(160, 25, 215)));
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(creerBoutonElement("Aimant Push Neon",
                    String.valueOf(ObstacleAimant.prix),
                    new ObstacleAimant(new Vecteur(.0f), -50.0, 20.0),
                    new Color(160, 25, 215)));
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(creerBoutonElement("Propulseur Neon",
                    String.valueOf(ObstaclePropulseur.prix), new
                    ObstaclePropulseur(new Vecteur(0, 0), new Vecteur(50, 0), 0.1f, 20.f, 100.f),
                    new Color(160, 25, 215)));
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(creerBoutonElement("Teleporteur Neon",
                    String.valueOf(ObstacleTeleporteurNeon.prix), new
                    ObstacleTeleporteurNeon(new Vecteur(0, 0), new Vecteur(50, 0), 8.f, 150.f),
                    new Color(160, 25, 215)));

        return sidebar;
    }
    /**
     * Crée le panneau de contrôle inférieur (Footer) avec les actions globales.
     * @return Le JPanel du footer.
     */
    private JPanel construireFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 12));
        footer.setBackground(new Color(15, 15, 20));

        // Bouton retour
        JButton btnRetour = new JButton("RETOUR MENU");
        styliserBoutonAction(btnRetour, new Color(100, 100, 100)); // Gris pour le retour
        btnRetour.addActionListener(e -> {
        this.dispose(); // ferme la fenêtre actuelle
        new MenuPrincipal(); // rouvre le menu
        });


        JButton btnLancer = new JButton("LANCER LA SIMULATION");
        styliserBoutonAction(btnLancer, new Color(40, 140, 80));
        
        btnLancer.addActionListener(e -> {
            btnLancer.setEnabled(false);
            labelStatut.setText("En cours...");
            engine.executerTour(() -> {
                rafraichirLabels();
                btnLancer.setEnabled(true);
                labelStatut.setText(engine.getMap().estGagne() ? "GAGNÉ !" : "Perdu – replacez vos obstacles");
            });
        });

        // Bouton Arrêter
        // JButton btnArreter = new JButton("STOP");
        // styliserBoutonAction(btnArreter, new Color(160, 40, 40)); // Rouge
        // btnArreter.addActionListener(e -> {
        //     if (engine.getMap() != null) {
        //         // On force l'arrêt du Timer de simulation dans Map
        //         engine.getMap().stopperSimulation();
        //     }
        // });

        footer.add(btnRetour);
        footer.add(btnLancer);
        // footer.add(btnArreter);
        return footer;
    }
    /**
     * Utilitaire pour créer un bouton d'élément (boutique) stylisé.
     * @param nom Le nom de l'obstacle.
     * @param prix Le prix affiché.
     * @param type L'instance modèle d'obstacle à sélectionner.
     * @param c La couleur de fond du bouton.
     * @return Le JButton configuré.
     */
    private JButton creerBoutonElement(String nom, String prix, Obstacle type, Color c) {
        JButton btn = new JButton("<html><center><b>" + nom + "</b><br>" + prix + "</center></html>");
        btn.setMaximumSize(new Dimension(170, 65));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.setBackground(c);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createRaisedBevelBorder());

        btn.addActionListener(e -> {
            selectedObstacle = type;
        });
        return btn;
    }
    /**
     * Applique un style visuel cohérent aux boutons d'actions principaux.
     * @param btn Le bouton à styliser.
     * @param c La couleur thématique du bouton.
     */
    private void styliserBoutonAction(JButton btn, Color c) {
        btn.setBackground(c);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(c.brighter(), 2),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
    }
    /**
     * Gère la logique de placement d'un obstacle sur la carte.
     * Vérifie le budget du joueur
     * @param x Coordonnée X du clic.
     * @param y Coordonnée Y du clic.
     */
    private void placerObstacle(int x, int y) {
        if (selectedObstacle == null) return;

        selectedObstacle.setPosition(x, y);
        Obstacle copy = selectedObstacle.copier();
        if (engine.getMap().ajouterObstacle(copy)) {
            engine.getJoueurCourant().retirerBudget(copy.getPrix());
            rafraichirLabels();
        }

        this.clickedPosition = new Vecteur(x, y);
    }

    private void placerObstacleDirection(int bx, int by, int ex, int ey) {
        if (selectedObstacle == null) return;

        selectedObstacle.setPointFinal(new Vecteur(ex, ey));
        placerObstacle(bx, by);
    }

    /**
     * Met à jour les textes des étiquettes (budget et tour) avec les données du moteur.
     */
    private void rafraichirLabels() {
        labelBudget.setText("Budget : " + engine.getJoueurCourant().getBudget() + "€");
        labelTour.setText("Tour : " + engine.getTour() + " (" + engine.getJoueurCourant().getNom() + ")");
    }
}
