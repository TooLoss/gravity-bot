package ihm;

import javax.swing.*;
import java.awt.*;
import core.Game;
import core.joueurs.Joueur;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import ihm.controleur.ControleurEditeur;

import utils.Vecteur;

/** Fenetre du mode Editeur de Gravity-Bot.
 * Permet au joueur de construire son propre contrat en :
 * - placant des obstacles sur le plateau
 * - reglant le budget de depart
 * - definissant la position de l'objectif
 * Cette fenetre s'ouvre depuis le bouton "Editeur" du MenuPrincipal.
 * La structure visuelle est identique a FenetreJeu, seule la sidebar change.
 */
public class ModeEditeur extends JFrame {
    /** Le budget de départ. **/
    private int budgetDepart = 1000;

    /**Le label du budget de départ. **/
    private JLabel labelBudgetDepart;

    /** L'outil selectionné.**/
    private String outilSelectionne = null; // "obstacle", "objectif", "depart"

    /** Configuration de l'obstacle courant choisi par l'utilisateur **/
    private boolean obstacleEstMur = false;
    private int obstacleRayon = 15;

    /**Référence à la grille pour la mettre à jour visuellement. **/
    private AfficherGrille grille;

    /**Position de la cible choisie dans l'éditeur(coord Map). **/
    private Vecteur ciblePosition = new Vecteur(450.0, 450.0);

    /**Position de départ du satellite choisie dans l'éditeur(coordonées Map). **/
    private Vecteur positionDepart = new Vecteur(10.0, 10.0);

    public ModeEditeur() {
        setTitle("Gravity-Bot - Editeur de niveaux");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        construireInterface();
    }

    private void construireInterface() {
        setLayout(new BorderLayout(5, 5));

        add(construireBandeauNord(), BorderLayout.NORTH);
        add(construireCentre(), BorderLayout.CENTER);
        add(construireSidebar(), BorderLayout.EAST);
        add(construireFooter(), BorderLayout.SOUTH);

    }

