import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import core.obstacles.*;
import core.Game;
import core.Map;
import core.Obstacle;
import utils.Vecteur;

public class FenetreJeu extends JFrame {
    private Game engine;
    private JLabel labelBudget;
    private JLabel labelTour;
    private Obstacle selectedObstacle;

    public FenetreJeu(Game engine) {
        this.engine = engine;
        setTitle("Gravity-Bot - Arena");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        construireInterface();
        setVisible(true);
    }

    public FenetreJeu() {}

    private void construireInterface() {
        // Fond global sombre
        getContentPane().setBackground(new Color(10, 10, 15));
        setLayout(new BorderLayout(5, 5));

        // NORD : Infos (Budget / Tour)
        add(construireBandeauNord(), BorderLayout.NORTH);

        // CENTRE : Ta Map (Physics engine / JPanel)
        Map mapPanel = engine.getMap();
        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                placerObstacle(e.getX(), e.getY());
            }
        });
        // Bordure stylisée pour délimiter la zone de jeu
        mapPanel.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 120), 2));
        add(mapPanel, BorderLayout.CENTER);

        // EST : Boutique
        add(construireSidebar(), BorderLayout.EAST);

        // SUD : Contrôles
        add(construireFooter(), BorderLayout.SOUTH);
    }

    private JPanel construireBandeauNord() {
        JPanel bandeau = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        bandeau.setBackground(new Color(25, 25, 30));
        
        labelBudget = new JLabel();
        labelTour = new JLabel();
        
        Font f = new Font("SansSerif", Font.BOLD, 18);
        labelBudget.setFont(f);
        labelBudget.setForeground(new Color(100, 255, 100)); // Vert budget
        labelTour.setFont(f);
        labelTour.setForeground(new Color(180, 180, 255)); // Bleu clair
        
        rafraichirLabels();

        bandeau.add(labelBudget);
        bandeau.add(labelTour);
        return bandeau;
    }

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

        // Ajout des boutons avec style boutique
        sidebar.add(creerBoutonElement("Planète", "200€", new ObstacleAimantPull(new Vecteur(.0f)), new Color(60, 60, 150)));
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(creerBoutonElement("Propulseur", "150€", new ObstaclePropulseur(new Vecteur(.0f)), new Color(150, 60, 60)));

        return sidebar;
    }

    private JPanel construireFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 12));
        footer.setBackground(new Color(15, 15, 20));

        JButton btnLancer = new JButton("LANCER LA SIMULATION");
        styliserBoutonAction(btnLancer, new Color(40, 140, 80));
        
        btnLancer.addActionListener(e -> {
            engine.executerTour();
            rafraichirLabels();
        });

        footer.add(btnLancer);
        return footer;
    }

    private JButton creerBoutonElement(String nom, String prix, Obstacle type, Color c) {
        JButton btn = new JButton("<html><center><b>" + nom + "</b><br>" + prix + "</center></html>");
        btn.setMaximumSize(new Dimension(170, 65));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.setBackground(c);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createRaisedBevelBorder());

        btn.addActionListener(e -> selectedObstacle = type);
        return btn;
    }

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

    private void placerObstacle(int x, int y) {
        if (selectedObstacle != null) {
            // On positionne avant de copier pour transmettre les coordonnées
            selectedObstacle.setPosition(x, y);
            Obstacle copy = selectedObstacle.copier();
            engine.getMap().ajouterObstacle(copy);
            
            selectedObstacle = null; 
            rafraichirLabels();
        }
    }

    private void rafraichirLabels() {
        labelBudget.setText("Budget : " + engine.getJoueurCourant().getBudget() + "€");
        labelTour.setText("Tour : " + engine.getTour() + " (" + engine.getJoueurCourant().getNom() + ")");
    }
}
