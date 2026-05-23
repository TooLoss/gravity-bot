import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * La classe MenuPrincipal représente l'interface d'accueil du jeu GravityBOT.
 * Elle permet de naviguer entre le jeu, l'éditeur, les paramètres, et les crédits.
 * Cette classe gère également l'ambiance sonore de fond et l'affichage d'un fond animé.
*/
public class MenuPrincipal extends JFrame {
    /** Largeur par défaut de la fenêtre du menu. */
    static int boardWidth = 700;
    /** Hauteur par défaut de la fenêtre du menu. */
    static int boardHeight = 600;

    // Couleurs du thème
    private static final Color loganGlow = new Color(170, 169, 205);
    private static final Color white = new Color(246, 246, 246);

    /** Libellés des options disponibles dans le menu. */
    private static final String[] menuOptions = {"Jouer", "Paramètres", "Crédits", "Éditeur", "Quitter"};
    
    /** Fenêtre principale du menu. */
    JFrame frame = new JFrame("GravityBot");

    /**
     * Constructeur de la classe MenuPrincipal.
     * Initialise la musique, charge les ressources graphiques (GIF, curseur)
     * et construit l'interface utilisateur.
     */
    public MenuPrincipal() {
        // Chargement du fond animé.
        ImageIcon bgIcon = new ImageIcon("ressources/gifGravity.gif");
        JPanel buttonsPanel = new JPanel();
        JLabel backgroundLabel = new JLabel(bgIcon);
        
        // Setup de la frame
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Setup du fond (GIF)
        backgroundLabel.setLayout(new BorderLayout());
        frame.setContentPane(backgroundLabel); 

        // Gestion du curseur
        try {
            ImageIcon icon = new ImageIcon("ressources/cursor.png");
            Image originalImg = icon.getImage();
            int cursorSize = 32;
            BufferedImage resizedImg = new BufferedImage(cursorSize, cursorSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resizedImg.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(originalImg, 0, 0, cursorSize, cursorSize, null);
            g2.dispose();
            Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(resizedImg, new Point(0, 0), "GravityCursor");
            frame.setCursor(customCursor);
        } catch (Exception e) {
            frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }

        // Setup du titre
        JLabel title = new JLabel("GRAVITYBOT");
        title.setFont(new Font("Monospaced", Font.BOLD, 60));
        title.setForeground(white);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        backgroundLabel.add(title, BorderLayout.NORTH);

        // Setup des boutons
        buttonsPanel.setLayout(new GridLayout(5, 1, 0, 16));
        buttonsPanel.setOpaque(false); // Transparent

        for (String opt : menuOptions) {
            JButton btn = createMenuButton(opt);
            buttonsPanel.add(btn);
        }

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(buttonsPanel);
        backgroundLabel.add(centerPanel, BorderLayout.CENTER); 

        frame.setVisible(true);
    }
    /**
     * Crée et stylise un bouton de menu stylisé.
     * @param text Le texte à afficher sur le bouton (détermine aussi l'action associée).
     * @return Un JButton configuré avec les polices, bordures et écouteurs du thème.
     */
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Monospaced", Font.PLAIN, 25));
        btn.setForeground(white);
        btn.setContentAreaFilled(false); 
        btn.setOpaque(false);
        btn.setFocusable(false);
        btn.setPreferredSize(new Dimension(250, 50));

        // On définit la bordure lissée et arrondie
        int radius = 30; // Rayon d'arrondissement
        
        btn.setBorder(new javax.swing.border.Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(btn.getForeground()); // Utilise la couleur du texte du bouton
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(10, 20, 10, 20);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        });

        // Interaction au survol
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(loganGlow);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setForeground(white);
            }
        });
        
        // Gestion des actions lors du clic.
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (text.equals("Jouer")) {
                    frame.dispose(); // Fermer la fenetre du menu
                    new SelectionMap();
                }
                else if (text.equals("Paramètres")) {
                    frame.dispose(); // Fermer la fenetre du menu
                    new Parametres(); // Parametres gère son propre affichage
                }
                else if (text.equals("Éditeur")){
                    frame.dispose(); // Fermer la fenetre du menu
                    ModeEditeur jeu = new ModeEditeur();
                    jeu.setVisible(true); // Afficher la fenetre editeur
                }
                else if (text.equals("Crédits")) {
                    frame.dispose(); // Fermer la fenetre du menu
                    new Credits(); // Credits gère son propre affichage
                }
                else {
                    System.exit(0);
                }
            }
        });
        
        return btn;
    }
    /**
     * Point d'entrée principal de l'application.
     * @param args Arguments de la ligne de commande (non utilisés).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal());
    }
}
