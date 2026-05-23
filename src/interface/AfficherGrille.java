import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Point;

public class AfficherGrille extends JPanel {
    
    // Variables pour l'éditeur
    private ArrayList<Point> obstacles = new ArrayList<>();

    public void setPointDepart(int x, int y) { 
        this.setDepart(x, y); 
    }
    
    public void setPointCible(int x, int y) { 
        this.setCible(x, y); 
    }
    
    public void ajouterObstacle(int x, int y) { 
        this.obstacles.add(new Point(x, y)); 
        repaint(); 
    }
    
    public Point getPointDepart() { return new Point(departX, departY); }
    public Point getPointCible() { return new Point(cibleX, cibleY); }
    public ArrayList<Point> getObstacles() { return obstacles; }
    
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
        g.setColor(Color.GRAY);
        for (Point p : obstacles) {
            g.fillOval(p.x, p.y, tailleCase, tailleCase);
        }
    }
}