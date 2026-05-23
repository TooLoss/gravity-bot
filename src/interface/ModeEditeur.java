import javax.swing.*;
import java.awt.BasicStroke;
import java.awt.*;
import core.Game;
import core.joueurs.Joueur;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.awt.Point;
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
    
    // Zone Centrale : liée à AfficherGrille
    private JPanel construireCentre() {
        JPanel centre = new JPanel(new BorderLayout());
        centre.setBackground(new Color(20, 15, 5));
        centre.setBorder(BorderFactory.createLineBorder(new Color(120, 80, 20), 2));

        grille = new AfficherGrille();
        grille.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (outilSelectionne == null) return;

                int px = (e.getX() / grille.tailleCase) * grille.tailleCase;
                int py = (e.getY() / grille.tailleCase) * grille.tailleCase;
                int mapX = (int)(e.getX()* 500.0 / grille.getWidth());
                int mapY = (int)(e.getY()* 500.0 / grille.getHeight());

                switch (outilSelectionne) {
                    case "objectif":
                        ciblePosition = new Vecteur(mapX,mapY);
                        grille.setCible(px,py);
                        grille.repaint();
                        break;
                    case "depart":
                        positionDepart = new Vecteur(mapX,mapY);
                        grille.setDepart(px,py);
                        grille.repaint();
                        break;
                    case "obstacle": 
                        // Utilise la configuration choisie (Mur ou Cercle avec son rayon)
                        grille.ajouterObstacle(px, py, obstacleEstMur, obstacleRayon);
                        break;
                    default:
                        System.out.println(outilSelectionne + " placé en (" + px + ", " + py + ")");
                        break;
                }
            }
        });    

        centre.add(grille, BorderLayout.CENTER);

        return centre;
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

        btnTester.addActionListener(e -> {
            Joueur j1 = new Joueur("Joueur 1", budgetDepart);
            Joueur j2 = new Joueur("Joueur 2", budgetDepart);
            Game game = new Game(j1, j2);
            game.getMap().setCiblePosition(ciblePosition);
            game.getMap().setPositionInitiale(positionDepart);
            this.dispose();
            new FenetreJeu(game);
        });

        btnRetour.addActionListener(e -> {
            this.dispose();
            new MenuPrincipal();
        });

        footer.add(btnSauvegarder);
        footer.add(btnTester);
        footer.add(btnRetour);

        return footer;
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

            for (int i = 0; i < grille.getObstacles().size(); i++) {
                Point p = grille.getObstacles().get(i);
                int rayon = grille.getRayonsObstacles().get(i);
                boolean mur = grille.getEstUnMur().get(i);
                
                int mapX = (int)(p.x * 500.0 / grille.getWidth());
                int mapY = (int)(p.y * 500.0 / grille.getHeight());
                
                // Adapte l'écriture brute dans le fichier texte selon le choix de l'utilisateur
                if (mur) {
                    writer.write("OBSTACLE = MUR " + mapX + ".0 " + mapY + ".0\n");
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