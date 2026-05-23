package ihm.controleur;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import utils.Vecteur;
import ihm.ModeEditeur;

/** Contrôleur gérant les interactions de l'éditeur de niveau. */
public class ControleurEditeur {

    /** La vue de l'éditeur associée. */
    private ModeEditeur vue;

    /**
     * @param vue La vue de l'éditeur à contrôler.
     */
    public ControleurEditeur(ModeEditeur vue) {
        this.vue = vue;
    }

    /**
     * @return Le gestionnaire de souris pour le placement d'éléments sur la grille.
     */
    public MouseListener getGrilleMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String outil = vue.getOutilSelectionne();
                if (outil == null) return;

                int caseTaille = vue.getGrille().tailleCase;
                int px = (e.getPoint().x / caseTaille) * caseTaille;
                int py = (e.getPoint().y / caseTaille) * caseTaille;
                
                int mapX = (int)(e.getPoint().x * 800.0 / vue.getGrille().getWidth());
                int mapY = (int)(e.getPoint().y * 700.0 / vue.getGrille().getHeight());

                switch (outil) {
                    case "objectif":
                        vue.setCiblePosition(new Vecteur(mapX, mapY));
                        vue.getGrille().setCible(px, py);
                        vue.getGrille().repaint();
                        break;
                    case "depart":
                        vue.setPositionDepart(new Vecteur(mapX, mapY));
                        vue.getGrille().setDepart(px, py);
                        vue.getGrille().repaint();
                        break;
                    case "obstacle": 
                        vue.getGrille().ajouterObstacle(px, py, vue.isObstacleEstMur(), vue.getObstacleRayon());
                        break;
                }
            }
        };
    }
}
