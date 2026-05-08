import javax.swing.*;
import java.awt.event.*;
import core.Game;
import core.joueurs.*;

class GravityBot extends JFrame {

    public static void main(String[] args) {
        Joueur j1 = new JoueurHumain("Joueur 1");
        Joueur j2 = new JoueurHumain("Joueur 2");

        Game game = new Game(j1, j2);
        
        game.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    }
}
