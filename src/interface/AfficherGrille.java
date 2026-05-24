package ihm;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Point;

public class AfficherGrille extends JPanel {
    
    // Variables pour l'éditeur
    private ArrayList<Point> obstacles = new ArrayList<>();
    // Liste parallèle pour stocker le rayon de chaque obstacle (ex: 15 par défaut, ou personnalisé)
    private ArrayList<Integer> rayonsObstacles = new ArrayList<>();
    // Liste pour savoir si c'est un mur ou un cercle (true si Mur, false si Cercle)
    private ArrayList<Boolean> estUnMur = new ArrayList<>();
    
    private ArrayList<Point> finsMurs = new ArrayList<>();

    public void setPointDepart(int x, int y) { 
        this.setDepart(x, y); 
    }
    
    public void setPointCible(int x, int y) { 
        this.setCible(x, y); 
    }
    
    // le type et le rayon personnalisé
    public void ajouterObstacle(int px, int py, int finX, int finY,
                             int mapX, int mapY, int finMapX, int finMapY,
                             boolean mur, int rayon) {
    obstacles.add(new Point(px, py));
    rayonsObstacles.add(rayon);
    estUnMur.add(mur);
    finsMurs.add(new Point(finX, finY));
    repaint();
    }
    
    public ArrayList<Point> getFinsMurs() { return finsMurs; }
    public Point getPointDepart() { return new Point(departX, departY); }
    public Point getPointCible() { return new Point(cibleX, cibleY); }
    public ArrayList<Point> getObstacles() { return obstacles; }
    public ArrayList<Integer> getRayonsObstacles() { return rayonsObstacles; }
    public ArrayList<Boolean> getEstUnMur() { return estUnMur; }
    
    public final int tailleCase = 30;

    private int departX = tailleCase * 2;
    private int departY = tailleCase * 3;
    private int cibleX = -1;
    private int cibleY = -1;

    public void setDepart(int x, int y) {
        this.departX = x;
        this.departY = y;
    }
    
    public void setCible(int x, int y) {
        this.cibleX = x;
        this.cibleY = y;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(10,10,25));
        g.setColor(new Color(51,204,255, 60));
        for (int x = 0; x <=getWidth(); x+= tailleCase) {
            g.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y <= getHeight(); y += tailleCase) {
            g.drawLine(0, y, getWidth(), y);
        }
        //Satellite (point de départ)
        g.setColor(Color.WHITE);
        g.fillOval(departX, departY, tailleCase, tailleCase);
        g.drawOval(departX + 5, departY + 5, tailleCase - 10, tailleCase - 10);
        //Cible/objectif —on l'affiche que si défini
        if (cibleX >= 0) {
            g.setColor(new Color(255, 220, 0));
            g.fillOval(cibleX, cibleY, tailleCase, tailleCase);
            g.setColor(Color.WHITE);
            g.drawOval(cibleX, cibleY, tailleCase, tailleCase);
        }
        
        // Dessin des obstacles selon leur type et leur taille
        for (int i = 0; i < obstacles.size(); i++) {
            Point p = obstacles.get(i);
            int r = rayonsObstacles.get(i);
            boolean mur = estUnMur.get(i);
            
            if (mur) {
                Point fin = finsMurs.get(i);
                int x = Math.min(p.x, fin.x);
                int y = Math.min(p.y, fin.y);
                int w = Math.abs(fin.x - p.x);
                int h = Math.abs(fin.y - p.y);
                if (w == 0) w = tailleCase; // taille minimale si clic sans drag
                if (h == 0) h = tailleCase;
                g.setColor(new Color(180, 50, 50));
                g.fillRect(x, y, w, h);
            } else {
                g.setColor(Color.GRAY); // Gris pour les sphères/cercles
                // On centre le dessin selon le rayon fourni
                g.fillOval(p.x + (tailleCase/2) - r, p.y + (tailleCase/2) - r, r * 2, r * 2);
            }
        }
    }
}
