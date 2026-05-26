import javax.swing.*;
import java.awt.event.*;
import core.Game;
import core.joueurs.*;
import ihm.*;

public class GravityBot {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal();
        });
    }
}
