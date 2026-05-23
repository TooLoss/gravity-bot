package ihm;

import javax.swing.*;
import java.awt.*;
import core.obstacles.Obstacle;
import core.Game;
import core.Map;
import core.obstacles.*;
import utils.Vecteur;
import ihm.controleur.ControleurJeu;
import ihm.*;

/** La classe FenetreJeu constitue la Vue principale de l'interface graphique (IHM). */
public class FenetreJeu extends JFrame {

    /** Le moteur logique du jeu contenant l'état actuel (Modèle). */
    private Game engine;

    /** Étiquette textuelle affichant le budget disponible du joueur courant. */
    private JLabel labelBudget;

    /** Étiquette textuelle affichant le numéro du tour ainsi que le nom du joueur actif. */
    private JLabel labelTour;

    /** L'obstacle actuellement sélectionné par l'utilisateur dans la boutique de la barre latérale. */
    private Obstacle selectedObstacle;

    /** Référence vers le mode éditeur ayant lancé cette fenêtre, permettant un retour à celui-ci. */
    private ModeEditeur editeur;
    
    /** Le contrôleur chargé d'intercepter et de traiter les interactions de l'utilisateur. */
    private ControleurJeu controleur; 

    /** Constructeur complet permettant d'initialiser la fenêtre de jeu depuis le mode éditeur.
     * Instancie également le ControleurJeu associé à cette vue.
     * @param engine Le moteur de jeu (Modèle) à associer à cette interface.
     * @param editeur L'instance du mode éditeur parent pour permettre la navigation de retour.
     */
    public FenetreJeu(Game engine, ModeEditeur editeur) {
        this.engine = engine;
        this.editeur = editeur;
        this.controleur = new ControleurJeu(engine, this); 

        setTitle("Gravity-Bot");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        construireInterface();
        setVisible(true);
    }

    /** Constructeur secondaire initialisant le jeu sans mode éditeur associé.
     * @param engine Le moteur de jeu (Modèle) à associer à cette interface.
     */
    public FenetreJeu(Game engine) {
        this(engine, null);
    }

    /** Constructeur par défaut (vide). */
    public FenetreJeu() {}

    /** Initialise et assemble les différents composants de l'IHM. */
    private void construireInterface() {
        getContentPane().setBackground(new Color(10, 10, 15));
        setLayout(new BorderLayout(5, 5));

        add(construireBandeauNord(), BorderLayout.NORTH);

        Map mapPanel = engine.getMap();
        mapPanel.addMouseListener(controleur.getMapMouseListener());

        mapPanel.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 120), 2));
        add(mapPanel, BorderLayout.CENTER);

        add(construireSidebar(), BorderLayout.EAST);
        add(construireFooter(), BorderLayout.SOUTH);
    }

    /** Crée et configure le panneau supérieur affichant les informations textuelles (budget, tour).
     * @return Un JPanel configuré contenant les étiquettes de suivi.
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

    /** Crée et configure la barre latérale servant de boutique pour acheter et sélectionner les obstacles.
     * @return Un JPanel structuré verticalement contenant la liste des boutons d'obstacles.
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

                ObstacleAimant aimantPullLegacy = new ObstacleAimant(new Vecteur(.0f), 5.0, 30.0, 50);
        sidebar.add(creerBoutonElement("Aimant Pull LegacyTech",
                    String.valueOf(aimantPullLegacy.getPrix()),
                    aimantPullLegacy,
                    new Color(35, 30, 35)));    
        sidebar.add(Box.createVerticalStrut(15));
        ObstacleAimant aimantPushLegacy = new ObstacleAimant(new Vecteur(.0f), -5.0, 30.0, 50);
        sidebar.add(creerBoutonElement("Aimant Push LegacyTech",
                    String.valueOf(aimantPushLegacy.getPrix()),
                    aimantPushLegacy,
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
        ObstacleAimant aimantPullNeon = new ObstacleAimant(new Vecteur(.0f), 50.0, 20.0, 150);
        sidebar.add(creerBoutonElement("Aimant Pull Neon",
                    String.valueOf(aimantPullNeon.getPrix()),
                    aimantPullNeon,
                    new Color(160, 25, 215)));
        sidebar.add(Box.createVerticalStrut(15));
        ObstacleAimant aimantPushNeon = new ObstacleAimant(new Vecteur(.0f), -50.0, 20.0, 150);
        sidebar.add(creerBoutonElement("Aimant Push Neon",
                    String.valueOf(aimantPushNeon.getPrix()),
                    aimantPushNeon,
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

    /** Crée et configure le bandeau inférieur contenant la navigation (Retour Menu, Éditeur) 
     * ainsi que le déclenchement de la simulation.
     * @return Un JPanel contenant les boutons d'action globale.
     */
    private JPanel construireFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 12));
        footer.setBackground(new Color(15, 15, 20));

        if (editeur != null) {
            JButton btnRetourEditeur = new JButton("RETOUR ÉDITEUR");
            styliserBoutonAction(btnRetourEditeur, new Color(180, 130, 20));
            btnRetourEditeur.addActionListener(e -> {
                this.dispose();
                editeur.setVisible(true);
            });
            footer.add(btnRetourEditeur);
        }

        JButton btnRetour = new JButton("RETOUR MENU");
        styliserBoutonAction(btnRetour, new Color(100, 100, 100));
        btnRetour.addActionListener(e -> {
            this.dispose();
            new MenuPrincipal();
        });

        JButton btnLancer = new JButton("LANCER LA SIMULATION");
        styliserBoutonAction(btnLancer, new Color(40, 140, 80));
        
        btnLancer.addActionListener(controleur.getActionLancerSimulation());

        footer.add(btnRetour);
        footer.add(btnLancer);
        return footer;
    }

    /** Méthode utilitaire de fabrication des boutons d'achat de la boutique latérale.
     * @param nom Le nom textuel à afficher sur le bouton.
     * @param prix Le tarif sous forme de chaîne de caractères.
     * @param type L'instance de l'obstacle servant de modèle de copie lors du futur placement.
     * @param c La couleur de fond personnalisée du bouton.
     * @return Un JButton stylisé prêt à être intégré.
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
            this.selectedObstacle = type;
        });
        return btn;
    }

    /** Applique une charte visuelle standardisée et avancée aux boutons d'actions principaux.
     * @param btn Le bouton sur lequel appliquer les bordures, couleurs et polices.
     * @param c La couleur de base du thème pour ce bouton.
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

    /** Fournit l'obstacle actuellement sélectionné dans la barre latérale.
     * du choix de l'utilisateur afin de procéder au placement sur la carte.
     * @return L'instance de l'Obstacle sélectionné, ou null si aucun choix n'a été fait.
     */
    public Obstacle getSelectedObstacle() {
        return this.selectedObstacle;
    }

    /** Interroge le modèle et met à jour les labels. */
    public void rafraichirLabels() {
        labelBudget.setText("Budget : " + engine.getJoueurCourant().getBudget() + "€");
        labelTour.setText("Tour : " + engine.getTour() + " (" + engine.getJoueurCourant().getNom() + ")");
    }
}
