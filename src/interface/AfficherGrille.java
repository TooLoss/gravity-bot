import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class AfficherGrille extends JPanel {
    public final int tailleCase = 30;
  @Override
  protected void paintComponent(Graphics g){
    super.paintComponent(g); //nettoyage du panneau
    g.setColor(Color.LIGHT_GRAY);
    for (int x = 0; x <= getWidth(); x += tailleCase){
        g.drawLine(x, 0, x, getHeight());
    } 
    for (int y = 0; y <= getHeight(); y += tailleCase) {
        g.drawLine(0, y, getWidth(), y);
    }
    g.setColor(Color.RED);
    g.fillRect(tailleCase * 2, tailleCase * 3, tailleCase, tailleCase);
  }   
}
