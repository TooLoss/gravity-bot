import javax.swing.*;
import java.awt.*;

/** Fenetre du mode Editeur de Gravity-Bot.
 * Permet au joueur de construire son propre contrat en :
 *   - placant des obstacles sur le plateau
 *   - reglant le budget de depart
 *   - definissant la position de l'objectif
 * Cette fenetre s'ouvre depuis le bouton "Editeur" du MenuPrincipal.
 * La structure visuelle est identique a FenetreJeu, seule la sidebar change.
 */
public class ModeEditeur extends JFrame {
    /** Le budget de départ. **/
    private int budgetDepart = 1000;
    
    /**Le label du budget de départ. **/
    private JLabel labelBudgetDepart;

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

        centre.add(new AfficherGrille(), BorderLayout.CENTER);

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
        JButton btnObstacle = creerBoutonOutil("Obstacle", "Bloquer la trajectoire");
        btnObstacle.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Obstacle selectionne.\nCliquez sur le plateau pour le placer.",
                "Obstacle", JOptionPane.INFORMATION_MESSAGE);
            // TODO : appeler la logique de placement
        });
        sidebar.add(btnObstacle);
        sidebar.add(Box.createVerticalStrut(10));

        // Bouton pour definir la position de l'objectif sur le plateau
        JButton btnObjectif = creerBoutonOutil("Objectif", "Definir la destination");
        btnObjectif.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Objectif selectionne.\nCliquez sur le plateau pour le positionner.",
                "Objectif", JOptionPane.INFORMATION_MESSAGE);
            // TODO : appeler la logique de placement de l'objectif
        });
        sidebar.add(btnObjectif);
        sidebar.add(Box.createVerticalStrut(10));

        // Bouton pour definir le point de depart du satellite
        JButton btnDepart = creerBoutonOutil("Depart satellite", "Definir le point de depart");
        btnDepart.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Point de depart selectionne.\nCliquez sur le plateau pour le positionner.",
                "Depart", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(this,
                "Niveau sauvegarde !\n(La logique de sauvegarde sera implementee par l'autre groupe.)",
                "Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
            // TODO : appeler la logique de sauvegarde du niveau
        });

        btnTester.addActionListener(e -> {
            // Ferme l'editeur et ouvre la fenetre de jeu pour tester le niveau cree
            this.dispose();
            FenetreJeu jeu = new FenetreJeu();
            jeu.setVisible(true);
        });

        btnRetour.addActionListener(e -> {
            this.dispose();
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
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


}