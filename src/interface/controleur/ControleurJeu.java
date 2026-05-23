package ihm.controleur;

import java.awt.event.*;
import javax.swing.JOptionPane;
import core.Game;
import core.obstacles.Obstacle;
import utils.Vecteur;
import ihm.FenetreJeu;

public class ControleurJeu {
    private Game engine;
    private FenetreJeu vue;

    public ControleurJeu(Game engine, FenetreJeu vue) {
        this.engine = engine;
        this.vue = vue;
    }

    /**
     * Gestionnaire du clic et glissé de souris sur la Map pour placer un obstacle
     * @return MouseListener event.
     */
    public MouseListener getMapMouseListener() {
        return new MouseAdapter() {
            private int startX;
            private int startY;

            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Obstacle selected = vue.getSelectedObstacle();
                if (selected == null) return;

                int endX = e.getX();
                int endY = e.getY();

                // Validation du Budget (Logique métier -> va vers le moteur/joueur)
                if (engine.getJoueurCourant().getBudget() >= selected.getPrix()) {
                    
                    selected.setPointFinal(new Vecteur(endX, endY));
                    selected.setPosition(startX, startY);
                    Obstacle copy = selected.copier();

                    if (engine.getMap().ajouterObstacle(copy)) {
                        engine.getJoueurCourant().retirerBudget(copy.getPrix());
                        vue.rafraichirLabels();
                    }
                }
            }
        };
    }

    /**
     * Action pour le bouton "LANCER LA SIMULATION"
     * @return ActionListener action
     */
    public ActionListener getActionLancerSimulation() {
        return e -> {
            engine.executerTour();
            vue.rafraichirLabels();
            
            if (engine.estGagne()) {
                JOptionPane.showMessageDialog(vue,
                    engine.getJoueurCourant().getNom() + " a atteint la cible !",
                    "Victoire !",
                    JOptionPane.INFORMATION_MESSAGE);
                vue.dispose();
            };
        };
    }
}
