package ihm.controleur;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import utils.Vecteur;
import ihm.ModeEditeur;

/** Contrôleur gérant les interactions de l'éditeur de niveau. */
public class ControleurEditeur {

    private ModeEditeur vue;

    public ControleurEditeur(ModeEditeur vue) {
        this.vue = vue;
    }

    /**
     * Retourne le gestionnaire de souris pour la grille.
     *
     * Pour les obstacles MUR : drag (pressed → released) pour définir les 2 coins.
     * Pour tout le reste (cercle, départ, objectif) : clic simple (released = pressed).
     */
    public MouseListener getGrilleMouseListener() {
        return new MouseAdapter() {

            // Point mémorisé au moment du clic
            private int startPx, startPy;
            private int startMapX, startMapY;

            @Override
            public void mousePressed(MouseEvent e) {
                int caseTaille = vue.getGrille().tailleCase;
                // Snap à la grille
                startPx   = (e.getPoint().x / caseTaille) * caseTaille;
                startPy   = (e.getPoint().y / caseTaille) * caseTaille;
                // Coordonnées dans l'espace Map
                startMapX = (int)(e.getPoint().x * 800.0 / vue.getGrille().getWidth());
                startMapY = (int)(e.getPoint().y * 700.0 / vue.getGrille().getHeight());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                String outil = vue.getOutilSelectionne();
                if (outil == null) return;

                int caseTaille = vue.getGrille().tailleCase;

                // Point de fin (snap à la grille)
                int endPx   = (e.getPoint().x / caseTaille) * caseTaille;
                int endPy   = (e.getPoint().y / caseTaille) * caseTaille;
                int endMapX = (int)(e.getPoint().x * 800.0 / vue.getGrille().getWidth());
                int endMapY = (int)(e.getPoint().y * 700.0 / vue.getGrille().getHeight());

                switch (outil) {
                    case "objectif":
                        vue.setCiblePosition(new Vecteur(startMapX, startMapY));
                        vue.getGrille().setCible(startPx, startPy);
                        vue.getGrille().repaint();
                        break;

                    case "depart":
                        vue.setPositionDepart(new Vecteur(startMapX, startMapY));
                        vue.getGrille().setDepart(startPx, startPy);
                        vue.getGrille().repaint();
                        break;

                    case "obstacle":
                        if (vue.isObstacleEstMur()) {
                            // ── MUR : drag multi-cases ──────────────────────────────────
                            // Garantit au moins une case si l'utilisateur ne fait que cliquer
                            int fx = (endPx == startPx) ? startPx + caseTaille : endPx;
                            int fy = (endPy == startPy) ? startPy + caseTaille : endPy;
                            int fmx = (endMapX == startMapX) ? startMapX + 50 : endMapX;
                            int fmy = (endMapY == startMapY) ? startMapY + 50 : endMapY;

                            vue.getGrille().ajouterObstacle(
                                startPx, startPy, fx, fy,
                                startMapX, startMapY, fmx, fmy,
                                true, vue.getObstacleRayon());
                        } else {
                            // ── CERCLE : clic simple, pas de fin ────────────────────────
                            vue.getGrille().ajouterObstacle(
                                startPx, startPy, startPx, startPy,
                                startMapX, startMapY, startMapX, startMapY,
                                false, vue.getObstacleRayon());
                        }
                        break;
                }
            }
        };
    }
}
