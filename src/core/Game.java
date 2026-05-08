package core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import core.joueurs.Joueur;
import core.obstacles.*;

public class Game extends JFrame implements MouseListener {

    private Map map;
    private Joueur joueur1;
    private Joueur joueur2;
    private Joueur joueurCourant;
    private int tour;
    private Obstacle selectedObstacle;
    private int prixObstacle;
    
    private JLabel lblJoueur;
    private JLabel lblBudget;
    private JLabel lblTour;

    public Game(Joueur joueur1, Joueur joueur2) {
        super("GravityBot");
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.tour = 1;
        this.joueurCourant = joueur1;
        this.map = new Map();

        construireInterface();

        addMouseListener(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 700);
        this.setVisible(true);
    }

    private void construireInterface() {
        setLayout(new BorderLayout());

        // NORTH: Stats
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        topPanel.setBackground(new Color(30, 30, 30));
        
        lblJoueur = new JLabel("Joueur: " + joueurCourant.getNom());
        lblBudget = new JLabel("Budget: " + joueurCourant.getBudget() + "€");
        lblTour = new JLabel("Tour: " + tour);
        
        for (JLabel l : new JLabel[]{lblJoueur, lblBudget, lblTour}) {
            l.setForeground(Color.WHITE);
            l.setFont(new Font("Arial", Font.BOLD, 14));
            topPanel.add(l);
        }
        add(topPanel, BorderLayout.NORTH);

        // CENTER: Map
        add(map, BorderLayout.CENTER);

        // EAST: Sidebar (Shop)
        add(construireSidebar(), BorderLayout.EAST);

        // SOUTH: Controls
        JPanel footer = new JPanel();
        JButton btnLancer = new JButton("Lancer Simulation");
        btnLancer.addActionListener(e -> lancerPartie());
        footer.add(btnLancer);
        add(footer, BorderLayout.SOUTH);
    }

    private JPanel construireSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBackground(new Color(25, 25, 50));

        // TODO ajouter obstacles
        sidebar.add(creerBoutonObstacle("Planete", new ObstacleAimantPull(), 200));
        sidebar.add(creerBoutonObstacle("Propulseur", new ObstaclePropulseur(), 100));

        return sidebar;
    }

    private JButton creerBoutonObstacle(String nom, Obstacle obs, int prix) {
        JButton btn = new JButton(nom + " (" + prix + "€)");
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addActionListener(e -> {
            if (joueurCourant.getBudget() >= prix) {
                selectedObstacle = obs; 
                this.prixObstacle = prix;
                // Retirer l'argent au click
            } else {
                JOptionPane.showMessageDialog(this, "Pas assez d'argent !");
            }
        });
        return btn;
    }

    public void lancerPartie() {
        if (map.lancerSimulation()) {
            finPartie();
        } else {
            tour++;
            joueurCourant = (tour % 2 != 0) ? joueur1 : joueur2;
            refreshUI();
        }
    }

    private void refreshUI() {
        lblJoueur.setText("Joueur: " + joueurCourant.getNom());
        lblBudget.setText("Budget: " + joueurCourant.getBudget() + "€");
        lblTour.setText("Tour: " + tour);
    }

    public void finPartie() { /* Reset logic */ }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (selectedObstacle != null) {
            selectedObstacle.setPosition(e.getX(), e.getY());
            System.out.println("Obstacle ajouté à la position : " + e.getX() + " " + e.getY());
            map.ajouterObstacle(selectedObstacle);
            selectedObstacle = null;
            joueurCourant.retirerBudget(prixObstacle);
            refreshUI();
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
