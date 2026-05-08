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
        add(mapPanel, BorderLayout.CENTER);

        // EST : Boutique
        add(construireSidebar(), BorderLayout.EAST);

        // SUD : Contrôles
        add(construireFooter(), BorderLayout.SOUTH);
    }

    private JPanel construireBandeauNord() {
        JPanel bandeau = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 8));
        bandeau.setBackground(new Color(30, 30, 30));
        
        labelBudget = new JLabel();
        labelTour = new JLabel();
        labelBudget.setForeground(Color.WHITE);
        labelTour.setForeground(Color.LIGHT_GRAY);
        
        rafraichirLabels();

        bandeau.add(labelBudget);
        bandeau.add(labelTour);
        return bandeau;
    }

    private JPanel construireSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(25, 25, 50));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        sidebar.add(creerBoutonElement("Planète", "200€", new ObstacleAimantPull(new Vecteur(.0f))));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(creerBoutonElement("Propulseur", "150€", new ObstaclePropulseur(new Vecteur(.0f))));

        return sidebar;
    }

    private JPanel construireFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 8));
        footer.setBackground(new Color(20, 20, 20));

        JButton btnLancer = new JButton("Lancer la simulation");
        btnLancer.addActionListener(e -> {
            engine.executerTour();
            rafraichirLabels();
        });

        footer.add(btnLancer);
        return footer;
    }

    private JButton creerBoutonElement(String nom, String prix, Obstacle type) {
        JButton btn = new JButton("<html><b>" + nom + "</b><br>" + prix + "</html>");
        btn.addActionListener(e -> selectedObstacle = type);
        return btn;
    }

    private void placerObstacle(int x, int y) {
        if (selectedObstacle != null) {
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
