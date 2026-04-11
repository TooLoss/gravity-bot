import javax.swing.*;
import java.awt.*;

/** Fenetre principale du jeu.
 * Nord : bandeau info (budget et tour en cours)
 * Centre : plateau de jeu
 * Est - barre laterale (boutique des elements a placer)
 * Sud - boutons de controle
 * Cette fenetre s'ouvre quand on clique sur Jouer dans MenuPrincipal
 */
 public class FenetreJeu extends JFrame {
     /** Le budget. **/
     private int budget = 1000;
     
     /** Le tour. **/
     private int tour = 1;

    /** Le label du budget. **/
     private JLabel labelBudget;
    
    /** Le label du numero de tour. **/
    private JLabel labelTour;

    public FenetreJeu() {
        setTitle("Gravity-Bot - Partie");
        setSize(900,650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        construireInterface();


    }

    private void construireInterface() {
        setLayout(new BorderLayout(5,5));

        add(construireBandeauNord(), BorderLayout.NORTH);
        add(construireCentre(), BorderLayout.CENTER);
        add(construireSidebar(), BorderLayout.EAST);
        add(construireFooter(), BorderLayout.SOUTH);

    }

    // Bandeau nord : affiche le budget et le tour en cours
    private JPanel construireBandeauNord() {
        JPanel bandeau = new JPanel( new FlowLayout(FlowLayout.CENTER, 40, 8));
        bandeau.setBackground(new Color(30, 30, 30));
        
        labelBudget = new JLabel("Budget : " + budget + " euros");
        labelBudget.setForeground(Color.WHITE);
        labelBudget.setFont(new Font("Arial", Font.BOLD, 16));
        
        labelTour = new JLabel("Tour : " + tour);
        labelTour.setForeground(Color.LIGHT_GRAY);
        labelTour.setFont(new Font("Arial", Font.BOLD, 16));
        
        bandeau.add(labelBudget);
        bandeau.add(labelTour);

        return bandeau;

    }

    // Zone Centrale : liée à AfficherGrille
    private JPanel construireCentre() {
        JPanel centre = new JPanel(new BorderLayout());
        centre.setBackground(new Color(15, 15, 40));
        centre.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 120), 2));
        
        // affichage de la grille
        centre.add(new AfficherGrille(), BorderLayout.CENTER);

        return centre;

    }

    // Barre Laterale : liste les elements que le joueur peut acheter et placer
    private JPanel construireSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(25, 25, 50));
        sidebar.setPreferredSize(new Dimension(180, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(15, 10, 15,10));
        JLabel titre = new JLabel("ELEMENTS");
        titre.setForeground(new Color(100, 160, 255));
        titre.setFont(new Font("Arial", Font.BOLD, 14));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(titre);
        sidebar.add(Box.createVerticalStrut(15));

    // Bouton PLanete (200 euros)
    JButton btnPlanete = creerBoutonElement("Planete", "Gravite - 200 euros");
    btnPlanete.addActionListener(e -> {
        JOptionPane.showMessageDialog(this,
            "Planete selectionnee.\nCliquez sur le plateau pour la placer.",
            "Planete", JOptionPane.INFORMATION_MESSAGE);

        //TODO (logique de placement)
    });
    sidebar.add(btnPlanete);
    sidebar.add(Box.createVerticalStrut(10));

    // Bouton Mur (100 euros)
    JButton btnMur = creerBoutonElement("Mur", "Rebond - 100 euros");
    btnMur.addActionListener(e -> {
        JOptionPane.showMessageDialog(this,
            "Mur selectionne.\nCliquez sur le plateau pour le le placer.",
            "Mur", JOptionPane.INFORMATION_MESSAGE);
        //TODO (logique de placement)
    });
    sidebar.add(btnMur);
    sidebar.add(Box.createVerticalStrut(10));

    // Bouton Ventilateur (150 euros)
    JButton btnVentil = creerBoutonElement("Ventilateur", "Propulsion - 150 euros");
    btnVentil.addActionListener(e -> {
        JOptionPane.showMessageDialog(this,
            "Ventilateur selectionne.\nCliquez sur le plateau pour le placer.",
            "Ventilateur", JOptionPane.INFORMATION_MESSAGE);
        //TODO (logique de placement)
    });
    sidebar.add(btnVentil);
    sidebar.add(Box.createVerticalStrut(10));

    return sidebar;
    }

    // Footer : boutons de controle pour lancer la simualtion ou retourner au menu
    private JPanel construireFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 8));
        footer.setBackground(new Color(20, 20, 20));

        JButton btnLancer = new JButton("Lancer la simulation");
        JButton btnRetour = new JButton("Retour au Menu");

        styliserBoutonControle(btnLancer, new Color(40, 160, 80));
        styliserBoutonControle(btnRetour, new Color(140, 50, 50));

        btnLancer.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Simulation lancee !\n(La physique sera implementee par l'autre groupe.)",
                    "Simulation", JOptionPane.INFORMATION_MESSAGE);
            // TODO : declencher la simulation
        });

        btnRetour.addActionListener(e -> {
            this.dispose();
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
        });

        footer.add(btnLancer);
        footer.add(btnRetour);

        return footer;

    }

    // Creer boutons stylise pour la barre laterale
    private JButton creerBoutonElement(String nom, String info) {
        JButton btn = new JButton("<html><b>" + nom + "</b><br><small>" + info + "</small></html>");
        btn.setBackground(new Color(40, 40, 80));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(160, 55));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 160), 1));

        return btn;
    }

    // Appliquer meme style aux boutons du Footer
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