import javax.swing.*;
import java.awt.event.*;
import core.Game;
import core.joueurs.*;

public class GravityBot {
    public static void main(String[] args) {
        // Initialize Backend
        Joueur j1 = new Joueur("Joueur 1");
        Joueur j2 = new Joueur("Joueur 2");
        Game engine = new Game(j1, j2);

        // Initialize Frontend and pass the backend to it
        SwingUtilities.invokeLater(() -> {
            new FenetreJeu(engine);
        });
    }
}
