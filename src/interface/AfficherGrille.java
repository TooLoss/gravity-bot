import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class AfficherGrille extends JPanel {
    public final int tailleCase = 30;
  @Override
  protected void paintComponent(Graphics g){
    super.paintComponent(g); //nettoyage du panneau
    setBackground(new Color(10, 10, 25));
    g.setColor(new Color(51, 204, 255, 60));
    for (int x = 0; x <= getWidth(); x += tailleCase){
        g.drawLine(x, 0, x, getHeight());
    } 
    for (int y = 0; y <= getHeight(); y += tailleCase) {
        g.drawLine(0, y, getWidth(), y);
    }
    //Couleur des obstacles
    g.setColor(new Color(255, 69, 0));
    g.fillRect(tailleCase * 5, tailleCase * 2, tailleCase, tailleCase);
    //Contour
    g.setColor(new Color(255, 150, 0));
    g.drawRect(tailleCase * 5, tailleCase * 2, tailleCase, tailleCase);
    //Couleur Satellite
    g.setColor(Color.WHITE);
    g.fillOval(tailleCase * 2, tailleCase * 3, tailleCase, tailleCase);
    //Effet sur satellite
    g.setColor(Color.WHITE);
    g.drawOval(tailleCase * 2 + 5,tailleCase * 3 + 5, tailleCase - 10, tailleCase - 10);
  }   
}
