package ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import core.Game;
import core.joueurs.Joueur;
import ihm.*;

/**
 * Écran de sélection de map.
 * Liste tous les fichiers .txt du répertoire src/core/maps/ et permet
 * de choisir une map manuellement ou aléatoirement.
 */
public class SelectionMap extends JFrame {

    private static final Color LOGAN_GLOW = new Color(170, 169, 205);
    private static final Color WHITE = new Color(246, 246, 246);
    private static final Color GOLD = new Color(255, 200, 80);
    private static final String MAPS_DIR = "src/core/maps/";

    public SelectionMap() {
        setTitle("GravityBot Choisir une Map");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        construireInterface();
        setVisible(true);
    }

    /**
     * Scanne le répertoire et filtre les fichiers valides.
     * * @return Une List d'objets File pointant vers les fichiers de configuration.
     */
    private List<File> chargerMaps() {
        List<File> maps = new ArrayList<>();
        File dir = new File(MAPS_DIR);
        if (dir.exists() && dir.isDirectory()) {
            File[] fichiers = dir.listFiles(f -> f.isFile() && f.getName().endsWith(".txt"));
            if (fichiers != null) {
                for (File f : fichiers) maps.add(f);
            }
        }
        return maps;
    }

    private void construireInterface() {
        ImageIcon bgIcon = new ImageIcon("ressources/gifGravity.gif");
        JLabel background = new JLabel(bgIcon);
        background.setLayout(new BorderLayout());
        setContentPane(background);

        appliquerCurseur();

        // Titre
        JLabel title = new JLabel("CHOISIR UNE MAP");
        title.setFont(new Font("Monospaced", Font.BOLD, 38));
        title.setForeground(WHITE);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        background.add(title, BorderLayout.NORTH);

        // Centre : bouton aléatoire + liste des maps
        List<File> maps = chargerMaps();

        JPanel listePanel = new JPanel();
        listePanel.setLayout(new BoxLayout(listePanel, BoxLayout.Y_AXIS));
        listePanel.setOpaque(false);
        listePanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 80));

        // Bouton aléatoire (mis en valeur)
        JButton btnAleatoire = creerBouton("🎲 MAP ALÉATOIRE");
        btnAleatoire.setForeground(GOLD);
        btnAleatoire.addActionListener(e -> {
            if (maps.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Aucune map trouvée dans " + MAPS_DIR,
                        "Erreur", JOptionPane.WARNING_MESSAGE);
            } else {
                File choix = maps.get(new Random().nextInt(maps.size()));
                lancerJeu(choix.getPath());
            }
        });
        listePanel.add(btnAleatoire);
        listePanel.add(Box.createVerticalStrut(18));

        // Séparateur
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(170, 169, 205, 80));
        sep.setMaximumSize(new Dimension(500, 1));
        listePanel.add(sep);
        listePanel.add(Box.createVerticalStrut(18));

        if (maps.isEmpty()) {
            JLabel vide = new JLabel("Aucune map disponible dans " + MAPS_DIR);
            vide.setForeground(LOGAN_GLOW);
            vide.setFont(new Font("Monospaced", Font.ITALIC, 16));
            vide.setAlignmentX(Component.CENTER_ALIGNMENT);
            listePanel.add(vide);
        } else {
            for (File map : maps) {
                String nom = map.getName().replace(".txt", "").toUpperCase().replace("_", " ");
                JButton btn = creerBouton(nom);
                String chemin = map.getPath();
                btn.addActionListener(e -> lancerJeu(chemin));
                listePanel.add(btn);
                listePanel.add(Box.createVerticalStrut(10));
            }
        }

        // ScrollPane transparent, comme dans la classe Credits
        JScrollPane scroll = new JScrollPane(listePanel);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setPreferredSize(new Dimension(540, 350));
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Personnalisation de la barre de défilement
        JScrollBar verticalBar = scroll.getVerticalScrollBar();
        verticalBar.setOpaque(false);
        verticalBar.setPreferredSize(new Dimension(8, 0));
        verticalBar.setUnitIncrement(16);

        verticalBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = LOGAN_GLOW;
                this.trackColor = new Color(0, 0, 0, 0);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }

            @Override
            protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        wrapper.add(scroll);
        background.add(wrapper, BorderLayout.CENTER);

        // Footer retour menu
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        JButton btnRetour = creerBouton("Retour");
        btnRetour.addActionListener(e -> {
            dispose();
            new MenuPrincipal();
        });
        footer.add(btnRetour);
        background.add(footer, BorderLayout.SOUTH);
    }


    /**
     * * @param cheminMap Le chemin d'accès système vers le fichier de carte à charger.
     */
    private void lancerJeu(String cheminMap) {
        dispose();
        Joueur j1 = new Joueur("Joueur 1");
        Joueur j2 = new Joueur("Joueur 2");
        Game engine = new Game(j1, j2, cheminMap);
        SwingUtilities.invokeLater(() -> new FenetreJeu(engine));
    }

    /**
     * Fabrique un bouton personnalisé aux coins arrondis doté d'animations au survol.
     * * @param texte Le libellé textuel à afficher sur le bouton.
     * @return Un objet JButton stylisé prêt à être intégré.
     */
    private JButton creerBouton(String texte) {
        JButton btn = new JButton(texte);
        btn.setFont(new Font("Monospaced", Font.PLAIN, 20));
        btn.setForeground(WHITE);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setFocusable(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(460, 50));
        btn.setPreferredSize(new Dimension(380, 50));

        btn.setBorder(new javax.swing.border.Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(btn.getForeground());
                g2.setStroke(new java.awt.BasicStroke(1));
                g2.drawRoundRect(x, y, w - 1, h - 1, 30, 30);
            }
            @Override public Insets getBorderInsets(Component c) { return new Insets(10, 20, 10, 20); }
            @Override public boolean isBorderOpaque() { return false; }
            });

        btn.addMouseListener(new MouseAdapter() {
            Color couleurBase = btn.getForeground();
            @Override public void mouseEntered(MouseEvent e) { btn.setForeground(LOGAN_GLOW); }
            @Override public void mouseExited(MouseEvent e) { btn.setForeground(couleurBase); }
            });

        return btn;
    }

    private void appliquerCurseur() {
        try {
            ImageIcon icon = new ImageIcon("ressources/cursor.png");
            Image img = icon.getImage();
            BufferedImage resized = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resized.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(img, 0, 0, 32, 32, null);
            g2.dispose();
            setCursor(Toolkit.getDefaultToolkit().createCustomCursor(resized, new Point(0, 0), "cursor"));
        } catch (Exception e) {
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }
    }
}
