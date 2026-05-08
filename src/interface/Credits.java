import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class Credits {
    // Variables de résolution
    static int boardWidth = 700;
    static int boardHeight = 600;
    
    // Couleurs du thème
    private static final Color loganGlow = new Color(170, 169, 205);
    private static final Color white = new Color(246, 246, 246);
    
    // Le tableau de notre équipe
    private static final String[] equipe = {
            "Développé par le Groupe CD3 :",
            "",
            "Abid Mohamed",
            "El Amrany Anas",
            "El Haddadi Bilele",
            "El Hauat Chada",
            "El Qabbab Oussama",
            "Hosayn Mohamed Anis",
            "Kibou Yassine",
            "Masbah Louay",
            "Samari Yassine","",
            "ENSEEIHT - 2026"
    };

    JFrame frame = new JFrame("GravityBot");

    public Credits() {
        ImageIcon bgIcon = new ImageIcon("src/gifGravity.gif");
        JLabel backgroundLabel = new JLabel(bgIcon);
        
        // Setup de la frame
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Setup du fond
        backgroundLabel.setLayout(new BorderLayout());
        frame.setContentPane(backgroundLabel);

        // Gestion du curseur
        try {
            ImageIcon icon = new ImageIcon("src/cursor.png");
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

        // Titre
        JLabel title = new JLabel("ÉQUIPAGE CD3");
        title.setFont(new Font("Monospaced", Font.BOLD, 50));
        title.setForeground(white);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        backgroundLabel.add(title, BorderLayout.NORTH);

        // Panneau des noms
        JPanel namesPanel = new JPanel(new GridLayout(0, 1, 0, 15));
        namesPanel.setOpaque(false);
        
        for (String nom : equipe) {
            JLabel nameLabel = new JLabel(nom);
            nameLabel.setFont(new Font("Monospaced", Font.PLAIN, 22));
            nameLabel.setForeground(loganGlow);
            nameLabel.setHorizontalAlignment(JLabel.CENTER);
            namesPanel.add(nameLabel);
        }

        // Gestion du scroll transparent
        JScrollPane scrollPane = new JScrollPane(namesPanel);
        
        // Rend le fond et le conteneur interne (Viewport) transparents
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); 
        
        // Personnalisation de la barre de défilement
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setOpaque(false);
        verticalBar.setPreferredSize(new Dimension(8, 0)); // Épaisseur fine de 8px
        
        verticalBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = loganGlow; // Le curseur prend la couleur du thème
                this.trackColor = new Color(0, 0, 0, 0); // Le rail est 100% transparent
            }

            @Override
            protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }

            @Override
            protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }

            // Methode pour cacher les flèches moches de base
            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });

        // Vitesse de défilement fluide avec la molette
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Conteneur pour centrer et limiter la taille du ScrollPane
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        
        // Taille maximale visible (si la liste dépasse, le scroll s'active)
        scrollPane.setPreferredSize(new Dimension(400, 250)); 
        
        centerWrapper.add(scrollPane);
        backgroundLabel.add(centerWrapper, BorderLayout.CENTER);

        // Bouton retour au menu principal
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0)); // Marge en bas
        
        JButton btnRetour = createMenuButton("Retour");
        bottomPanel.add(btnRetour);
        backgroundLabel.add(bottomPanel, BorderLayout.SOUTH);

        // Affichage final
        frame.setVisible(true);
    }

    // Méthode de création de bouton
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Monospaced", Font.PLAIN, 25));
        btn.setForeground(white);
        btn.setContentAreaFilled(false); 
        btn.setOpaque(false);
        btn.setFocusable(false);
        btn.setPreferredSize(new Dimension(250, 50));

        int radius = 30; 
        
        btn.setBorder(new javax.swing.border.Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(btn.getForeground()); 
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            }
            @Override
            public Insets getBorderInsets(Component c) { return new Insets(10, 20, 10, 20); }
            @Override
            public boolean isBorderOpaque() { return false; }
        });

        // Animation au survol
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { btn.setForeground(loganGlow); }
            @Override
            public void mouseExited(MouseEvent e) { btn.setForeground(white); }
        });
        
        // Action pour retourner au menu principal
        btn.addActionListener(e -> {
            frame.dispose();
            MenuPrincipal menu = new MenuPrincipal();
            menu.frame.setVisible(true);
        });
        
        return btn;
    }
}