    // Bandeau nord : affiche le budget de départ que le joueur est en train de configurer
    private JPanel construireBandeauNord() {
        JPanel bandeau = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 8));
        bandeau.setBackground(new Color(30, 20, 10));

        JLabel labelTitre = new JLabel("Mode Editeur");
        labelTitre.setForeground(new Color(255, 200, 80));
        labelTitre.setFont(new Font("Arial", Font.BOLD, 16));

        labelBudgetDepart = new JLabel("Budget de depart : " + budgetDepart + " euros");
        labelBudgetDepart.setForeground(Color.LIGHT_GRAY);
        labelBudgetDepart.setFont(new Font("Arial", Font.BOLD, 16));

        bandeau.add(labelTitre);
        bandeau.add(labelBudgetDepart);

        return bandeau;
    }

    // Dans construireCentre() :
    private JPanel construireCentre() {
        JPanel centre = new JPanel(new BorderLayout());
        centre.setBackground(new Color(20, 15, 5));
        centre.setBorder(BorderFactory.createLineBorder(new Color(120, 80, 20), 2));

        grille = new AfficherGrille();

        // BRANCHEMENT AU CONTRÔLEUR ICI
        ControleurEditeur controleur = new ControleurEditeur(this);
        grille.addMouseListener(controleur.getGrilleMouseListener());    

        centre.add(grille, BorderLayout.CENTER);
        return centre;
    }

    /**
     * @return L'outil actuellement sélectionné par l'utilisateur.
     */
    public String getOutilSelectionne() {
        return this.outilSelectionne;
    }

    /**
     * @return Le composant graphique de rendu de la grille.
     */
    public AfficherGrille getGrille() {
        return this.grille;
    }

    /**
     * @param pos La nouvelle position de la cible.
     */
    public void setCiblePosition(Vecteur pos) {
        this.ciblePosition = pos;
    }

    /**
     * @param pos La nouvelle position de départ du satellite.
     */
    public void setPositionDepart(Vecteur pos) {
        this.positionDepart = pos;
    }

    /**
     * @return True si l'obstacle configuré est un mur.
     */
    public boolean isObstacleEstMur() {
        return this.obstacleEstMur;
    }

    /**
     * @return Le rayon choisi pour l'obstacle.
     */
    public int getObstacleRayon() {
        return this.obstacleRayon;
    }

    // Barre laterale : outils de construction du niveau (obstacles, objectif, budget
    private JPanel construireSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(30, 20, 10));
        sidebar.setPreferredSize(new Dimension(185, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JLabel titre = new JLabel("CONSTRUCTION");
        titre.setForeground(new Color(255, 200, 80));
        titre.setFont(new Font("Arial", Font.BOLD, 13));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(titre);
        sidebar.add(Box.createVerticalStrut(15));

        // Bouton pour placer un obstacle sur le plateau
        JButton btnObstacle = creerBoutonOutil("Obstacle", "Choisir le type d'obstacle");
        btnObstacle.addActionListener(e -> {
            outilSelectionne = "obstacle";

            // Choix entre Mur et Cercle 
            String[] options = {"Obstacle Cercle", "Obstacle Mur"};
            int choix = JOptionPane.showOptionDialog(this, 
                    "Quel type d'obstacle voulez-vous placer ?", 
                    "Selection de l'obstacle", 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
                    null, options, options[0]);

            if (choix == 1) {
                obstacleEstMur = true;
                obstacleRayon = 15; // Valeur par défaut non critique pour un mur rectangle
            } else if (choix == 0) {
                obstacleEstMur = false;

                // Si c'est un cercle, on demande sa taille (son rayon)
                String tailleSaisie = JOptionPane.showInputDialog(this, 
                        "Entrez le rayon du cercle (ex: 15, 25, 40) :", 
                        "Taille de l'obstacle", JOptionPane.QUESTION_MESSAGE);

                if (tailleSaisie != null && !tailleSaisie.trim().isEmpty()) {
                    try {
                        obstacleRayon = Integer.parseInt(tailleSaisie.trim());
                    } catch (NumberFormatException ex) {
                        obstacleRayon = 15; // Sécurité si l'entrée est invalide
                    }
                } else {
                    obstacleRayon = 15;
                }
            }
        });
        sidebar.add(btnObstacle);
        sidebar.add(Box.createVerticalStrut(10));

        // Bouton pour definir la position de l'objectif sur le plateau
        JButton btnObjectif = creerBoutonOutil("Objectif", "Definir la destination");
        // Selectionner l'outil objectif pour le prochain clic sur la grille
        btnObjectif.addActionListener(e -> {
            outilSelectionne = "objectif";
            // TODO : appeler la logique de placement de l'objectif
        });
        sidebar.add(btnObjectif);
        sidebar.add(Box.createVerticalStrut(10));

        // Bouton pour definir le point de depart du satellite
        JButton btnDepart = creerBoutonOutil("Depart satellite", "Definir le point de depart");
        // Selectionner l'outil depart pour le prochain clic sur la grille
        btnDepart.addActionListener(e -> {
            outilSelectionne = "depart";
            // TODO : appeler la logique de placement du satellite
        });
        sidebar.add(btnDepart);
        sidebar.add(Box.createVerticalStrut(20));

        // Reglage du budget de depart avec un slider
        JLabel labelSlider = new JLabel("Budget de depart :");
        labelSlider.setForeground(Color.LIGHT_GRAY);
        labelSlider.setFont(new Font("Arial", Font.PLAIN, 12));
        labelSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(labelSlider);
        sidebar.add(Box.createVerticalStrut(5));

        JSlider sliderBudget = new JSlider(500, 3000, budgetDepart);
        sliderBudget.setMajorTickSpacing(500);
        sliderBudget.setPaintTicks(true);
        sliderBudget.setPaintLabels(true);
        sliderBudget.setBackground(new Color(30, 20, 10));
        sliderBudget.setForeground(Color.LIGHT_GRAY);
        sliderBudget.setMaximumSize(new Dimension(165, 60));
        sliderBudget.addChangeListener(e -> {
            budgetDepart = sliderBudget.getValue();
            labelBudgetDepart.setText("Budget de depart : " + budgetDepart + " euros");
        });
        sidebar.add(sliderBudget);

        return sidebar;
    }

    // Footer : sauvegarder et tester le niveau,retourner au menu
    private JPanel construireFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 8));
        footer.setBackground(new Color(20, 20, 20));

        JButton btnSauvegarder = new JButton("Sauvegarder le niveau");
        JButton btnTester      = new JButton("Tester le niveau");
        JButton btnRetour      = new JButton("Retour au Menu");

        styliserBoutonControle(btnSauvegarder, new Color(180, 130, 20));
        styliserBoutonControle(btnTester,      new Color(40, 160, 80));
        styliserBoutonControle(btnRetour,      new Color(140, 50, 50));

        btnSauvegarder.addActionListener(e -> {
            sauvegarderMapDansFichier();
        });

        btnTester.addActionListener(e -> testerNiveau());

        btnRetour.addActionListener(e -> {
            this.dispose();
            new MenuPrincipal();
        });

        footer.add(btnSauvegarder);
        footer.add(btnTester);
        footer.add(btnRetour);

        return footer;
    }

    private void testerNiveau() {
        Point depart = grille.getPointDepart();
        Point cible = grille.getPointCible();

        if (depart == null || cible == null) {
            JOptionPane.showMessageDialog(this,
                    "Placez au moins un Depart et un Objectif avant de tester !",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Construire la Map à partir de l'état de l'éditeur
        core.Map map = new core.Map();
        map.setPositionInitiale(positionDepart);
        map.setVitesseInitiale(new Vecteur(0.0, 10.0));
        map.setCiblePosition(ciblePosition);
        map.setBudgetInitial(budgetDepart);

        for (int i = 0; i < grille.getObstacles().size(); i++) {
            Point p = grille.getObstacles().get(i);
            int rayon = grille.getRayonsObstacles().get(i);
            boolean mur = grille.getEstUnMur().get(i);

            int mapX = (int)(p.x * 800.0 / grille.getWidth());
            int mapY = (int)(p.y * 700.0 / grille.getHeight());

            if (mur) {
                Point fin = grille.getFinsMurs().get(i);
                int finMapX = (int)(fin.x * 800.0 / grille.getWidth());
                int finMapY = (int)(fin.y * 700.0 / grille.getHeight());
                map.ajouterObstacle(new core.obstacles.ObstacleMur(
                new Vecteur(mapX, mapY),
                new Vecteur(finMapX, finMapY)
                ));
            } else {
                map.ajouterObstacle(new core.obstacles.ObstacleCirculaire(
                            new Vecteur(mapX, mapY), rayon
                            ));
            }
        }

        Joueur testeur = new Joueur("Test");
        Game engine = new Game(testeur, testeur, map);

        // Cacher l'éditeur, ouvrir la fenêtre de jeu
        setVisible(false);
        SwingUtilities.invokeLater(() -> {
            FenetreJeu jeu = new FenetreJeu(engine,this);
            jeu.setVisible(true);
        });
    }

    // Cree un bouton stylise pour la sidebar de l'editeur
    private JButton creerBoutonOutil(String nom, String description) {
        JButton btn = new JButton("<html><b>" + nom + "</b><br><small>" + description + "</small></html>");
        btn.setBackground(new Color(60, 40, 10));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(165, 55));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(new Color(180, 130, 20), 1));
        return btn;
    }

    //Applique le meme style aux boutons du footer
    private void styliserBoutonControle(JButton btn, Color couleur) {
        btn.setBackground(couleur.darker());
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(couleur, 1),
                    BorderFactory.createEmptyBorder(6, 16, 6, 16)));

    }
    private void sauvegarderMapDansFichier() {
        Point depart = grille.getPointDepart();
        Point cible = grille.getPointCible();

        if (depart == null || cible == null) {
            JOptionPane.showMessageDialog(this,
                    "Placez au moins un Depart et un Objectif avant de sauvegarder !",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nomFichier = JOptionPane.showInputDialog(this, 
                "Entrez le nom du fichier pour sauvegarder le niveau (ex: map2) :", 
                "Nom du niveau", JOptionPane.QUESTION_MESSAGE);

        if (nomFichier == null || nomFichier.trim().isEmpty()) {
            return;
        }

        if (!nomFichier.endsWith(".txt")) {
            nomFichier += ".txt";
        }

        try {
            // Emplacement exact sans recréation
            String cheminComplet = "src/core/maps/" + nomFichier;

            FileWriter writer = new FileWriter(cheminComplet); 
            writer.write("positionInitiale = " + positionDepart.getX() + " " + positionDepart.getY() + "\n");
            writer.write("vitesseInitiale = 0.0 10.0\n");
            writer.write("ciblePosition = " + ciblePosition.getX() + " " + ciblePosition.getY() + "\n");
            writer.write("budgetInitiale = " + budgetDepart + "\n");

            for (int i = 0; i < grille.getObstacles().size(); i++) {
                Point p = grille.getObstacles().get(i);
                int rayon = grille.getRayonsObstacles().get(i);
                boolean mur = grille.getEstUnMur().get(i);

                int mapX = (int)(p.x * 800.0 / grille.getWidth());
                int mapY = (int)(p.y * 700.0 / grille.getHeight());

                // Adapte l'écriture brute dans le fichier texte selon le choix de l'utilisateur
                if (mur) {
                    Point fin = grille.getFinsMurs().get(i);
                    int finMapX = (int)(fin.x * 800.0 / grille.getWidth());
                    int finMapY = (int)(fin.y * 700.0 / grille.getHeight());
                    writer.write("OBSTACLE = MUR " + mapX + ".0 " + mapY + ".0 "
                    + finMapX + ".0 " + finMapY + ".0\n");
                } else {
                    writer.write("OBSTACLE = SPHERE " + mapX + ".0 " + mapY + ".0 " + rayon + ".0 0.0\n");
                }
            }
            writer.close();

            JOptionPane.showMessageDialog(this,
                    "Niveau sauvegarde avec succes dans src/core/maps/" + nomFichier + " !",
                    "Succès", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            System.out.println("Erreur de sauvegarde : " + ex.getMessage());
        }
    }
}
