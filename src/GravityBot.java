import javax.swing.*;
import java.awt.event.*;
import java.awt.MouseInfo;
import java.awt.Point;

class GravityBot extends JFrame implements MouseListener {

    public GravityBot() {
        super("titre de l'application");

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        };

        addWindowListener(l);

        JPanel panneau = new JPanel();
        setSize(1000,1000);
        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new GravityBot();
    }
}
