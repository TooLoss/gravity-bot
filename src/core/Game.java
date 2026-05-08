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
    
    private JButton btnLancer;
    private JLabel lblJoueur;

    public Game(Joueur joueur1, Joueur joueur2) {
        super("GravityBot");
        this.setResizable(true);
        System.setProperty("sun.java2d.opengl", "true");
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.tour = 0;
        this.joueurCourant = joueur1;
        this.map = new Map(); 
        this.add(map, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        btnLancer = new JButton("Lancer Jeu");
        btnLancer.addActionListener(e -> lancerPartie());
        topPanel.add(btnLancer);

        lblJoueur = new JLabel(joueurCourant.getNom());
        topPanel.add(lblJoueur);

        add(topPanel, BorderLayout.NORTH);

        addMouseListener(this);

        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void lancerPartie() {
        tour++;
        if (map.lancerSimulation()) {
            finPartie();
        } else {
            this.joueurCourant = (this.tour % 2 == 0) ? this.joueur1 : this.joueur2;
            lblJoueur.setText(joueurCourant.getNom());
        }
    }

    public void finPartie() {
        // TODO changer de map et reset
    }

    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (selectedObstacle != null) {
            selectedObstacle.setPosition(e.getX(), e.getY());
            map.ajouterObstacle(selectedObstacle);
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
